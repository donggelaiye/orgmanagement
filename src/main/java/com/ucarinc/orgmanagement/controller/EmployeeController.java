package com.ucarinc.orgmanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.Employee;
import com.ucarinc.orgmanagement.entity.form.EmpSearchForm;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.IDataPermissionService;
import com.ucarinc.orgmanagement.service.IEmployeeService;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * description: 员工模块控制器
 * @author jinbin.chen （jinbin.chen@ucarinc.com）
 * @date 2019-08-04 15:40:00
 * @version 1.0
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDataPermissionService dataPermissionService;

    @RequestMapping(value = "/management/getEmployee", method = RequestMethod.GET)
    public Result getEmployee(@RequestParam("empId") String empId) {
        logger.info("==执行获取员工方法==");
        //可手动抛出异常，方法如下
        /*throw new GlobalException(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());*/
        return ResultUtil.success(employeeService.getEmployeeById(empId));
    }

    @RequiresPermissions("employee:addEmployee")
    @RequestMapping(value = "/management/addEmployee", method = RequestMethod.GET)
    public Result addEmployee(HttpServletRequest request, @RequestParam("employee") String employeeStr) {
        logger.info("==调用添加员工方法==");
        Employee employee = JSONObject.parseObject(employeeStr, Employee.class);
        employeeService.insertEmployee(employee);
        return ResultUtil.success();
    }

    @RequiresPermissions("employee:deleteEmployee")
    @RequestMapping(value = "/management/deleteEmployee", method = RequestMethod.GET)
    public Result deleteEmployee(HttpServletRequest request, @RequestParam("empId") String empId) {
        logger.info("==调用删除员工方法==");
        employeeService.deleteEmployeeById(empId);
        return ResultUtil.success();
    }

    @RequiresPermissions("employee:updateEmployee")
    @RequestMapping(value = "/management/updateEmployee", method = RequestMethod.GET)
    public Result updateEmployee(HttpServletRequest request, @RequestParam("employee") String employeeStr) {
        logger.info("==调用修改员工方法==");
        Employee employee = JSONObject.parseObject(employeeStr, Employee.class);
        employeeService.updateEmployeeById(employee);
        return ResultUtil.success();
    }

    @RequiresPermissions("employee:getList")
    @RequestMapping(value = "/management/getEmployeeList", method = RequestMethod.GET)
    public Result getEmployeeList(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize, @RequestParam("empSearchForm") String empSearchFormStr) {
        logger.info("==调用获取员工分页方法==");
        EmpSearchForm empSearchForm = JSONObject.parseObject(empSearchFormStr, EmpSearchForm.class);
        empSearchForm.setGrantDeptList(dataPermissionService.getActiveAccountDataPermissionDeptIdList());
        //        数据权限是本人,则列表只显示自己
        Account account = ShiroUtil.getActiveAccountInfo();

        if (3 == account.getDataPermissionId() ){
            empSearchForm.setAccountName(account.getAccountLogin());
        }

        return ResultUtil.success(employeeService.getEmployeeList(pageNum, pageSize, empSearchForm));
    }

    @RequestMapping(value = "/management/checkEmployeeId", method = RequestMethod.GET)
    public Result checkEmployeeId(@RequestParam("empId") String empId) {
        logger.info("==执行检测员工ID是否存在方法==");
        return ResultUtil.success(employeeService.checkEmployeeId(empId));
    }

    @RequiresPermissions("employee:dimissionEmployee")
    @RequestMapping(value = "/management/updateDimission", method = RequestMethod.GET)
    public Result updateDimission(@RequestParam("empId") String empId) {
        logger.info("==执行修改员工状态为离职方法==");
        employeeService.updateDimission(empId);
        return ResultUtil.success();
    }

    @RequiresPermissions("employee:reDimissionEmployee")
    @RequestMapping(value = "/management/updateReDimission", method = RequestMethod.GET)
    public Result updateReDimission(@RequestParam("empId") String empId) {
        logger.info("==执行修改员工状态为正常方法==");
        employeeService.updateReDimission(empId);
        return ResultUtil.success();
    }

    @RequiresPermissions(" employee:allocateDept")
    @RequestMapping(value = "/management/updateEmployeeDept", method = RequestMethod.GET)
    public Result updateEmployeeDept(@RequestParam("empId") String empId, @RequestParam("empDepart") String empDepart) {
        logger.info("==执行修改员工所属部门方法==");
        employeeService.updateEmployeeDept(empId, empDepart);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/management/getEmployeeByAccount", method = RequestMethod.GET)
    public Result getEmployeeByAccount(@RequestParam("accountLogin") String accountLogin) {
        logger.info("==执行通过登陆账号获取员工方法==");
        return ResultUtil.success(employeeService.getEmployeeByAccount(accountLogin));
    }

    @RequestMapping(value = "/management/getEmployeeListOnly", method = RequestMethod.GET)
    public Result getEmployeeListOnly(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize, @RequestParam("empSearchForm") String empSearchFormStr) {
        logger.info("==调用获取员工查询一一对应方法==");
        EmpSearchForm empSearchForm = JSONObject.parseObject(empSearchFormStr, EmpSearchForm.class);
        return ResultUtil.success(employeeService.getEmployeeListOnly(pageNum, pageSize, empSearchForm));
    }

    @RequestMapping(value = "/management/getEmployeeListAll", method = RequestMethod.GET)
    public Result getEmployeeListAll(HttpServletRequest request, @RequestParam("empSearchForm") String empSearchFormStr) {
        logger.info("==执行导出获取员工数据方法==");
        EmpSearchForm empSearchForm = JSONObject.parseObject(empSearchFormStr, EmpSearchForm.class);
        return ResultUtil.success(employeeService.getEmployeeListAll(empSearchForm));
    }

}
