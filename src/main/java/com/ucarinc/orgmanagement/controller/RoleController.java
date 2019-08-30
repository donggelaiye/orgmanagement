package com.ucarinc.orgmanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.ucarinc.orgmanagement.entity.*;
import com.ucarinc.orgmanagement.entity.form.*;
import com.ucarinc.orgmanagement.entity.form.RoleAccountForm;
import com.ucarinc.orgmanagement.entity.form.RoleApproveForm;
import com.ucarinc.orgmanagement.entity.vo.AccountResult;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.IDataPermissionService;
import com.ucarinc.orgmanagement.service.IRoleApplyService;
import com.ucarinc.orgmanagement.service.IRoleService;
import com.alibaba.fastjson.JSON;
import com.ucarinc.orgmanagement.service.IRoleApproveService;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description:
 * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
 * @date: 2019/8/3 10:14
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleApplyService IRoleApplyService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleApproveService roleApproveService;

    @RequiresPermissions("roleApply:getList")
    @RequestMapping(value = "/apply/getApplyList", method = RequestMethod.POST)
    public Result getRoleApplyList(HttpServletRequest request, @RequestBody String roleApplyFormJSON) {
        JSONObject parms = JSON.parseObject(roleApplyFormJSON);
        RoleApplySearchForm roleApplyForm = JSON.parseObject((String) parms.get("roleApplyForm"), RoleApplySearchForm.class);
        logger.debug("==角色申请查询条件==" + roleApplyForm.toString());
        Integer pageSize = (Integer) parms.get("pageSize");
        Integer pageNum = (Integer) parms.get("pageNum");
        logger.info("==调用获取角色申请分页方法==");
        if (roleApplyForm.getStatus() == 5) {
            roleApplyForm.setStatus(null);
        }
        return ResultUtil.success(IRoleApplyService.getRoleApplyList(pageNum, pageSize, roleApplyForm));

    }

    /**
     * @Description: 根据角色ID获取角色选择回填的信息，返回RoleInfoVO
     * @Author: 徐才艺(caiyi.xu @ ucarinc.com)
     * @date: 2019/8/8 10:42
     * @Param: roleId 角色id
     * @return:
     */
    @RequestMapping(value = "/apply/getRoleInfo", method = RequestMethod.POST)
    public Result getRoleInfo(HttpServletRequest request, @RequestBody String roleJSON) {
        System.out.println(roleJSON);
        Result getRoleInfoRes = IRoleApplyService.getRoleInfoByRole(roleJSON);
        return getRoleInfoRes;
    }

    /**
     * @Description: 获取角色申请信息详情
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/9 16:50
     * @param applyCode 角色申请编号
     * @return: json {pageInfo: roleApply},{accountData:RoleApplyAccountForm[]}（数组包含申请账号的信息）
     */
    @RequestMapping(value = "/apply/showApplyDetail", method = RequestMethod.GET)
    public Result showApplyDetail(HttpServletRequest request, @RequestParam("applyCode") String applyCode) {
        String infoJson = IRoleApplyService.getRoleApplyInfoByApplyCode(applyCode);
        return ResultUtil.success(infoJson);
    }

    /**
     * 保存角色申请详情
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/10 11:19
     * @param newPageInfoJSON "roleApply"：｛角色订单表的信息｝ "roleApplyAccountForm"数组：[{包含applyAccount的基本信息}]
     * @return:
     */
    @RequiresPermissions("roleApply:addApply")
    @RequestMapping(value = "/apply/saveApply", method = RequestMethod.POST)
    public Result saveApply(HttpServletRequest request, @RequestBody String newPageInfoJSON) {
        Result res  = IRoleApplyService.addApply(newPageInfoJSON);
        return res;
    }

    /**
     * 更新角色申请详情
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/10 11:19
     * @param newPageInfoJSON "roleApply"：｛角色订单表的信息｝ "roleApplyAccountForm"数组：[{包含roleApplyAccount的基本信息}]
     * @return:
     */
    @RequiresPermissions("roleApply:updateApply")
    @RequestMapping(value = "/apply/updateApply", method = RequestMethod.POST)
    public Result updateApply(HttpServletRequest request, @RequestBody String newPageInfoJSON) {
        Result res  = IRoleApplyService.updateApply(newPageInfoJSON);
        logger.debug("执行角色申请更新");
        System.out.println(newPageInfoJSON);
        return ResultUtil.success();
    }

    /**
     * 批量删除角色申请
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/12 16:29
     * @param deleteItems 要删除的多条RoleApply数据
     * @return:
     */
    @RequiresPermissions("roleApply:deleteApply")
    @RequestMapping(value = "/apply/deleteApplys", method = RequestMethod.POST)
    public Result deleteApplys(HttpServletRequest request, @RequestBody String deleteItems) {
        int res = IRoleApplyService.deleteRoleApplys(deleteItems);
        return ResultUtil.success(res);
    }

    /**
     * 批量提交审核
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/12 17:22
     * @param approveItems 提交的多条审核数据
     * @return:
     */
    @RequiresPermissions("roleApply:submitApprove")
    @RequestMapping(value = "/apply/approveApplys", method = RequestMethod.POST)
    public Result apprvoeApplys(HttpServletRequest request, @RequestBody String approveItems) {
        IRoleApplyService.approveRoleApplys(approveItems);
        return ResultUtil.success();
    }

    /**
     * 角色修改页面中，删除添加账号
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/12 9:24
     * @param applyCode 申请订单编号
     * @param account  登录账号
     * @return:
     */
    @RequestMapping(value = "/apply/deleteAccount", method = RequestMethod.GET)
    public Result deleteAccount(HttpServletRequest request, @RequestParam("applyCode") String applyCode, @RequestParam("account") String account) {
        IRoleApplyService.deleteAccount(applyCode, account);
        return ResultUtil.success();
    }

    /**
     * 导出
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/12 8:24
     * @param  roleApplyFormJSON 查询条件
     * @return: 所有的数据
     */
    @RequestMapping(value = "/apply/exportApplyList", method = RequestMethod.POST)
    public Result exportApplyList(HttpServletRequest request, @RequestBody String roleApplyFormJSON) {
        JSONObject parms = JSON.parseObject(roleApplyFormJSON);
        RoleApplySearchForm roleApplyForm = JSON.parseObject(String.valueOf(parms.get("roleApplyForm")), RoleApplySearchForm.class);
        if (roleApplyForm.getStatus() == 5) {
            roleApplyForm.setStatus(null);
        }

        List<RoleApply> roleApplyList = IRoleApplyService.getExportData(roleApplyForm);
        return ResultUtil.success(roleApplyList);
    }

    /**
     * 根据角色名称 获取状态为有效的角色列表
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/20 16:00
     * @param roleName 角色名称
     * @param pageNum 第几页
     * @param pageSize 每页数量
     * @return:
     */
    @RequestMapping(value = "/apply/getValidRoleList", method = RequestMethod.GET)
    public Result getValidRoleList(HttpServletRequest request, @RequestParam(value = "roleName", required = false) String roleName, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return ResultUtil.success(IRoleApplyService.getValidRoleList(roleName, pageNum, pageSize));
    }

    /**
     *
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/21 10:03
     * @param parmsMap accounts:多个账号数组的JSON字符串
     * @return: 如果账号发生改变返回code702以及相应的提示消息，如果没有改变则返回List账号数据
     */
    @RequestMapping(value = "/apply/getAccountsInfo", method = RequestMethod.POST)
    public Result getAccountsInfo(HttpServletRequest request, @RequestBody Map<String,Object> parmsMap) {
        String accountsJSON = (String) parmsMap.get("accounts");
        System.out.println(accountsJSON);
        List<AccountResult> accounts = JSON.parseArray(accountsJSON, AccountResult.class);
        Result result = IRoleApplyService.getAccountsInfo(accounts);
        return result;
    }



    /**
     * description:<导出角色>
     * @author 周志钦（zhiqin.zhou@ucarinc.com）
     * @date 2019/8/11 23:21
     * @param <> <roleStr>
     * @return <>
     */
    @RequiresPermissions("role:exportRoleList")
    @RequestMapping(value = "/management/exportRoleList")
    public Result exportRoleList(HttpServletRequest request, @RequestParam("role") String roleStr) {
        Role role= JSONObject.parseObject(roleStr,Role.class);
        logger.info("==调用获取角色数据集合不分页方法==");
        return ResultUtil.success(roleService.getRoleListMes(role));
    }

    /**
     * description:<获得角色列表>
     * @author 周志钦（zhiqin.zhou@ucarinc.com）
     * @date 2019/8/11 23:24
     * @param <> <角色名称>
     * @return <返回角色列表>
     */
    @RequiresPermissions("role:getList")
    @RequestMapping(value = "/management/getRoleList", method = RequestMethod.GET)
    public Result getRoleList(HttpServletRequest request, @RequestParam(value = "roleName", required = false) String roleName, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        logger.info("==调用获取角色列表分页方法==");
        return ResultUtil.success(roleService.getRoleList(roleName, pageNum, pageSize));
    }

    /**
    * description:<根据角色ID获取角色信息>
    * @author 周志钦（zhiqin.zhou@ucarinc.com）
    * @date 2019/8/11 23:25
    * @param <> <角色ID>
    * @return <返回该角色信息>
    */
    @RequiresPermissions("role:updateRole")
    @RequestMapping(value = "/management/getRole", method = RequestMethod.GET)
    public Result getRoleList(HttpServletRequest request, @RequestParam("roleId") Long roleId) {
        logger.info("==调用由角色ID获取角色方法==");
        return ResultUtil.success(roleService.getRole(roleId));
    }

    /**
    * description:<删除角色>
    * @author 周志钦（zhiqin.zhou@ucarinc.com）
    * @date 2019/8/11 23:25
    * @param <> <角色ID>
    * @return <>
    */
    @RequiresPermissions("role:deleteRole")
    @RequestMapping(value = "/management/deleteRole", method = RequestMethod.GET)
    public Result deleteRole(HttpServletRequest request, @RequestParam("roleId") Long roleId) {
        logger.info("==调用删除角色的方法==");
        //可手动抛出异常，方法如下
        /*throw new GlobalException(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());*/
        /*roleService.deleteRole(roleId);*/
        return ResultUtil.success(roleService.deleteRole(roleId));
    }

    /**
    * description:<新建角色>
    * @author 周志钦（zhiqin.zhou@ucarinc.com）
    * @date 2019/8/11 23:26
    * @param <> <>
    * @return <>
    */
    @RequiresPermissions("role:addRole")
    @RequestMapping(value = "/management/addRole", method = RequestMethod.POST)
    public Result addRole(HttpServletRequest request, @RequestBody String insertJSON) {
        logger.info("==新建角色的方法==");
        int res = roleService.addRole(insertJSON);
        return ResultUtil.success(res);
    }
    /**
    * description:<修改角色>
    * @author 周志钦（zhiqin.zhou@ucarinc.com）
    * @date 2019/8/23 9:33
    * @param <> <参数说明>
    * @return <返回修改结果>
    */
    @RequiresPermissions("role:updateRole")
    @RequestMapping(value = "/management/updateRole", method = RequestMethod.POST)
    public Result updateRole(HttpServletRequest request, @RequestBody String updateJSON) {
        logger.info("==修改角色的方法==");
        Result result = roleService.updateRole(updateJSON);
        return result;
    }

    /**
    * description:<检查角色是否存在>
    * @author 周志钦（zhiqin.zhou@ucarinc.com）
    * @date 2019/8/11 23:40
    * @param <> <角色名称>
    * @return <返回值说明>
    */
    @RequiresPermissions(value={"role:addRole","role:updateRole"},logical = Logical.OR)
    @RequestMapping(value = "/management/checkRoleName", method = RequestMethod.GET)
    public Result checkRoleName(@RequestParam("roleName") String roleName) {
        logger.info("==执行检测角色是否存在方法==");
        return ResultUtil.success(roleService.checkRoleName(roleName));
    }

    /**
     * description:<检查审批人是否被删除>
     * @author 周志钦（zhiqin.zhou@ucarinc.com）
     * @date 2019/8/11 23:41
     * @param <> <参数说明>
     * @return <返回值说明>
     */
   @RequiresPermissions(value={"role:addRole","role:updateRole"},logical = Logical.OR)
    @RequestMapping(value = "/management/checkApproveAccount", method = RequestMethod.GET)
    public Result checkApproveAccount(@RequestParam("approveAccount") String approveAccount) {
        logger.info("==展示全部业务线方法==");
        return ResultUtil.success(roleService.checkApproveAccount(approveAccount));
    }

    /**
    * description:<获得所有业务线>
    * @author 周志钦（zhiqin.zhou@ucarinc.com）
    * @date 2019/8/11 23:41
    * @param <> <参数说明>
    * @return <返回值说明>
    */
    @RequiresPermissions(value={"role:addRole","role:updateRole"},logical = Logical.OR)
    @RequestMapping(value = "/management/getLineList", method = RequestMethod.GET)
    public Result getLineList(HttpServletRequest request) {
        logger.info("==展示全部业务线方法==");
        return ResultUtil.success(roleService.getLineList());
    }

    /**
    * description:<根据角色ID获取业务线ID>
    * @author 周志钦（zhiqin.zhou@ucarinc.com）
    * @date 2019/8/11 23:41
    * @param <> <参数说明>
    * @return <返回值说明>
    */
    @RequiresPermissions("role:updateRole")
    @RequestMapping(value = "/management/getLineId", method = RequestMethod.GET)
    public Result getLineId(HttpServletRequest request,@RequestParam("roleId") Long roleId) {
        logger.info("==展示角色ID获取业务线ID方法==");
        return ResultUtil.success(roleService.getLineId(roleId));
    }

    /**
     * description:<获取角色申请审批分页方法>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:43
     * @param request
     * @param pageNum
     * @param pageSize
     * @param roleApproveFormStr
     * @return com.ucarinc.orgmanagement.entity.vo.Result <返回值说明>
     */
    @RequiresPermissions("roleApprove:getList")
    @RequestMapping(value = "/approve/getApproveList", method = RequestMethod.GET)
    public Result getRoleApproveList(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("roleApproveForm") String roleApproveFormStr) {
        logger.info("==调用获取角色申请审批分页方法==");
        RoleApproveForm roleApproveForm=JSONObject.parseObject(roleApproveFormStr,RoleApproveForm.class);
        return ResultUtil.success(roleApproveService.getRoleApproveList(pageNum, pageSize,roleApproveForm));
    }

    /**
     * description:<获取角色申请详情页方法>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:43
     * @param request
     * @param applyCode
     * @return com.ucarinc.orgmanagement.entity.vo.Result <返回值说明>
     */
    @RequiresPermissions("roleApprove:getApprove")
    @RequestMapping(value = "/approve/getApprove", method = RequestMethod.GET)
    public Result getApprove(HttpServletRequest request, @RequestParam("applyCode")  String applyCode) {
        logger.info("==调用获取角色申请详情页功能，用于展示角色申请审核详情页==");
        String infoJson = IRoleApplyService.getRoleApplyInfoByApplyCode(applyCode);
        return ResultUtil.success(infoJson);
    }

    /**
     * description:<审批拒绝>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:44
     * @param request
     * @param RoleApproveRefuseStr
     * @return com.ucarinc.orgmanagement.entity.vo.Result <返回值说明>
     */
    @RequiresPermissions("roleApprove:getApprove")
    @RequestMapping(value = "/approve/approveRefuse", method = RequestMethod.GET)
    public Result approveRefuse(HttpServletRequest request, @RequestParam("RoleApproveRefuse") String RoleApproveRefuseStr) {
        logger.info("==调用角色审批拒绝方法==");
        RoleApprove roleApprove =JSONObject.parseObject(RoleApproveRefuseStr,RoleApprove.class);
        //获取审批人
        String roleApproveName = roleApprove.getRoleApproveName();
        String accountName = ShiroUtil.getActiceLoginAccount();
        String approveAccountName = accountName + "(" +roleApproveName+ ")";
        roleApprove.setApproveAccountName(approveAccountName);
        roleApproveService.approveRefuse(roleApprove);
        return ResultUtil.success();
    }

    /**
     * description:<审批通过>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:44
     * @param request
     * @param approveJSON
     * @return com.ucarinc.orgmanagement.entity.vo.Result <返回值说明>
     */
    @RequiresPermissions("account:grantPermission")
    @RequestMapping(value = "/approve/approveAgree", method = RequestMethod.POST)
    public Result approveAgree(HttpServletRequest request, @RequestBody String approveJSON) {
        System.out.println(approveJSON);
        logger.info("==调用角色审批通过方法==");
        roleApproveService.approveAgree(approveJSON);
        return ResultUtil.success();
    }

    /**
     * description:<获得角色关联账号列表>
     * @author 周志钦（zhiqin.zhou@ucarinc.com）
     * @date 2019/8/13 11:11
     * @param <> <角色ID名称>
     * @return <返回角色账号列表>
     */
    @RequiresPermissions("role:addRoleAccountList")
    @RequestMapping(value = "/management/getRoleAccountList", method = RequestMethod.POST)
    public Result getRoleAccountList(HttpServletRequest request, @RequestBody String roleAccountJSON) {
        logger.info("==调用获取角色列表分页方法==");
        JSONObject jsonObject = JSON.parseObject(roleAccountJSON);
        Integer pageNum = (Integer) jsonObject.get("pageNum");
        Integer pageSize = (Integer) jsonObject.get("pageSize");
        RoleAccountForm roleAccountForm = jsonObject.getObject("roleAccount", RoleAccountForm.class);
        return ResultUtil.success(roleService.getRoleAccountList(roleAccountForm, pageNum, pageSize));
    }

    /**
     * description:<删除角色账号>
     * @author 周志钦（zhiqin.zhou@ucarinc.com）
     * @date 2019/8/13 17:11
     * @param <> <角色ID名称>
     * @return <返回角色账号列表>
     */
    @RequiresPermissions("role:addRoleAccountList")
    @RequestMapping(value = "/management/deleteRoleAccountList", method = RequestMethod.POST)
    public Result deleteRoleAccountList(HttpServletRequest request, @RequestBody String jsonStr) {
        logger.info("==调用删除角色账号方法==");
        Result result = roleService.deleteRoleAccount(jsonStr);
        return result;
    }

    /**
     * description:<添加角色账号>
     * @author 周志钦（zhiqin.zhou@ucarinc.com）
     * @date 2019/8/13 17:11
     * @param <> <角色ID名称>
     * @return <返回角色账号列表>
     */
    @RequiresPermissions("role:addRoleAccountList")
    @RequestMapping(value = "/management/addRoleAccountList", method = RequestMethod.POST)
    public Result addRoleAccountList(HttpServletRequest request, @RequestBody Map<String,Object> parmsMap) {
        logger.info("==调用添加角色账号方法==");
        String accountsJSON = (String) parmsMap.get("accounts");
        Long roleId = Long.valueOf((String) parmsMap.get("roleId"));
        System.out.println(accountsJSON);
        System.out.println(roleId);
        List<AccountResult> accounts = JSON.parseArray(accountsJSON, AccountResult.class);
        Result result = roleService.addRoleAccount(accounts,roleId);
        return result;
    }
}
