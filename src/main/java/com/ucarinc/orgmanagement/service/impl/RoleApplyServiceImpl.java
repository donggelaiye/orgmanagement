package com.ucarinc.orgmanagement.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucarinc.orgmanagement.common.Constant;
import com.ucarinc.orgmanagement.common.ResultEnum;
import com.ucarinc.orgmanagement.dao.*;
import com.ucarinc.orgmanagement.entity.*;
import com.ucarinc.orgmanagement.entity.exception.GlobalException;
import com.ucarinc.orgmanagement.entity.form.*;
import com.ucarinc.orgmanagement.entity.vo.*;
import com.ucarinc.orgmanagement.service.IDataPermissionService;
import com.ucarinc.orgmanagement.service.IRoleApplyService;
import com.ucarinc.orgmanagement.utils.ComUtil;
import com.ucarinc.orgmanagement.utils.PageUtil;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Author: 徐才艺（caiyi.xu@ucarinc.com）
 * @CreateDate: 2019/8/3 15:05
 * @Version: 1.0
 */
@Service
public class RoleApplyServiceImpl implements IRoleApplyService {
    private Logger logger = LoggerFactory.getLogger(RoleApplyServiceImpl.class);
    @Autowired
    private RoleApplyMapper roleApplyMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleApplyAccountMapper roleApplyAccountMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private IDataPermissionService dataPermissionService;

    @Override
    public PageResult getRoleApplyList(Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        roleApplyMapper.getRoleApplyList();
        return PageUtil.getPageResult(page);
    }

    @Override
    public PageResult getRoleApplyList(Integer pageNum, Integer pageSize, RoleApplySearchForm roleApplyForm) {
        List<String> dataPermissionDeptIdList = dataPermissionService.getActiveAccountDataPermissionDeptIdList();
        Account account = ShiroUtil.getActiveAccountInfo();
        if (3 == account.getDataPermissionId()) {
            roleApplyForm.setAccount(account.getAccountLogin());
        }
        roleApplyForm.setGrantDeptList(dataPermissionDeptIdList);
        Page page = PageHelper.startPage(pageNum, pageSize);
        roleApplyMapper.query(roleApplyForm);
        return PageUtil.getPageResult(page);
    }

    // 根据角色id返回角色和业务线的信息
    @Override
    public RoleInfoResult getRoleInfoById(Long roleId) {
        RoleInfoResult roleInfoResult = new RoleInfoResult();
        Role role = roleMapper.selectByRoleId(roleId);
        roleInfoResult.setRole(role);
        String bussinessName = roleApplyMapper.getBussinessNameByRoleId(roleId);
        roleInfoResult.setBusinessLineName(bussinessName);
        return roleInfoResult;
    }

    /**
     * 新建角色申请
     *
     * @param newPageInfoJSON roleApply:｛roleApply相关的信息}, roleApplyAccount :[{RoleApplyAccountForm}]（包含RoleApplyAccount需要的信息）
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/10 14:39
     * @return:
     */
    @Transactional
    @Override
    public Result addApply(String newPageInfoJSON) {
        JSONObject pageInfoObject = JSONObject.parseObject(newPageInfoJSON);
        String roleApplyStr = (String) pageInfoObject.get("roleApply");
        RoleApply roleApply = JSONObject.parseObject(roleApplyStr, RoleApply.class);
        getAddApplyInfo(roleApply);
        Role role = roleMapper.selectByRoleId(roleApply.getRoleId());
        boolean isSame = compareRoleOfRoleApply(roleApply);
        if (!isSame) {
            throw new GlobalException(ResultEnum.ERROR_INVALID_ROLE.getCode(), ResultEnum.ERROR_INVALID_ROLE.getMsg());
        }
        roleApply.setApplyCode(generateApplyCode());
        JSONArray accountArray = pageInfoObject.getJSONArray("roleApplyAccount");
        RoleApplyAccount roleApplyAccount = new RoleApplyAccount();
        for (int i = 0; i < accountArray.size(); i++) {
            RoleApplyAccountForm roleApplyAccountForm = JSONObject.parseObject(String.valueOf(accountArray.get(i)), RoleApplyAccountForm.class);
            getRoleApplyAccountFromForm(roleApplyAccount, roleApplyAccountForm);
            boolean isTheSameAccount = compareAccountForm(roleApplyAccountForm);
            if (!isTheSameAccount) {
                throw new GlobalException(702, "账号" + roleApplyAccount.getApplyAccount() + "的信息发生改变，请删除后重新选择");
            }
            roleApplyAccount.setIsDelete((byte) 0);
            roleApplyAccount.setApplyCode(roleApply.getApplyCode());
            roleApplyAccount.setCreateTime(new Date());
            roleApplyAccount.setCreateEmp(roleApply.getCreateEmp());
            roleApplyAccount.setModifyEmp(roleApply.getCreateEmp());
            roleApplyAccount.setModifyTime(new Date());
            int accountRes = roleApplyAccountMapper.insertSelective(roleApplyAccount);
        }
        roleApply.setIsDelete((byte) 0);
        int roleApplyRes = roleApplyMapper.insertSelective(roleApply);
        return ResultUtil.success();
    }

    /**
     * 将角色申请页面传入的accountForm和数据库中的账号信息是否相同，相同返回true,不同返回false
     *
     * @param applyAccount 角色申请新建页面传入的数据
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/21 11:11
     * @return: 相同返回true，不同返回false
     */
    private boolean compareAccountForm(RoleApplyAccountForm applyAccount) {
        Account accountDatabase = getAccountByAccount(applyAccount.getAccountLogin());
        if (accountDatabase.getStatus() == 2) {
            return false;
        }
        return applyAccount.getModifyTime().equals(accountDatabase.getModifyTime());
//        return applyAccount.getEmpId().equals(accountDatabase.getEmpId())
//                && accountDatabase.getDeptName().equals(applyAccount.getEmpDept());


    }


    /**
     * 新建时，根据当前账号信息封装数据 包括申请人，操作人，修改人的信息，还有创建、修改、操作时间的封装
     *
     * @param roleApply 要封装的RoleApply
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/14 17:21
     * @return:
     */
    private void getAddApplyInfo(RoleApply roleApply) {
        String empCode = ShiroUtil.getActiveEmpCode();
        Long empId = ShiroUtil.getActiveEmpId();
        EmployeeResult employee = employeeMapper.getEmployeeById(empCode);
        roleApply.setCreateEmp(empId);
        roleApply.setApplyAccountLogin(employee.getAccountLogin());
        roleApply.setApplyEmpName(employee.getEmpName());
        roleApply.setApplyEmpId(empCode);
        roleApply.setApplyDept(employee.getDeptName());
        roleApply.setModifyEmp(empId);
        roleApply.setOperateEmp(employee.getAccountLogin() + '(' + employee.getEmpName() + ')');
        roleApply.setModifyAccountName(employee.getAccountLogin() + '(' + employee.getEmpName() + ')');
        Date nowDate = new Date();
        roleApply.setModifyTime(nowDate);
        roleApply.setCreateTime(nowDate);
        roleApply.setOperateTime(nowDate);
    }

    /**
     * 根据角色申请编号查询详情信息
     *
     * @param applyCode 角色申请编号
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/9 17:04
     * @return: json pageInfo: roleApply，accountData: RoleApplyAccountForm数组（封装了账号的信息）
     */
    @Override
    public String getRoleApplyInfoByApplyCode(String applyCode) {
        RoleApply roleApply = roleApplyMapper.getRoleApplyByApplyCode(applyCode);
        List<RoleApplyAccount> applyAccountList = roleApplyAccountMapper.getAccountListByApplyCode(applyCode);
        List<RoleApplyAccountForm> formList = getFormListFromAccounts(applyAccountList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageInfo", roleApply);
        jsonObject.put("accountData", formList);
        return jsonObject.toJSONString();
    }

    /**
     * 更新角色申请
     *
     * @param newPageInfoJSON roleApply:｛roleApply相关的信息}, roleApplyAccount :[{RoleApplyAccountForm}]（包含RoleApplyAccount需要的信息）
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/10 14:42
     * @return:
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result updateApply(String newPageInfoJSON) {
        JSONObject pageInfoObject = JSONObject.parseObject(newPageInfoJSON);
        String roleApplyStr = (String) pageInfoObject.get("roleApply");
        RoleApply roleApply = JSONObject.parseObject(roleApplyStr, RoleApply.class);
        getUpdateApplyInfo(roleApply);
        boolean isSame = compareRoleOfRoleApply(roleApply);
        if (!isSame) {
            throw new GlobalException(ResultEnum.ERROR_INVALID_ROLE.getCode(), ResultEnum.ERROR_INVALID_ROLE.getMsg());
        }
        String deleteAccountJSON = pageInfoObject.getJSONArray("deleteAccount").toJSONString();
        List<String> deleteAccountList = JSON.parseArray(deleteAccountJSON, String.class);
        List<RoleApplyAccountForm> updateAccountFormList = JSON.parseArray(pageInfoObject.getJSONArray("roleApplyAccount").toJSONString(), RoleApplyAccountForm.class);
        Result updateRes = updateApplyAccounts(roleApply, deleteAccountList, updateAccountFormList);
        if (updateRes.getCode() != 200) {
            throw new GlobalException(702, "更新账号失败");
        }
        boolean isTheSameRoleApply = checkStatusOfRoleApply(roleApply);
        if (!isTheSameRoleApply) {
            throw new GlobalException(ResultEnum.ERROR_INVALID_ROLEAPPLY.getCode(), ResultEnum.ERROR_INVALID_ROLEAPPLY.getMsg());
        }
        roleApplyMapper.updateRoleApplyByRoleCode(roleApply);
        return ResultUtil.success();
    }


    /**
     * 判断当前数据库中的角色申请状态是否为已新建，如果不是已新建则不允许修改
     *
     * @param roleApply 前台传入的roleApply
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/23 15:26
     * @return:
     */
    private boolean checkStatusOfRoleApply(RoleApply roleApply) {
        RoleApply roleApplyDatabase = roleApplyMapper.getRoleApplyByApplyCode(roleApply.getApplyCode());
        if (roleApplyDatabase.getStatus() != 0) {
            return false;
        }
        return true;
    }

    // 更新角色申请账号
    @Transactional(propagation = Propagation.REQUIRED)
    public Result updateApplyAccounts(RoleApply roleApply, List<String> deleteAccountList, List<RoleApplyAccountForm> updateAccountFormList) {
        doDeleteAccounts(roleApply, deleteAccountList);

        RoleApplyAccount roleApplyAccount = new RoleApplyAccount();
        Date nowData = new Date();
        for (int i = 0; i < updateAccountFormList.size(); i++) {
            RoleApplyAccountForm roleApplyAccountForm = updateAccountFormList.get(i);
            getRoleApplyAccountFromForm(roleApplyAccount, roleApplyAccountForm);
            boolean isValidAccount = isValidAccount(roleApplyAccountForm);
            if (!isValidAccount) {
                throw new GlobalException(702, "账号" + roleApplyAccount.getApplyAccount() + "的信息发生改变，请删除后重新选择");
            }
            roleApplyAccount.setModifyTime(nowData);
            roleApplyAccount.setApplyCode(roleApply.getApplyCode());
            roleApplyAccount.setModifyEmp(roleApply.getModifyEmp());
            roleApplyAccount.setIsDelete((byte) 0);
            int accountRes = updateAccount(roleApplyAccount);
            // 更新不成功就插入
            if (accountRes != 1) {
                roleApplyAccount.setCreateTime(nowData);
                roleApplyAccount.setCreateEmp(roleApply.getCreateEmp());
                roleApplyAccountMapper.insertSelective(roleApplyAccount);
            }
        }
        return ResultUtil.success();
    }

    private boolean isValidAccount(RoleApplyAccountForm roleApplyAccountForm) {
        Account accountDatabase = getAccountByAccount(roleApplyAccountForm.getAccountLogin());
        if (accountDatabase.getStatus() == 2) {
            return false;
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {RuntimeException.class, Error.class})
    public int updateAccount(RoleApplyAccount roleApplyAccount) {
        int accountRes = roleApplyAccountMapper.updateAccount(roleApplyAccount);
        return accountRes;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Error.class})
    public void doDeleteAccounts(RoleApply roleApply, List<String> deleteAccountList) {

        for (String deleteAccount : deleteAccountList) {
            roleApplyAccountMapper.deleteAccount(roleApply.getApplyCode(), deleteAccount);

        }

    }

    // 比较角色申请中的角色部分
    private boolean compareRoleOfRoleApply(RoleApply roleApply) {
        Role role = roleMapper.selectByRoleId(roleApply.getRoleId());
        String bussinessNameRes = roleApplyMapper.getBussinessNameByRoleId(role.getRoleId());
        if (role.getStatus() == 0) {
            return false;
        }
        return roleApply.getBussinessLine().equals(bussinessNameRes)
                && roleApply.getRoleApproveName().equals(role.getApproveName())
                && roleApply.getRoleName().equals(role.getRoleName());
    }

    /**
     * 修改角色申请时，封装修改人的信息，包括操作人和操作时间
     *
     * @param roleApply
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/14 18:00
     */
    private void getUpdateApplyInfo(RoleApply roleApply) {
        Long empId = ShiroUtil.getActiveEmpId();
        String empCode = ShiroUtil.getActiveEmpCode();
        EmployeeResult employee = employeeMapper.getEmployeeById(empCode);
        roleApply.setModifyEmp(empId);
        roleApply.setModifyTime(new Date());
        roleApply.setOperateEmp(employee.getAccountLogin() + '(' + employee.getEmpName() + ')');
        roleApply.setModifyAccountName(employee.getAccountLogin() + '(' + employee.getEmpName() + ')');
        roleApply.setOperateTime(new Date());
    }

    /**
     * 根据角色名称和登录账号
     *
     * @param applyCode 角色申请编号
     * @param account   账号名称
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/14 18:54
     * @return:
     */
    @Override
    public int deleteAccount(String applyCode, String account) {
        int res = roleApplyAccountMapper.deleteAccount(applyCode, account);
        return res;
    }


    private List<RoleApplyAccountForm> getFormListFromAccounts(List<RoleApplyAccount> applyAccountList) {
        ArrayList<RoleApplyAccountForm> roleApplyAccountList = new ArrayList<>();
        for (RoleApplyAccount roleApplyAccount : applyAccountList) {
            RoleApplyAccountForm roleApplyAccountForm = new RoleApplyAccountForm();
            roleApplyAccountForm.setAccountLogin(roleApplyAccount.getApplyAccount());
            roleApplyAccountForm.setEmpName(roleApplyAccount.getEmpName());
            roleApplyAccountForm.setDeptName(roleApplyAccount.getEmpDept());
            roleApplyAccountForm.setEmpId(roleApplyAccount.getEmpId());
            roleApplyAccountForm.setOperateType(roleApplyAccount.getOperateType());
            roleApplyAccountList.add(roleApplyAccountForm);
        }
        return roleApplyAccountList;
    }

    /**
     * 从前端提交的表单中，拿到需要字段的数据，放入RoleApplyAccount中
     *
     * @param account 要填充信息的RoleApplyAccount类
     * @param form    表单
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/8 22:18
     */
    private void getRoleApplyAccountFromForm(RoleApplyAccount account, RoleApplyAccountForm form) {
        account.setApplyAccount(form.getAccountLogin());
        account.setEmpDept(form.getDeptName());
        account.setEmpId(form.getEmpId());
        account.setEmpName(form.getEmpName());
        account.setOperateType(form.getOperateType());
        account.setModifyEmp(null);
    }

    /**
     * @Description: 生成角色申请订单号
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/8 22:19
     * @return: 15位的角色申请编号
     */
    @Override
    public synchronized String generateApplyCode() {
        StringBuilder applyCodeBuilder = new StringBuilder("SQ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        applyCodeBuilder.append(String.valueOf(sdf.format(new Date())));
        String codePrefix = applyCodeBuilder.toString();
        int count = roleApplyMapper.getApplyCount(codePrefix);
        String codeSuffix = String.valueOf(count + 1);
        int suffixLength = 5;
        for (int i = 0; i < suffixLength - codeSuffix.length(); i++) {
            applyCodeBuilder.append('0');
        }
        applyCodeBuilder.append(codeSuffix);
        return applyCodeBuilder.toString();
    }

    @Override
    public List<RoleApply> getExportData(RoleApplySearchForm roleApplyForm) {
        List<String> dataPermissionDeptIdList = dataPermissionService.getActiveAccountDataPermissionDeptIdList();
        Account account = ShiroUtil.getActiveAccountInfo();
        if (3 == account.getDataPermissionId()) {
            roleApplyForm.setAccount(account.getAccountLogin());
        }
        roleApplyForm.setGrantDeptList(dataPermissionDeptIdList);
        List<RoleApply> queryResult = roleApplyMapper.query(roleApplyForm);
        return queryResult;
    }

    // 现在前台只能选择一个了，但是传进来还是数组
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public int deleteRoleApplys(String deleteItems) {
        JSONObject jsonObject = JSON.parseObject(deleteItems);
        JSONArray deleteItemArray = jsonObject.getJSONArray("deleteItems");
        Date nowDate = new Date();
        int res = 0;
        for (int i = 0; i < deleteItemArray.size(); i++) {
            RoleApply roleApply = JSON.parseObject(String.valueOf(deleteItemArray.get(i)), RoleApply.class);
            Long empId = ShiroUtil.getActiveEmpId();
            String empCode = ShiroUtil.getActiveEmpCode();
            EmployeeResult employee = employeeMapper.getEmployeeById(empCode);
            roleApply.setModifyEmp(empId);
            roleApply.setModifyTime(nowDate);
            roleApply.setModifyAccountName(employee.getAccountLogin() + '(' + employee.getEmpName() + ')');
            roleApply.setOperateTime(nowDate);
            roleApply.setOperateEmp(employee.getAccountLogin() + '(' + employee.getEmpName() + ')');
            roleApply.setStatus((byte) 4);
            roleApply.setIsDelete((byte) Constant.IS_DELETED_YES);
            res = roleApplyMapper.deleteRoleApply(roleApply);
        }
        return res;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public int approveRoleApplys(String approveItems) {
        JSONObject jsonObject = JSON.parseObject(approveItems);
        JSONArray approveItemArray = jsonObject.getJSONArray("approveItems");
        List<RoleApply> applyItems = JSON.parseArray(jsonObject.getJSONArray("approveItems").toJSONString(), RoleApply.class);
        RoleApply roleApply = applyItems.get(0);
        roleApply.setOperateTime(new Date());
        roleApply.setOperateEmp(ComUtil.getEmpStr(ShiroUtil.getActiveEmpId()));
        roleApply.setStatus((byte) 1);
        int res = roleApplyMapper.updateRoleApplyByRoleCode(roleApply);
        return res;
    }


    @Override
    public PageResult getValidRoleList(String roleName, Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        roleMapper.getValidRoleList(roleName);
        return PageUtil.getPageResult(page);
    }

    @Override
    public Result getRoleInfoByRole(String roleJSON) {
        JSONObject jsonObject = JSON.parseObject(roleJSON);
        Role role = JSON.parseObject(String.valueOf(jsonObject.get("role")), Role.class);
        RoleInfoResult roleInfoResult = getRoleInfoById(role.getRoleId());
        boolean isSame = compareRole(role);
        if (!isSame) {
            return ResultUtil.error(ResultEnum.ERROR_INVALID_ROLE.getCode(), ResultEnum.ERROR_INVALID_ROLE.getMsg());
        }
        return ResultUtil.success(roleInfoResult);
    }

    @Override
    public Result getAccountsInfo(List<AccountResult> accounts) {
        for (AccountResult account : accounts) {
            Account accountDataBase = getAccountByAccount(account.getAccountLogin());
            boolean isSame = compareAccount(account, accountDataBase);
            if (!isSame) {
                return ResultUtil.error(702, "账号" + account.getAccountLogin() + "的信息发生变化，请重新选择");
            }
        }
        return ResultUtil.success(accounts);
    }

    private Account getAccountByAccount(String accountLogin) {
        Account accountInfo = accountMapper.getAccountByLogin(accountLogin);
        return accountInfo;
    }

    /**
     * 比较传入的账号和数据库查到的账号
     *
     * @param account         传入的账号
     * @param accountDatabase 数据库查询的账号
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/21 10:01
     * @return: 相同返回true，不同返回false
     */
    private boolean compareAccount(AccountResult account, Account accountDatabase) {
        // 账号无效
        if (accountDatabase.getStatus() == 2) {
            return false;
        }
        // 直接判断操作时间即可
        return account.getModifyTime().equals(accountDatabase.getModifyTime());
    }

    // 比较传入的角色和数据库存在的角色是否相等，相等返回true，不相等返回false
    private boolean compareRole(Role role) {
        RoleInfoResult roleInfoResult = getRoleInfoById(role.getRoleId());
        Role roleRes = roleInfoResult.getRole();
        if (roleRes.getStatus() == 0) {
            return false;
        }
        return role.getRoleName().equals(roleRes.getRoleName())
                && role.getApproveAccount().equals(roleRes.getApproveAccount())
                && role.getApproveDepartment().equals(roleRes.getApproveDepartment())
                && role.getApproveId().equals(roleRes.getApproveId())
                && role.getApproveName().equals(roleRes.getApproveName());
    }


}
