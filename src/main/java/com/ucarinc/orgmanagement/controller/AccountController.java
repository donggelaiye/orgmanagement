package com.ucarinc.orgmanagement.controller;


import com.alibaba.fastjson.JSONObject;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.form.AccountForm;
import com.ucarinc.orgmanagement.entity.form.AccountModifyForm;
import com.ucarinc.orgmanagement.entity.form.AccountSearchForm;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.IAccountService;
import com.ucarinc.orgmanagement.service.IDataPermissionService;
import com.ucarinc.orgmanagement.service.IHistoryService;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * description:账号管理Controller
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IHistoryService historyService;
    @Autowired
    private IDataPermissionService dataPermissionService;



    @RequestMapping("/management/getAccountList")
    @RequiresPermissions("account:getList")
    //查询
    public Result getAccountList(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "account") String accountSearchForm) {
        AccountSearchForm accountSearchForm1 = JSONObject.parseObject(accountSearchForm, AccountSearchForm.class);
        logger.info("==调用获取账号分页方法==");
        accountSearchForm1.setGrantDeptList(dataPermissionService.getActiveAccountDataPermissionDeptIdList());
//        数据权限是本人,则列表只显示自己
        Account account = ShiroUtil.getActiveAccountInfo();
        if (3 == account.getDataPermissionId() ){
            accountSearchForm1.setAccountName(account.getAccountLogin());
        }
//        return ResultUtil.success(accountService.getAccountList(pageNum, pageSize));
        return ResultUtil.success(accountService.getAccountList(pageNum, pageSize, accountSearchForm1));
    }

    //获取密码
    @RequestMapping("/management/getAccountPassword")
    public Result getAccountPassword(String loginAccount, HttpServletRequest request) {

        logger.info("==调用获取密码的方法==");
        return ResultUtil.success(accountService.getAccountPassword(loginAccount));

    }

    //增加账户
    @RequestMapping("/management/addAccount")
    @RequiresPermissions("account:addAccount")
    public Result addAccount(@RequestBody AccountModifyForm account1, HttpServletRequest request) {
        //AccountModifyForm account1 = JSONObject.parseObject(account, AccountModifyForm.class);
        logger.info("==调用增加账号的方法==");
        //logger.info(account1.toString());
        accountService.addAccount(account1);
        logger.info("==调用插入历史记录的方法");
        historyService.addAccHistory(account1,0);
        return ResultUtil.success();
    }

    //修改账户
    @RequestMapping("/management/modifyAccount")
    @RequiresPermissions("account:modifyAccount")
    public Result modifyAccount(@RequestBody AccountModifyForm accountModifyForm, HttpServletRequest request) {
        logger.info("==调用修改账号的方法==");
        accountService.modifyAccount(accountModifyForm);
        logger.info("==调用插入历史记录的方法");
        historyService.addAccHistory(accountModifyForm,1);
        return ResultUtil.success();
    }

    //删除账号
    @RequestMapping("/management/deleteAccount")
    @RequiresPermissions("account:deleteAccount")
    public Result deleteAccount(@RequestParam(value = "accountLogin") String loginAccount, HttpServletRequest request) {
        logger.info("==调用删除的方法==");
        Account account = accountService.getAccountByLogin(loginAccount);
        logger.info("==调用插入历史记录的方法");
        historyService.addAccHistory(account,2);
        return ResultUtil.success(accountService.deleteAccount(loginAccount));
    }

    //冻结账号
    @RequestMapping("/management/FreezeAccount")
    @RequiresPermissions("account:freezeAccount")
    public Result FreezeAccount(@RequestParam(value = "accountLogin") String loginAccount, HttpServletRequest request) {
        logger.info("==调用冻结的方法==");
        Account account = accountService.getAccountByLogin(loginAccount);
        logger.info("==调用插入历史记录的方法");
        historyService.addAccHistory(account,3);
        return ResultUtil.success(accountService.freezeAccount(loginAccount));
    }

    //解冻账号
    @RequestMapping("/management/UnfreezeAccount")
    @RequiresPermissions("account:unfreezeAccount")
    public Result UnfreezeAccount(@RequestParam(value = "accountLogin") String loginAccount, HttpServletRequest request) {
        logger.info("==调用解冻的方法==");
        Account account = accountService.getAccountByLogin(loginAccount);
        logger.info("==调用插入历史记录的方法");
        historyService.addAccHistory(account,4);
        return ResultUtil.success(accountService.unfreezeAccount(loginAccount));
    }

    @RequestMapping("/management/IsLoginOnly")
    public Result IsLoginOnly(@RequestParam(value = "accountLogin") String loginAccount, HttpServletRequest request) {
        logger.info("==调用判断账号唯一的方法==");
        return ResultUtil.success(accountService.IsLoginOnly(loginAccount));
    }

    @RequestMapping("/management/getAccount")
    @RequiresPermissions("account:modifyAccount")
    public Result getAccount(@RequestParam(value="accountLogin") String loginAccount,HttpServletRequest request){
        logger.info("==调用获取账号==");
        return ResultUtil.success(accountService.getAccount(loginAccount));
    }

    //查找之前的部门信息
    @RequestMapping("/management/getAccountDataPermissionDept")
    public Result getAccountDataPermissionDeptIdList(@RequestParam(value="accountLogin") String accountLogin,@RequestParam(value = "dataPermissionId") String dataPermissionId, HttpServletRequest request){
        logger.info("==调用账号获取部门==");
        logger.info(accountLogin);
//        return ResultUtil.success(dataPermissionService.getAccountDataPermissionDeptIdList(accountList));
        return ResultUtil.success();
    }

    //详细信息
    @RequestMapping("/management/getAccountSelectByLogin")
    public Result getAccountSelectByLogin(@RequestParam(value = "accountLogin") String loginAccount, HttpServletRequest request) {
        logger.info("==调用通过accountLogin查找的的方法==");
        return ResultUtil.success(accountService.getAccountSelectByLogin(loginAccount));
    }

    //导出excel文件
    @RequestMapping(value = "/management/exportAccountList")
    @RequiresPermissions("account:exportAccount")
    public Result exportAccountList(HttpServletRequest request, @RequestParam("account") String accountSearchForm) {
        AccountSearchForm accountSearchForm1 = JSONObject.parseObject(accountSearchForm, AccountSearchForm.class);
        return ResultUtil.success(accountService.exportAccountList(accountSearchForm1));
    }

    @RequestMapping(value = "/management/getValidAccountList")
    public Result getValidAccountList(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "account") String accountStr){
        AccountSearchForm accountSearchForm = JSONObject.parseObject(accountStr, AccountSearchForm.class);
        logger.info("==调用获取有效账号分页方法==");
        return ResultUtil.success(accountService.getValidAccountList(pageNum, pageSize, accountSearchForm));
    }
}
