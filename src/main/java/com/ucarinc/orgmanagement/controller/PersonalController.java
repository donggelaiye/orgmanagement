package com.ucarinc.orgmanagement.controller;

import com.github.pagehelper.util.StringUtil;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.Employee;
import com.ucarinc.orgmanagement.entity.History;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.impl.AccountServiceImpl;
import com.ucarinc.orgmanagement.service.impl.EmployeeServiceImpl;
import com.ucarinc.orgmanagement.service.impl.HistoryServiceImpl;
import com.ucarinc.orgmanagement.utils.MailUtils;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ProjectName: orgmanagement
 * @Package: com.ucarinc.orgmanagement.controller
 * @ClassName: PersonalController
 * @Author: wtf
 * @Description: 个人账号管理模块
 * @Date: 2019/8/9 15:24
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/personal")
public class PersonalController {

    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private HistoryServiceImpl historyService;


    /***
     * @author wtf
     * @description  修改密码
     * @date 2019/8/12 11:17
     * @param accountLogin 登录账号
     * @param accountPwd 登录密码
     * @param newPwd 新密码
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/management/updatePwd")
    @ResponseBody
    @RequiresPermissions("empAccount:passModify")
    public Result updatePwd(@RequestParam("accountLogin") String accountLogin, @RequestParam("accountPwd") String accountPwd,@RequestParam("newPwd") String newPwd){
        String oldPwd = accountService.getAccountPassword(accountLogin);
        if (StringUtil.isNotEmpty(oldPwd) && StringUtil.isNotEmpty(accountPwd)){
            if (oldPwd.equals(accountPwd)){
                if (accountService.updatePassword(accountLogin,accountPwd)){
                    Account account = accountService.getAccountByLogin(accountLogin);
                    historyService.addAccHistory(account,1);
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(501,"修改密码失败");
                }
            } else {
                return ResultUtil.error(501,"输入的原始密码错误，请重新输入");
            }
        } else {
            return ResultUtil.error(501,"禁止输入空值");
        }
    }

    /***
     * @author wtf
     * @description 通过账号获取员工信息
     * @date 2019/8/12 11:18
     * @param accountLogin 登录账号
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/management/getEmployeeByAccountLogin")
    @ResponseBody
    @RequiresPermissions("empAccount:baseInfo")
    public Result getEmployeeByAccountLogin(@RequestParam("accountLogin") String accountLogin){
        return ResultUtil.success(employeeService.getEmployeeByAccount(accountLogin));
    }

    /***
     * @author wtf
     * @description  发送密码重置邮件
     * @date 2019/8/12 11:20
     * @param request 请求体
     * @param accountLogin 登录账号
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/management/sendResetPwdMail")
    @ResponseBody
    public Result sendResetPwdMail(HttpServletRequest request, @RequestParam("accountLogin") String accountLogin) throws MessagingException, IOException, javax.mail.MessagingException {
        Account account = accountService.getAccountByLogin(accountLogin);
        Employee employee = employeeService.getEmployeeByAccount(accountLogin);
        if (account != null){
            if (null != account.getAccountEmail()){
                MailUtils mailUtils = new MailUtils();
                if (null != employee){
                    if (mailUtils.sendResetPwdEmail(account.getAccountEmail(),accountLogin,employee.getEmpName())){
                        historyService.addAccHistory(account,5);
                        return ResultUtil.success();
                    }else {
                        return ResultUtil.error(501,"发送失败,请检查邮箱地址");
                    }
                }else {
                    if (mailUtils.sendResetPwdEmail(account.getAccountEmail(),accountLogin,"")){
                        historyService.addAccHistory(account,5);
                        return ResultUtil.success();
                    }else {
                        return ResultUtil.error(501,"发送失败,请检查邮箱地址");
                    }
                }
            }else {
                return ResultUtil.error(501,"该账号未绑定邮箱");
            }
        }else {
            return ResultUtil.error(501,"不存在员工");
        }
    }

    /***
     * @author wtf
     * @description 重置密码
     * @date 2019/8/12 11:19
     * @param request 请求体
     * @param accountLogin 登录账号
     * @param newPwd 新密码
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/management/resetPwd")
    @ResponseBody
//    @RequiresPermissions("empAccount:passReset")
    public Result resetPwd(HttpServletRequest request, @RequestParam("accountLogin") String accountLogin, @RequestParam("newPwd") String newPwd){
        if (StringUtil.isNotEmpty(accountLogin) && StringUtil.isNotEmpty(newPwd)){
            Account account = accountService.getAccountByLogin(accountLogin);
            if (!account.getAccountPassword().equals(newPwd)){
                if (accountService.updatePassword(accountLogin,newPwd)){
                    account = accountService.getAccountByLogin(accountLogin);
//                    historyService.addAccHistory(account,1);
                    if (null == historyService.getLoginHistoryById(accountLogin)){
                        historyService.insertFirstTimeLoginInfo(accountLogin,account.getEmpId());
                        return ResultUtil.success();
                    }
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(501,"修改密码失败");
                }
            }else {
                return ResultUtil.error(501,"新密码不能与上一次密码一样");
            }
        }else {
            return ResultUtil.error(501,"系统错误");
        }
    }
}
