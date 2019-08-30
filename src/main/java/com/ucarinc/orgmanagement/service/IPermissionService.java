package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.exception.GlobalException;
import com.ucarinc.orgmanagement.entity.form.AccountPermissionForm;
import com.ucarinc.orgmanagement.entity.form.GrantRoleResourceForm;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.entity.vo.PermissionTreeResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * description:账号权限Service接口
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-05 11:16
 */
public interface IPermissionService {

    /**
     * description:执行账号权限分配
     *
     * @param accountPermissionForm 请求参数
     * @return com.ucarinc.orgmanagement.entity.vo.Result
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/10 9:41
     */
    @Transactional
    Result updateAccountPermission(AccountPermissionForm accountPermissionForm);

    /**
     * description:授予账号角色
     *
     * @param loginAccount    登录账号
     * @param grantRoleIdList 待授予角色IdList
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 13:53
     */
    @Transactional
    int grantRoleToAccount(String loginAccount, List<Long> grantRoleIdList) throws GlobalException;

    /**
     * description:撤销账号角色
     *
     * @param loginAccount     登录账号
     * @param revokeRoleIdList 待撤销角色IdList
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/10 9:12
     */
    @Transactional
    int revokeRoleFromAccount(String loginAccount, List<Long> revokeRoleIdList);

    /**
     * description:授予账号特殊权限
     *
     * @param loginAccount        登录账号
     * @param grantResourceIdList 授予资源权限Id list
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 13:24
     */
    @Transactional
    int grantResourceToAccount(String loginAccount, List<Long> grantResourceIdList);

    /**
     * description:撤销账号特殊资源权限
     *
     * @param loginAccount         登录账号
     * @param revokeResourceIdList 撤销资源权限Id list
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 13:24
     */
    @Transactional
    int revokeResourceFromAccount(String loginAccount, List<Long> revokeResourceIdList);

    /**
    * description:执行角色分配权限操作
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/12 15:30
    * @param roleResourceForm 角色管理:权限分配页面Form
    * @return  com.ucarinc.orgmanagement.entity.vo.Result
    */
    @Transactional
    Result updateRoleResource(GrantRoleResourceForm roleResourceForm);

    /**
     * description:授予角色资源权限
     *
     * @param roleId              角色Id
     * @param grantResourceIdList 权限Id list
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 13:24
     */
    @Transactional
    int grantResourceToRole(Long roleId, List<Long> grantResourceIdList);

    /**
     * description:撤销角色权限
     *
     * @param roleId              角色Id
     * @param revokeResourceIdList 权限Id list
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 13:24
     */
    @Transactional
    int revokeResourceFromRole(Long roleId, List<Long> revokeResourceIdList);

    /**
     * description:获取指定账号的角色信息
     *
     * @param requestBody @requestBody获取request参数
     * @return com.ucarinc.orgmanagement.entity.vo.Result
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/8 16:47
     */
    Result getAccountRoleInfoByLoginAccount(Map<String, Object> requestBody);

    /**
     * description:获取指定账号已拥有特殊资源权限信息及系统资源权限树
     *
     * @param requestBody @requestBody获取request参数
     * @return com.ucarinc.orgmanagement.entity.vo.Result
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/8 16:47
     */
    Result getAccountPermissionInfoByLoginAccount(Map<String, Object> requestBody);

    /**
     * description:获取指定角色资源权限信息
     *
     * @param roleId 角色Id
     * @return com.ucarinc.orgmanagement.entity.vo.Result
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/10 13:40
     */
    Result getRoleResourceInfo(Long roleId);

    /**
     * description:依照登录账号获取账号特殊权限编号
     *
     * @param loginAccount String 登录账号
     * @return java.util.List<java.lang.String>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/8 14:05
     */
    List<String> getAccountPermissionByLoginAccount(String loginAccount);

    /**
     * description:获取完整系统权限树
     *
     * @return java.util.List<com.ucarinc.orgmanagement.entity.vo.PermissionTree> 系统权限树
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/8 14:10
     */
    List<PermissionTreeResult> getSystemPermissionTree();
}
