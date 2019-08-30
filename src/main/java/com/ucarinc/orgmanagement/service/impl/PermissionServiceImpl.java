package com.ucarinc.orgmanagement.service.impl;

import com.ucarinc.orgmanagement.common.Constant;
import com.ucarinc.orgmanagement.dao.*;
import com.ucarinc.orgmanagement.entity.AccountResource;
import com.ucarinc.orgmanagement.entity.AccountRole;
import com.ucarinc.orgmanagement.entity.Role;
import com.ucarinc.orgmanagement.entity.RoleResource;
import com.ucarinc.orgmanagement.entity.dto.RoleDTO;
import com.ucarinc.orgmanagement.entity.exception.GlobalException;
import com.ucarinc.orgmanagement.entity.form.AccountPermissionForm;
import com.ucarinc.orgmanagement.entity.form.GrantRoleResourceForm;
import com.ucarinc.orgmanagement.entity.vo.PermissionTreeResult;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.security.shiro.realm.AccountRealm;
import com.ucarinc.orgmanagement.service.IHistoryService;
import com.ucarinc.orgmanagement.service.IPermissionService;
import com.ucarinc.orgmanagement.service.IShiroSessionService;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * description:账号权限Service接口实现类
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-08 15:18
 */
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private AccountRoleMapper accountRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private AccountResourceMapper accountResourceMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    @Qualifier("systemPermissionTree")
    private List<PermissionTreeResult> systemPermissionTree;
    @Autowired
    private IHistoryService historyService;
    @Autowired
    private AccountRealm accountRealm;
    @Autowired
    private IShiroSessionService shiroSessionService;

    private Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Override
    public Result updateAccountPermission(AccountPermissionForm accountPermissionForm) {
        String loginAccount = accountPermissionForm.getLoginAccount();
        logger.info("权限分配影响账号 : " + loginAccount);
        try {
            //添加角色
            if (!accountPermissionForm.getGrantRoleIdList().isEmpty()) {
                logger.debug("即将执行角色分配");
                grantRoleToAccount(loginAccount, accountPermissionForm.getGrantRoleIdList());
                logger.debug("角色分配完成");

            }
            //撤销角色
            if (!accountPermissionForm.getRevokeRoleIdList().isEmpty()) {
                logger.debug("即将执行角色移除");
                revokeRoleFromAccount(loginAccount, accountPermissionForm.getRevokeRoleIdList());
                logger.debug("角色移除完成");
            }
            //添加特殊权限
            if (!accountPermissionForm.getGrantPermissionIdList().isEmpty()) {
                logger.debug("即将执行特殊权限分配");
                grantResourceToAccount(loginAccount, accountPermissionForm.getGrantPermissionIdList());
                logger.debug("特殊权限分配完成");
            }
            //撤销特殊权限
            if (!accountPermissionForm.getRevokePermissionIdList().isEmpty()) {
                logger.debug("即将执行特殊权限移除");
                revokeResourceFromAccount(loginAccount, accountPermissionForm.getRevokePermissionIdList());
                logger.debug("特殊权限移除完成");
            }
            //插入历史记录
            historyService.addAccHistory(ShiroUtil.getActiveAccountInfo(), 6);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GlobalException(500, "数据库错误!");
        }
        //清除shiro缓存
        logger.debug("即将清除权限缓存");
        accountRealm.clearCached();
        logger.debug("清除权限缓存完成");
        //杀死对应账号session,强制下线,重新加载权限
        shiroSessionService.killSessionByLoginAccount(loginAccount);
        return ResultUtil.success();
    }

    @Override
    public int grantRoleToAccount(String loginAccount, List<Long> grantRoleIdList) throws GlobalException {
        Long empId = ShiroUtil.getActiveEmpId();
        List<AccountRole> insertRoleList = new ArrayList<>();
        for (Long roleId : grantRoleIdList) {
            AccountRole accountRole = new AccountRole();
            accountRole.setAccountLogin(loginAccount);
            accountRole.setRoleId(roleId);
            accountRole.setCreateEmp(empId);
            accountRole.setCreateTime(new Date());
            accountRole.setModifyEmp(empId);
            accountRole.setModifyTime(new Date());
            accountRole.setIsDelete((byte) Constant.IS_DELETED_NO);
            insertRoleList.add(accountRole);
        }
        try {
            return accountRoleMapper.batchInsert(insertRoleList, empId);
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GlobalException(500, "数据库错误!");
        }
    }

    @Override
    public int revokeRoleFromAccount(String loginAccount, List<Long> revokeRoleIdList) {
        Long empId = ShiroUtil.getActiveEmpId();
        try {
            return accountRoleMapper.batchLogicalDelete(loginAccount, revokeRoleIdList, empId);
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GlobalException(500, "数据库错误!");
        }
    }

    @Override
    public int grantResourceToAccount(String loginAccount, List<Long> grantResourceIdList) {
        Long activeEmpId = ShiroUtil.getActiveEmpId();
        List<AccountResource> insertResourceList = new ArrayList<>();
        for (Long resourceId : grantResourceIdList) {
            AccountResource accountResource = new AccountResource();
            accountResource.setAccountLogin(loginAccount);
            accountResource.setResourceId(resourceId);
            accountResource.setIsDelete((byte) Constant.IS_DELETED_NO);
            accountResource.setCreateEmp(activeEmpId);
            accountResource.setCreateTime(new Date());
            accountResource.setModifyEmp(activeEmpId);
            accountResource.setModifyTime(new Date());
            insertResourceList.add(accountResource);
        }
        try {
            return accountResourceMapper.batchInsert(insertResourceList, activeEmpId);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GlobalException(500, "数据库错误!");
        }
    }

    @Override
    public int revokeResourceFromAccount(String loginAccount, List<Long> revokeResourceIdList) {
        Long activeEmpId = ShiroUtil.getActiveEmpId();
        try {
            return accountResourceMapper.batchLogicalDelete(loginAccount, revokeResourceIdList, activeEmpId);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GlobalException(500, "数据库错误!");
        }
    }

    @Override
    public Result updateRoleResource(GrantRoleResourceForm roleResourceForm) {
        Long roleId = roleResourceForm.getRoleId();
        try {
            if (!roleResourceForm.getRevokeResourceIdList().isEmpty()) {
                revokeResourceFromRole(roleId, roleResourceForm.getRevokeResourceIdList());
            }
            if (!roleResourceForm.getGrantResourceIdList().isEmpty()) {
                grantResourceToRole(roleId, roleResourceForm.getGrantResourceIdList());
            }
            historyService.addAccHistory(ShiroUtil.getActiveAccountInfo(), 7);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GlobalException(500, "数据库错误!");
        }
        //清除shiro缓存
        accountRealm.clearCached();
        return ResultUtil.success();
    }

    @Override
    public int grantResourceToRole(Long roleId, List<Long> grantResourceIdList) {
        Long activeEmpId = ShiroUtil.getActiveEmpId();
        List<RoleResource> grantResourceList = new ArrayList<>();
        for (Long resourceId : grantResourceIdList) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            roleResource.setIsDelete(Constant.IS_DELETED_NO);
            roleResource.setCreateEmp(activeEmpId);
            roleResource.setcreateTime(new Date());
            roleResource.setModifyEmp(activeEmpId);
            roleResource.setModifyTime(new Date());
            grantResourceList.add(roleResource);
        }
        try {
            return roleResourceMapper.batchInsert(grantResourceList, activeEmpId);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GlobalException(500, "数据库错误!");
        }
    }

    @Override
    public int revokeResourceFromRole(Long roleId, List<Long> revokeResourceIdList) {
        Long activeEmpId = ShiroUtil.getActiveEmpId();
        try {
            return roleResourceMapper.batchLogicalDelete(roleId, revokeResourceIdList, activeEmpId);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GlobalException(500, "数据库错误!");
        }
    }

    @Override
    public Result getAccountRoleInfoByLoginAccount(Map<String, Object> requestBody) {
        String activeLoginAccount = (String) requestBody.get("loginAccount");
        //总取角色信息
        List<Role> dataRoleList = roleMapper.selectAllRoleIdAndName();
        //账户拥有角色Id
        List<Long> accountRoleIdList = accountRoleMapper.selectAccountRoleIdListByLoginAccount(activeLoginAccount);
        List<RoleDTO> roleDTOList = new ArrayList<>();
        //待分配角色Index
        List<Long> unassignedRoleIdList = new ArrayList<>();
        //包装为DTO
        for (Role role : dataRoleList) {
            RoleDTO roleDTO = new RoleDTO();
            Long roleId = role.getRoleId();
            //转为DTO
            roleDTO.setRoleId(roleId);
            roleDTO.setRoleName(role.getRoleName());
            roleDTOList.add(roleDTO);
            //用户未拥有任何角色?
            if (!accountRoleIdList.isEmpty()) {
                //用户是否拥有当前角色
                if (!accountRoleIdList.contains(roleId)) {
                    //未拥有,加入未分配
                    unassignedRoleIdList.add(roleId);
                }
            } else {
                //用户无任何角色,加入未分配
                unassignedRoleIdList.add(roleId);
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleList", roleDTOList);
        resultMap.put("accountRoleIdList", accountRoleIdList);
        resultMap.put("unassignedRoleIdList", unassignedRoleIdList);
        return ResultUtil.success(resultMap);
    }

    @Override
    public Result getAccountPermissionInfoByLoginAccount(Map<String, Object> requestBody) {
        String loginAccount = (String) requestBody.get("loginAccount");
        List<String> accountPermissionList = getAccountPermissionByLoginAccount(loginAccount);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("permissionTree", systemPermissionTree);
        resultMap.put("accountPermissionList", accountPermissionList);
        return ResultUtil.success(resultMap);
    }

    @Override
    public Result getRoleResourceInfo(Long roleId) {
        List<Long> roleResourceIdList = roleResourceMapper.selectResourceIdByRoleId(roleId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("sysResourceTree", systemPermissionTree);
        resultMap.put("roleResourceIdList", roleResourceIdList);
        return ResultUtil.success(resultMap);
    }

    @Override
    public List<String> getAccountPermissionByLoginAccount(String loginAccount) {
        return accountResourceMapper.selectAccountResourceIdByLoginAccount(loginAccount);
    }

    @Override
    public List<PermissionTreeResult> getSystemPermissionTree() {
        return systemPermissionTree;
    }

}
