package com.ucarinc.orgmanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.ucarinc.orgmanagement.entity.AccountPermission;
import com.ucarinc.orgmanagement.entity.RoleAccountDetail;
import com.ucarinc.orgmanagement.entity.form.RoleResourceForm;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.IAccountPermissionService;
import com.ucarinc.orgmanagement.service.IRoleAccountDetailService;
import com.ucarinc.orgmanagement.service.IRoleResourceDetailService;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date 2019/8/7
 * @Time 15:05
 * @Description 查询controller
 * @Author jiajin.chen@ucarinc.com
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    private Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private IRoleAccountDetailService IRoleAccountDetailService;
    @Autowired
    private IAccountPermissionService accountPermissionService;

    @Autowired
    private IRoleResourceDetailService roleResourceDetailService;

    /**
     * description:<角色账户明细查询分页方法>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 12:45
     * @param request
     * @param pageNum
     * @param pageSize
     * @param roleAccountDetailStr
     * @return com.ucarinc.orgmanagement.entity.vo.Result <返回值说明>
     */
    @RequiresPermissions("searchAccountDetail:search")
    @RequestMapping(value = "/role/getRoleAccountList", method = RequestMethod.GET)
    public Result getRoleAccountList(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("roleAccountDetail") String roleAccountDetailStr) {
        logger.info("==调用获取角色账户明细查询分页方法==");
        RoleAccountDetail roleAccountDetail= JSONObject.parseObject(roleAccountDetailStr,RoleAccountDetail.class);
        return ResultUtil.success(IRoleAccountDetailService.getRoleAccountList(pageNum, pageSize,roleAccountDetail));
    }

    /**
     * description:<角色账户明细查询方法，不分页用于导出>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:35
     * @param request
     * @param roleAccountDetailStr
     * @return com.ucarinc.orgmanagement.entity.vo.Result <返回值说明>
     */
    @RequiresPermissions("searchAccountDetail:export")
    @RequestMapping(value = "/role/exportRoleAccountList", method = RequestMethod.GET)
    public Result exporRoleAccountList(HttpServletRequest request, @RequestParam("roleAccountDetail") String roleAccountDetailStr) {
        logger.info("==调用获取角色账户明细方法==");
        RoleAccountDetail roleAccountDetail= JSONObject.parseObject(roleAccountDetailStr,RoleAccountDetail.class);
        return ResultUtil.success(IRoleAccountDetailService.getRoleAccountList(roleAccountDetail));
    }

    /**
     * description:<获取角色权限明细查询分页方法>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:35
     * @param request
     * @param pageNum
     * @param pageSize
     * @param roleStr
     * @return com.ucarinc.orgmanagement.entity.vo.Result <返回值说明>
     */
    @RequiresPermissions("searchPermissionDetail:search")
    @RequestMapping(value = "/role/getRolePermissionList")
    public Result getRoleResourceList(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, @RequestParam("role") String roleStr) {
        logger.info("==调用获取角色权限查询分页方法==");
        RoleResourceForm roleResourceForm = JSONObject.parseObject(roleStr, RoleResourceForm.class);
        return ResultUtil.success(roleResourceDetailService.getRoleResourceList(pageNum, pageSize, roleResourceForm));
    }

    /**
     * description:<获取角色权限明细查询方法，不分页用于导出>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:35
     * @param request
     * @param roleStr
     * @return com.ucarinc.orgmanagement.entity.vo.Result <返回值说明>
     */
    @RequiresPermissions("searchPermissionDetail:export")
    @RequestMapping(value = "/role/exportRoleResourceList")
    public Result roleResourceList(HttpServletRequest request, @RequestParam("role") String roleStr) {
        logger.info("==调用获取角色权限查询方法==");
        RoleResourceForm roleResourceForm = JSONObject.parseObject(roleStr,RoleResourceForm.class);
        return ResultUtil.success(roleResourceDetailService.roleResourceList(roleResourceForm));
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param accountPermissionStr
     * @return
     */
    @RequiresPermissions("search:accountPermission")
    @RequestMapping(value = "/getAccountPermissionList", method = RequestMethod.GET)
    @ResponseBody
    public Result getAccountPermissionList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("accountPermission") String accountPermissionStr){
        logger.info(accountPermissionStr);
        AccountPermission accountPermission = JSONObject.parseObject(accountPermissionStr, AccountPermission.class);
        logger.info("账号功能查询条件："+accountPermission);
        return ResultUtil.success(accountPermissionService.getAccountPermissionList(accountPermission,pageNum,pageSize));
        }

    /**
     *
     * @param accountPermissionStr
     * @return
     */
    @RequiresPermissions("searchPermissionDetail:export")
    @RequestMapping(value = "/exportAccountPermissionListAll")
    public Result AccountPermissionList( @RequestParam("accountPermission") String accountPermissionStr) {
        AccountPermission accountPermission = JSONObject.parseObject(accountPermissionStr,AccountPermission.class);
        return ResultUtil.success(accountPermissionService.accountPermissionList(accountPermission));
    }
}



