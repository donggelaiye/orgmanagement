package com.ucarinc.orgmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ucarinc.orgmanagement.common.Constant;
import com.ucarinc.orgmanagement.common.ResultEnum;
import com.ucarinc.orgmanagement.dao.AccountMapper;
import com.ucarinc.orgmanagement.dao.DeptPermissionMapper;
import com.ucarinc.orgmanagement.dao.EmployeeMapper;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.DeptPermission;
import com.ucarinc.orgmanagement.entity.exception.GlobalException;
import com.ucarinc.orgmanagement.entity.form.AccountForm;
import com.ucarinc.orgmanagement.entity.form.AccountModifyForm;
import com.ucarinc.orgmanagement.entity.form.AccountSearchForm;

import com.ucarinc.orgmanagement.entity.form.AccountSelectForm;
import com.ucarinc.orgmanagement.entity.vo.AccountResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.service.IAccountService;
import com.ucarinc.orgmanagement.service.IDataPermissionService;
import com.ucarinc.orgmanagement.service.IShiroSessionService;
import com.ucarinc.orgmanagement.utils.ComUtil;
import com.ucarinc.orgmanagement.utils.PageUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ucarinc.orgmanagement.utils.ShiroUtil.getActiveEmpId;

/**
 * description:账号管理Service接口实现类
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-05 9:52
 */
@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private DeptPermissionMapper deptPermissionMapper;

    @Autowired
    private IDataPermissionService iDataPermissionService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private IShiroSessionService shiroSessionService;


    //新建
    @Override
    public int addAccount(AccountModifyForm account) {

        //添加到账号
        account.setCreateEmp(ShiroUtil.getActiveEmpId());
        account.setModifyEmp(ShiroUtil.getActiveEmpId());
        account.setAccountLogin(account.getAccountLogin().toLowerCase());
        if (account.getEmpId() != null) {
            account.setIsLinkEmp((byte) 1);
        } else {
            account.setIsLinkEmp((byte) 0);
        }
//        //删除数据前判断是否存在
//        if(0 == employeeMapper.getCountById(account.getEmpId())){
//            throw new GlobalException(ResultEnum.ERROR_NULL_EMP.getCode(),ResultEnum.ERROR_NULL_EMP.getMsg());
//        }
        //添加到部门权限
        if (account.getDataPermissionId() == 4) {
            //处理权限表新增
            if (!account.getGrantDeptList().isEmpty()) {
                for (String deptId : account.getGrantDeptList()) {
                    DeptPermission deptPermission = new DeptPermission();
                    deptPermission.setAccountLogin(account.getAccountLogin());
                    deptPermission.setAccountDept(deptId);
                    deptPermission.setCreateEmp(ShiroUtil.getActiveEmpId());
                    deptPermission.setCreateTime(new Date());
                    deptPermission.setIsDelete((byte) Constant.IS_DELETED_NO);
                    deptPermission.setModifyEmp(ShiroUtil.getActiveEmpId());
                    deptPermission.setModifyTime(new Date());
                    deptPermissionMapper.insertAll(deptPermission);
                }
            }
        }else
        {

        }
        System.out.println(account);
        return accountMapper.insertSelective(account);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyAccount(AccountModifyForm account) {
        Long activeEmpId = getActiveEmpId();
        account.setModifyEmp(activeEmpId);

        // 删除数据前判断是否已经被删除
        if(0 != accountMapper.selectDelete(account.getAccountLogin())){
            throw new GlobalException(ResultEnum.Error_DELETE_ACC.getCode(),ResultEnum.Error_DELETE_ACC.getMsg());
        }

        if (account.getEmpId() != null) {
            account.setIsLinkEmp((byte) 1);
        } else {
            account.setIsLinkEmp((byte) 0);
        }

        //先处理权限表删除与账号表更新
        try {
            accountMapper.updateByLoginAccount(account);
            if (account.getRevokeDeptList() != null && !account.getRevokeDeptList().isEmpty()) {
                deptPermissionMapper.deleteBatch(account.getAccountLogin(), account.getRevokeDeptList(), activeEmpId);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GlobalException(500, "数据库错误!");
        }
        //处理权限表新增
        if (account.getGrantDeptList() != null && !account.getGrantDeptList().isEmpty()) {
            List<DeptPermission> grantList = new ArrayList<>();
            for (String deptId : account.getGrantDeptList()) {
                DeptPermission deptPermission = new DeptPermission();
                deptPermission.setAccountLogin(account.getAccountLogin());
                deptPermission.setAccountDept(deptId);
                deptPermission.setCreateEmp(activeEmpId);
                deptPermission.setCreateTime(new Date());
                deptPermission.setIsDelete((byte) Constant.IS_DELETED_NO);
                deptPermission.setModifyEmp(activeEmpId);
                deptPermission.setModifyTime(new Date());
                grantList.add(deptPermission);
            }
            try {
                deptPermissionMapper.insetBatch(grantList, activeEmpId);
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new GlobalException(500, "数据库错误!");
            }
        }
    }

    //删除
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteAccount(String loginAccount) {

        Account account = new Account();
        account.setModifyEmp(ShiroUtil.getActiveEmpId());
        account.setAccountLogin(loginAccount);

        // 删除数据前判断是否已经被删除
        if(0 != accountMapper.selectDelete(loginAccount)){
            throw new GlobalException(ResultEnum.Error_DELETE_ACC.getCode(),ResultEnum.Error_DELETE_ACC.getMsg());
        }
        shiroSessionService.killSessionByLoginAccount(loginAccount);
        accountMapper.updateModifyEmp(account);
        return accountMapper.updateByIsDeleted(loginAccount);

    }

    //冻结
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int freezeAccount(String loginAccount) {

        Account account = new Account();
        account.setModifyEmp(ShiroUtil.getActiveEmpId());
        account.setAccountLogin(loginAccount);

        // 冻结数据前判断是否已经被删除
        if(0 != accountMapper.selectDelete(loginAccount)){
            throw new GlobalException(ResultEnum.Error_DELETE_ACC.getCode(),ResultEnum.Error_DELETE_ACC.getMsg());
        }
        // 冻结数据前判断是否已经被冻结
        if(0 != accountMapper.selectFreeze(loginAccount)){
            throw new GlobalException(ResultEnum.Error_FREEZE_ACC.getCode(),ResultEnum.Error_FREEZE_ACC.getMsg());
        }

        accountMapper.updateModifyEmp(account);
        shiroSessionService.killSessionByLoginAccount(loginAccount);
        return accountMapper.updateByFreeze(loginAccount);

    }

    //解冻
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int unfreezeAccount(String loginAccount) {

        Account account = new Account();
        account.setModifyEmp(ShiroUtil.getActiveEmpId());
        account.setAccountLogin(loginAccount);

        // 删除数据前判断是否已经被删除
        if(0 != accountMapper.selectDelete(loginAccount)){
            throw new GlobalException(ResultEnum.Error_DELETE_ACC.getCode(),ResultEnum.Error_DELETE_ACC.getMsg());
        }
        // 解冻数据前判断是否已经被解冻
        if(0 != accountMapper.selectUnfreeze(loginAccount)){
            throw new GlobalException(ResultEnum.Error_UNFREEZE_ACC.getCode(),ResultEnum.Error_UNFREEZE_ACC.getMsg());
        }

        accountMapper.updateModifyEmp(account);
        return accountMapper.updateByUnfreeze(loginAccount);

    }

    @Override
    public AccountSelectForm getAccountSelectByLogin(String loginAccount) {

        AccountSelectForm accountSelectForm = accountMapper.getAccountSelectByLogin(loginAccount);
        if (null != accountSelectForm) {
            //获取创建员工姓名
            if (null != accountSelectForm.getCreateEmp()) {
                Long empId = accountSelectForm.getCreateEmp();
                accountSelectForm.setCreateEmpName(ComUtil.getEmpStr(empId));
            }
            //获取修改员工姓名
            if (null != accountSelectForm.getModifyEmp()) {
                Long empId = accountSelectForm.getModifyEmp();
                accountSelectForm.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }
        return accountSelectForm;
    }

    @Override
    public boolean updatePassword(String accountLogin, String accountPassword) {
        if (StringUtil.isNotEmpty(accountLogin) && StringUtil.isNotEmpty(accountPassword)) {
            if (accountMapper.updateAccountPassword(accountLogin, accountPassword) > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void resetAccountPassword(String loginAccount) {

    }

    @Override
    public PageResult getAccountList(Integer pageNum, Integer pageSize, AccountSearchForm accountSearchForm) {
        //使用 PageHelper插件进行分页
        Page page = PageHelper.startPage(pageNum, pageSize);
        accountMapper.getAccountList(accountSearchForm);
        for (Object object : page) {
            AccountResult accountResult = (AccountResult) object;
            this.improveAccInfo(accountResult);
        }

        return PageUtil.getPageResult(page);
    }

    @Override
    public String getAccountPassword(String loginAccount) {

        return accountMapper.getAccountPassword(loginAccount);
    }

    //判断账号是否唯一
    @Override
    public int IsLoginOnly(String loginAccount) {
        return accountMapper.selectByLoginOnly(loginAccount);
    }

    @Override
    public AccountResult getAccount(String loginAccount) {

        AccountResult accountResult = accountMapper.getAccount(loginAccount);
        if (null != accountResult) {
            this.improveAccInfo(accountResult);
        }
        Account account = new Account();
        account.setDataPermissionId(accountResult.getDataPermissionId());
        account.setAccountLogin(accountResult.getAccountLogin());
        if (4 == account.getDataPermissionId()){
            accountResult.setOriginPermission(iDataPermissionService.getAccountDataPermissionDeptIdList(account));
        }
        return accountResult;
    }

    @Override
    public Account getAccountByLogin(String loginAccount) {

        return accountMapper.selectByLoginAccount(loginAccount);
    }

    @Override
    public List<AccountResult> exportAccountList(AccountSearchForm accountSearchForm) {

        List<AccountResult> accountResultList = accountMapper.getAccountList(accountSearchForm);
        for (Object object : accountResultList) {
            AccountResult accountResult = (AccountResult) object;
            this.improveAccInfo(accountResult);
        }
        return accountResultList;
    }

    /**
     * 对员工信息进行完善
     *
     * @param accountResult
     */
    public void improveAccInfo(AccountResult accountResult) {
        if (null != accountResult) {
            //获取创建员工姓名
            if (null != accountResult.getCreateEmp()) {
                Long empId = accountResult.getCreateEmp();
                accountResult.setCreateEmpName(ComUtil.getEmpStr(empId));
            }
            //获取修改员工姓名
            if (null != accountResult.getModifyEmp()) {
                Long empId = accountResult.getModifyEmp();
                accountResult.setModifyEmpName(ComUtil.getEmpStr(empId));
            }
        }
    }

    @Override
    public PageResult getValidAccountList(Integer pageNum, Integer pageSize, AccountSearchForm accountSearchForm){
        //使用 PageHelper插件进行分页
        Page page = PageHelper.startPage(pageNum, pageSize);
        accountMapper.getValidAccountList(accountSearchForm);
        return PageUtil.getPageResult(page);
    }

}
