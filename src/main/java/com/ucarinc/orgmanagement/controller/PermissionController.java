package com.ucarinc.orgmanagement.controller;

import com.ucarinc.orgmanagement.entity.form.AccountPermissionForm;
import com.ucarinc.orgmanagement.entity.form.GrantRoleResourceForm;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.IPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * description:权限相关Controller
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-10 14:08
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    private Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private IPermissionService permissionService;

    /**
    * description:账号管理:分配权限:获取指定账号特殊资源权限信息
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/12 15:11
    * @return  com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequiresPermissions("account:grantPermission")
    @RequestMapping("/getAccountPermissionInfo")
    @ResponseBody
    public Result getAccountPermissionInfo(@RequestBody Map<String, Object> requestBody) {
        logger.info("==调用获取账号特殊资源权限信息方法==");
        return permissionService.getAccountPermissionInfoByLoginAccount(requestBody);
    }

    /**
     * description:账号管理:分配权限:获取指定账号角色信息
     * @author dihan.chen(dihan.chen@ucarinc.com)
     * @date 2019/8/12 15:11
     * @return  com.ucarinc.orgmanagement.entity.vo.Result
     */
    @RequiresPermissions("account:grantPermission")
    @RequestMapping("/getAccountRoleInfo")
    @ResponseBody
    public Result getAccountRoleInfo(@RequestBody Map<String, Object> requestBody) {
        logger.info("==调用获取账号角色信息方法==");
        return permissionService.getAccountRoleInfoByLoginAccount(requestBody);
    }

    /**
     * description:账号管理:分配权限:更新账号角色及特殊资源权限
     * @author dihan.chen(dihan.chen@ucarinc.com)
     * @date 2019/8/12 15:11
     * @return  com.ucarinc.orgmanagement.entity.vo.Result
     */
    @RequiresPermissions("account:grantPermission")
    @RequestMapping("/grantAccountRoleAndPermission")
    @ResponseBody
    public Result grantAccountPermission(@RequestBody AccountPermissionForm accountPermissionForm) {
        logger.info("==调用账号权限分配方法==");
        return permissionService.updateAccountPermission(accountPermissionForm);
    }

    /**
    * description:角色管理:分配权限:获取指定角色拥有资源id与总资源树
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/10 14:26
    * @param requestBody 请求参数
    * @return  com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequiresPermissions("role:permission")
    @RequestMapping("/getRoleResourceInfo")
    @ResponseBody
    public Result getRoleResourceInfo(@RequestBody Map<String,Object> requestBody){
        Long roleId = Long.valueOf(requestBody.get("roleId").toString());
        return permissionService.getRoleResourceInfo(roleId);
    }

    /**
     * description:角色管理:分配权限:更新指定角色拥有资源权限信息
     * @author dihan.chen(dihan.chen@ucarinc.com)
     * @date 2019/8/10 14:26
     * @param grantRoleResourceForm 表单提交参数
     * @return  com.ucarinc.orgmanagement.entity.vo.Result
     */
    @RequiresPermissions("role:permission")
    @RequestMapping("/grantRoleResource")
    @ResponseBody
    public Result grantRoleResource(@RequestBody GrantRoleResourceForm grantRoleResourceForm){
        return permissionService.updateRoleResource(grantRoleResourceForm);
    }
}
