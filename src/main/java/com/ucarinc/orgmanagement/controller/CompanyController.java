package com.ucarinc.orgmanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.Company;
import com.ucarinc.orgmanagement.entity.Employee;
import com.ucarinc.orgmanagement.entity.form.CompanyForm;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.impl.AccountServiceImpl;
import com.ucarinc.orgmanagement.service.impl.CompanyServiceImpl;
import com.ucarinc.orgmanagement.service.impl.HistoryServiceImpl;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.MapUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: orgmanagement
 * @description: 公司模块管理类
 * @author: Wu.tengfei
 * @create: Create in 10:32 2019/8/5
 **/
@Controller
@RequestMapping(value = "/company")
public class CompanyController {


    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private HistoryServiceImpl historyService;
    private Logger log = LoggerFactory.getLogger(CompanyController.class);
    /**
     * @author wtf
     * @description  根据部门id查询关联公司
     * @date 2019/8/8 10:57
     * @param request
     * @param deptId 部门id
     * @param pageNum 当前页码
     * @param pageSize 每页总数
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/management/getCompanyByDeptId")
    @ResponseBody
    @RequiresPermissions(value = {"company:getList","department:linkCompany"},logical = Logical.OR)
    public Result getCompanyByDeptId(HttpServletRequest request,@RequestParam("deptId") String deptId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        log.info("company查询条件deptId："+deptId);
        return ResultUtil.success(companyServiceImpl.getCompanyByDeptId(deptId,pageNum,pageSize));
    }

    /**
     * @author wtf
     * @description  根据部门id查询未关联公司
     * @date 2019/8/8 10:58
     * @param request
     * @param deptId 部门id
     * @param pageNum 当前页码
     * @param pageSize 每页总数
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/management/getCompanyNotInDepId")
    @ResponseBody
    @RequiresPermissions("company:getList")
    public Result getCompanyNotInDepId(HttpServletRequest request,@RequestParam("deptId") String deptId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        log.info("company查询条件deptId："+deptId);
        return ResultUtil.success(companyServiceImpl.getCompanyNotInDepId(deptId,pageNum,pageSize));
    }

    /**
     * @author wtf
     * @description 根据查询条件返回公司链表
     * @date 2019/8/8 10:59
     * @param request
     * @param pageNum 当前页码
     * @param pageSize 每页总数
     * @param companyForm 公司查询条件
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/management/getCompanyList")
    @ResponseBody
    @RequiresPermissions(value = {"company:getList","department:linkCompany"},logical = Logical.OR)
    public Result getCompanyByExample(HttpServletRequest request,@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("companyForm") String companyForm){
        CompanyForm companyForm1 = JSONObject.parseObject(companyForm, CompanyForm.class);
        log.info("company查询条件："+companyForm1);
        if (pageNum == 0 && pageSize == 0){
            return ResultUtil.success(companyServiceImpl.getCompanyList(companyForm1));
        }else {
            return ResultUtil.success(companyServiceImpl.getCompanyList(companyForm1,pageNum,pageSize));
        }
    }

    /**
     * @author wtf
     * @description 根据统一社会信用代码删除公司
     * @date 2019/8/8 10:59
     *
     * @param request
     * @param coCreditCode 统一社会信用代码
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequestMapping(value = "/management/deleteCompanyByCode")
    @ResponseBody
    @RequiresPermissions("company:deleteCompany")
    public Result deleteCompanyByCode(HttpServletRequest request,@RequestParam("coCreditCode") String coCreditCode, @RequestParam("accountLogin") String accountLogin){
        log.info("company删除条件："+coCreditCode);
        if (companyServiceImpl.deleteCompanyByCoCreditCode(coCreditCode)){
            Account account = accountService.getAccountByLogin(accountLogin);
            historyService.addAccHistory(account,2);
            return ResultUtil.success();
        }else {
            return ResultUtil.error(501,"不存在该记录");
        }
    }

    /**
     * @author wtf
     * @description 更新公司信息
     * @date 2019/8/8 10:59
     * @param request
     * @param map 请求体，获取axios提交的post请求中的data
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @RequiresPermissions("company:modifyCompany")
    @ResponseBody
    @RequestMapping(value = "/management/updateCompany",method= RequestMethod.POST)
    public Result addCompany(HttpServletRequest request,@RequestBody Map<String,Object> map){
        log.info("company新增信息："+JSONObject.toJSON(map.get("company")).toString());
        Company company = JSONObject.parseObject(JSONObject.toJSON(map.get("company")).toString(),Company.class);
        List<String> fileData = JSONObject.parseArray(JSONObject.toJSON(map.get("fileData")).toString(),String.class);
        List<String> fileOldName = JSONObject.parseArray(JSONObject.toJSON(map.get("fileOldName")).toString(),String.class);
        List<String> fileOldUrl = JSONObject.parseArray(JSONObject.toJSON(map.get("fileOldUrl")).toString(),String.class);
        List<String> fileName = JSONObject.parseArray(JSONObject.toJSON(map.get("fileName")).toString(),String.class);
        if(JSONObject.toJSON(map.get("isUpdate")).toString().equals("0")){
            if (companyServiceImpl.addCompanyByExample(company,fileData,fileName)){
                String accountLogin = map.get("accountLogin").toString();
                Account account = accountService.getAccountByLogin(accountLogin);
                historyService.addAccHistory(account,0);
                return ResultUtil.success();
            }else {
                return ResultUtil.error(501,"统一信用代码重复，请重新输入");
            }
        } else if (JSONObject.toJSON(map.get("isUpdate")).toString().equals("1")){
            System.out.println(map.get("accountLogin").toString());
            Account accountByLogin = accountService.getAccountByLogin(map.get("accountLogin").toString());
            company.setModifyEmp(ShiroUtil.getActiveEmpId());
            if (companyServiceImpl.updateCompanyByCoCreditCode(company,fileData,fileName,fileOldName,fileOldUrl)){
                String accountLogin = map.get("accountLogin").toString();
                Account account = accountService.getAccountByLogin(accountLogin);
                historyService.addAccHistory(account,1);
                return ResultUtil.success();
            }else {
                return ResultUtil.error(501,"修改失败");
            }
        } else {
            return null;
        }
    }

    /**
     * @author wtf
     * @description 根据统一社会信用代码查询指定公司
     * @date 2019/8/8 10:59
     * @param request
     * @param coCreditCode 统一社会信用代码
     * @return com.ucarinc.orgmanagement.entity.vo.Result
    */
    @ResponseBody
    @RequiresPermissions("company:getList")
    @RequestMapping(value = "/management/getCompanyByCoCreditCode",method = RequestMethod.GET)
    public Result getCompanyByCoCreditCode(HttpServletRequest request,@RequestParam("coCreditCode") String coCreditCode){
        log.info("company根据统一社会信用代码查询："+coCreditCode);

        return ResultUtil.success(companyServiceImpl.getCompanyByCoCreditCode(coCreditCode));
    }
}
