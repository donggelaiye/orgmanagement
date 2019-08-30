package com.ucarinc.orgmanagement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucarinc.orgmanagement.dao.*;
import com.ucarinc.orgmanagement.entity.*;
import com.ucarinc.orgmanagement.entity.form.AccountSearchForm;
import com.ucarinc.orgmanagement.entity.form.ApproveAccountForm;
import com.ucarinc.orgmanagement.entity.form.RoleAccountForm;
import com.ucarinc.orgmanagement.entity.vo.AccountResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.IRoleService;
import com.ucarinc.orgmanagement.utils.ComUtil;
import com.ucarinc.orgmanagement.utils.PageUtil;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;


/**
 * description: 角色服务实现类
 *
 * @author 赖家美 （2477637880@qq.com）
 * @version 1.0
 * @date 2019-08-08 9:38:00
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RoleLineMapper roleLineMapper;

    @Autowired
    private BussinessLineMapper bussinessLineMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private AccountServiceImpl accountServiceImpl;


    /**
     * 对员工信息进行完善
     *
     * @param role 角色对象
     */
    public void improveEmpInfo(Role role) {
        if (null != role) {
            //获取创建员工姓名
            if (null != role.getCreateEmp()) {
                Long empId = role.getCreateEmp();
                role.setCreateEmpName(ComUtil.getEmpStr(empId));
            }

            //获取修改员工姓名
            if (null != role.getModifyEmp()) {
                Long empId = role.getModifyEmp();
                role.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }
    }

    public void improveRoleEmpInfo(RoleAccountForm roleAccountForm) {
        if (null != roleAccountForm) {
            //获取创建员工姓名
            if (null != roleAccountForm.getCreateEmp()) {
                Long empId = roleAccountForm.getCreateEmp();
                roleAccountForm.setCreateEmpName(ComUtil.getEmpStr(empId));
            }

            //获取修改员工姓名
            if (null != roleAccountForm.getModifyEmp()) {
                Long empId = roleAccountForm.getModifyEmp();
                roleAccountForm.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }
    }

    @Override
    public List<Role> getRoleListMes(Role role) {
        return roleMapper.getRoleListMes(role);
    }

    @Override
    public PageResult getRoleList(String roleName, Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        roleMapper.getRoleList(roleName);
        return PageUtil.getPageResult(page);
    }

    @Override
    public Role getRole(Long roleId) {
        Role role = roleMapper.selectByRoleId(roleId);
        this.improveEmpInfo(role);
        return role;
    }

    @Override
    public int deleteRole(Long roleId) {
        return roleMapper.deleteRole(roleId);
    }

    @Override
    public int addRole(String insertJSON) {
        JSONObject jsonObject = JSONObject.parseObject(insertJSON);
        String roleStr = (String) jsonObject.get("role");
        Role role = JSONObject.parseObject(roleStr, Role.class);
        String approveAccount = role.getApproveAccount();
        byte result = checkApproveAccount(approveAccount);
        int mes;
        if (result == 0) {
            ApproveAccountForm approveAccountForm = roleMapper.checkApproveAccountChange(approveAccount);
            if (role.getApproveId().equals(approveAccountForm.getEmpId()) &&
                    role.getApproveDepartment().equals(approveAccountForm.getDeptName()) &&
                    role.getApproveName().equals(approveAccountForm.getEmpName())) {
                Long roleId = getRoleCount() + 1L;
                role.setRoleId(roleId);
                role.setCreateEmp(ShiroUtil.getActiveEmpId());
                role.setModifyEmp(ShiroUtil.getActiveEmpId());
                JSONArray bussinessLineArray = jsonObject.getJSONArray("bussinessLine");
                if (bussinessLineArray.size() > 0) {
                    for (int i = 0; i <= bussinessLineArray.size() - 1; i++) {
                        Byte bussinessLineId = ((Integer) bussinessLineArray.get(i)).byteValue();
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("role", role);
                        map.put("bussinessLineId", bussinessLineId);
                        addRoleLine(map);
                    }
                }
                roleMapper.addRole(role);
                mes = 0;
            } else {
                mes = 1;
            }
        } else {
            mes = 1;
        }
        return mes;
    }

    @Override
    public byte checkApproveAccount(String accountLogin) {
        return roleMapper.checkApproveAccount(accountLogin);
    }

    @Override
    public Result updateRole(String updateJSON) {
        JSONObject jsonObject = JSONObject.parseObject(updateJSON);
        String roleStr = (String) jsonObject.get("role");
        Role role = JSONObject.parseObject(roleStr, Role.class);
        Long roleId = role.getRoleId();
        Date date = new Date();
        role.setModifyTime(date);
        boolean roleStatus = checkRoleStatus(roleId);
        if(roleStatus) {
            deleteRoleLine(roleId);
            role.setModifyEmp(ShiroUtil.getActiveEmpId());
            /*Date date= new Date();
            role.setModifyTime(date);*/
            JSONArray bussinessLineArray = jsonObject.getJSONArray("bussinessLine");
            if (bussinessLineArray.size() > 0) {
                for (int i = 0; i <= bussinessLineArray.size() - 1; i++) {
                    Byte bussinessLineId = ((Integer) bussinessLineArray.get(i)).byteValue();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("role", role);
                    map.put("bussinessLineId", bussinessLineId);
                    updateRoleLine(map);
                    if (getRoleLineCount(map) > 0) {
                        updateRoleLine(map);
                    } else {
                        addRoleLine(map);
                    }
                }
            }
            roleMapper.updateRole(role);
        }else {
            return ResultUtil.error(702, "该角色" + roleId + "已被删除,请重新选择操作对象");
        }
        return ResultUtil.success();
    }

    @Override
    public Long getRoleCount() {
        int num = roleMapper.getRoleCount();
        return Long.valueOf(((Integer) num).toString());
    }

    @Override
    public int getRoleLineCount(Map map) {
        return roleLineMapper.getRoleLineCount(map);
    }

    @Override
    public Long checkRoleName(String roleName) {
        int num = roleMapper.getRoleNameCount(roleName);
        return Long.valueOf(((Integer) num).toString());

    }

    @Override
    public List<BussinessLine> getLineList() {
        return bussinessLineMapper.getLineList();
    }

    @Override
    public List<Long> getLineId(Long roleId) {
        return roleLineMapper.getLineId(roleId);
    }

    @Override
    public int addRoleLine(Map map) {
        return roleLineMapper.insert(map);
    }

    @Override
    public int updateRoleLine(Map map) {
        return roleLineMapper.updateRoleLine(map);
    }

    @Override
    public int deleteRoleLine(Long roleId) {
        return roleLineMapper.deleteRoleLine(roleId);
    }

    @Override
    public PageResult getRoleAccountList(RoleAccountForm roleAccountForm, Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        roleMapper.getRoleAccountList(roleAccountForm);
        for (Object object : page) {
            AccountResult accountResult = (AccountResult) object;
            this.accountServiceImpl.improveAccInfo(accountResult);
        }
        return PageUtil.getPageResult(page);
    }

    @Override
    public Result deleteRoleAccount(String jsonStr) {
        RoleAccountForm roleAccountForm = JSONObject.parseObject(jsonStr, RoleAccountForm.class);
        Long roleId = roleAccountForm.getRoleId();
        List accountLoginList = roleAccountForm.getAccountLoginList();
        boolean roleStatus = checkRoleStatus(roleId);
        if(roleStatus) {
            roleAccountForm.setModifyEmp(ShiroUtil.getActiveEmpId());
            for (int i = 0; i < accountLoginList.size(); i++) {
                String accountLogin = (String) accountLoginList.get(i);
                Long modifyEmp = roleAccountForm.getModifyEmp();
                Date modifyTime = roleAccountForm.getModifyTime();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("accountLogin", accountLogin);
                map.put("roleId", roleId);
                map.put("modifyEmp", modifyEmp);
                map.put("modifyTime", modifyTime);
                roleMapper.deleteRoleAccount(map);
            }
        }else {
            return ResultUtil.error(702, "该角色" + roleId + "已被删除,请重新选择操作对象");
        }
        return ResultUtil.success();
    }

    @Override
    public int getRoleAccountCount(Map map) {
        return roleMapper.getRoleAccountCount(map);
    }

    @Override
    public int updateRoleAccount(Map map) {
        return roleMapper.updateRoleAccount(map);
    }

    @Override
    public Result addRoleAccount(List<AccountResult> accounts, Long roleId) {
        for (AccountResult account : accounts) {
            AccountResult accountDataBase = getAccountResultByAccount(account.getAccountLogin());
            boolean isSame = compareAccount(account, accountDataBase);
            boolean roleStatus = checkRoleStatus(roleId);
            if(roleStatus) {
                if (!isSame) {
                    return ResultUtil.error(702, "账号" + account.getAccountLogin() + "的信息发生变化，请重新选择");
                } else {
                    String accountLogin = account.getAccountLogin();
                    Long modifyEmp = ShiroUtil.getActiveEmpId();
                    Date modifyTime = new Date();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("accountLogin", accountLogin);
                    map.put("roleId", roleId);

                    if (getRoleAccountCount(map) > 0) {
                        map.put("modifyEmp", modifyEmp);
                        map.put("modifyTime", modifyTime);
                        updateRoleAccount(map);
                    } else {
                        map.put("modifyEmp", modifyEmp);
                        map.put("modifyTime", modifyTime);
                        roleMapper.addRoleAccount(map);
                    }
                }
            }else {
                return ResultUtil.error(703, "角色" + roleId + "的信息发生变化，请重新选择");
            }
        }
        return ResultUtil.success();
    }

    private AccountResult getAccountResultByAccount(String accountLogin) {
        AccountSearchForm accountSearchForm = new AccountSearchForm();
        accountSearchForm.setAccountLogin(accountLogin);
        List<AccountResult> accountList = accountMapper.getAccountList(accountSearchForm);
        return accountList.get(0);
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
    private boolean compareAccount(AccountResult account, AccountResult accountDatabase) {
        // 账号无效
        if (accountDatabase.getStatus() == 2) {
            return false;
        }
        // 直接判断操作时间即可
        return account.getModifyTime().equals(accountDatabase.getModifyTime());
    }

    /**
     * 比较传入的角色Id
     *
     * @param roleId         传入的角色Id
     * @Author: 周志钦(zhiqin.zhou@ ucarinc.com)
     * @date: 2019/8/22 16:01
     * @return: 未删除返回true，删除返回false
     */
    private boolean checkRoleStatus(Long roleId) {
        Byte status = roleMapper.checkRoleStatus(roleId);
        boolean result = false;
        if (status == 1) {
            result = true;
        }
        return result;
    }
}
