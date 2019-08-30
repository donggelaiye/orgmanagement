package com.ucarinc.orgmanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.ucarinc.orgmanagement.entity.Company;
import com.ucarinc.orgmanagement.entity.Department;
import com.ucarinc.orgmanagement.entity.form.DeptSearchForm;
import com.ucarinc.orgmanagement.entity.form.EmpSearchForm;
import com.ucarinc.orgmanagement.entity.vo.OptionalTreeResult;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.entity.vo.TreeResult;
import com.ucarinc.orgmanagement.service.IDepartmentService;
import com.ucarinc.orgmanagement.utils.ComUtil;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description: 部门模块控制器
 * @author daijianzhao
 * @date 2019-08-05 10:42
 * @version 1.0
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

    private Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping("/management/getDepartmentList")
    @ResponseBody
    public Result getDepartmentList(){
        logger.info("==执行获取部门信息的方法==");
        //可手动抛出异常，方法如下
        /*throw new GlobalException(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());*/
        return ResultUtil.success(departmentService.getDepartmentList());
    }
    @RequiresPermissions("department:getList")
    @RequestMapping("/management/getDepartmentTree")
    @ResponseBody
    public Result getDepartmentTree(){
        logger.info("==执行获取部门信息树方法==");
        //可手动抛出异常，方法如下
        /*throw new GlobalException(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());*/
        TreeResult result = departmentService.selectDepartment(true);
        List<TreeResult> list=new ArrayList<>();
        list.add(result);
        return ResultUtil.success(list);
    }
    @RequiresPermissions("department:modifySupDepartment")
    @RequestMapping("/management/getOptionalDeptTree")
    @ResponseBody
    public Result getOptionalDeptTree(@RequestParam("deptId")String deptId){
        logger.info("==执行获取可选择部门树的方法==");
        //可手动抛出异常，方法如下
        /*throw new GlobalException(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());*/
        OptionalTreeResult result = departmentService.getOptionalDeptTree(deptId);
        List<OptionalTreeResult> list=new ArrayList<>();
        list.add(result);
        return ResultUtil.success(list);
    }

    @RequiresPermissions(value = {"department:addSubDepartment","department:modifyDepartment"},logical = Logical.OR)
    @RequestMapping("/management/getDeptBussinessLine")
    @ResponseBody
    public Result getDeptBussinessLine(@RequestParam("deptId")String deptId){
        logger.info("==执行获取部门关联业务线方法==");
        return ResultUtil.success(departmentService.getDeptBussinessLine(deptId));
    }
    @RequestMapping("/management/getSubBussinessLine")
    @ResponseBody
    public Result getSubBussinessLine(@RequestParam("deptId")String deptId){
        logger.info("==执行获取部门关联业务线方法==");
        return ResultUtil.success(departmentService.getSubBussinessLine(deptId));
    }

    @RequiresPermissions("department:addSubDepartment")
    @RequestMapping("/management/addDepartment")
    @ResponseBody
    public Result addDepartment(@RequestBody Map<String,Object> map){
        logger.info("==执行新建子部门方法==");
        Department department=JSONObject.parseObject(JSONObject.toJSON(map.get("deptInfo")).toString(),Department.class);
        List<Integer> bussinessLine=JSONObject.parseArray(JSONObject.toJSON(map.get("deptBussinessLine")).toString(),Integer.class);
        departmentService.addDepartment(department, bussinessLine,0);
        return ResultUtil.success("插入成功");
    }

    @RequiresPermissions("department:modifyDepartment")
    @RequestMapping("/management/updateDepartment")
    @ResponseBody
    public Result updateDepartment(@RequestBody Map<String,Object> map){
        logger.info("==执行修改部门方法==");
        Department department=JSONObject.parseObject(JSONObject.toJSON(map.get("deptInfo")).toString(),Department.class);
        List<Integer> bussinessLine=JSONObject.parseArray(JSONObject.toJSON(map.get("deptBussinessLine")).toString(),Integer.class);
        departmentService.updateDepartment(department, bussinessLine,0);
        return ResultUtil.success();
    }
    @RequiresPermissions("department:deleteDepartment")
    @RequestMapping("/management/deleteDepartment")
    @ResponseBody
    public Result deleteDepartment(@RequestParam("deptId") String deptId){
        logger.info("==执行删除部门的方法==");
        departmentService.deleteDepartment(deptId);
        return ResultUtil.success();
    }

    @RequiresPermissions("department:modifyDepartment")
    @RequestMapping("/management/getDepartmentById")
    @ResponseBody
    public Result getDepartmentById(@RequestParam("deptId")String deptId){
        logger.info("==执行通过部门id获取部门信息的方法==");
        return ResultUtil.success(departmentService.getDepartmentById(deptId));
    }
    @RequiresPermissions("department:modifyDepartment")
    @RequestMapping("/management/getSuperDeptName")
    @ResponseBody
    public Result getSuperDeptName(@RequestParam("deptId")String deptId){
        logger.info("==执行获取部门名称的方法==");
        System.out.println(deptId);
        return ResultUtil.success(departmentService.getSuperDeptName(deptId));
    }

    @RequiresPermissions("department:modifySupDepartment")
    @RequestMapping("/management/updateSuperDept")
    @ResponseBody
    public Result updateSuperDept(@RequestParam("deptId")String deptId, @RequestParam("superDeptId")String superDeptId){
        logger.info("==执行修改上级部门的方法==");
        departmentService.updateSuperDept(deptId, superDeptId);
        return ResultUtil.success();
    }
    @RequiresPermissions("department:linkCompany")
    @RequestMapping("/management/updateDeptCompany")
    @ResponseBody
    public Result updateDeptCompany(@RequestBody Map<String,Object> map){
        logger.info("==执行修改部门所关联公司的方法==");
        String deptId=map.get("deptId").toString();
        List<Company> companyList=JSONObject.parseArray(JSONObject.toJSON(map.get("companyList")).toString(),Company.class);
        departmentService.updateDeptCompany(deptId,companyList);
        return ResultUtil.success();
    }

    @RequiresPermissions("search:dept")
    @RequestMapping("/management/getDeptSearchList")
    @ResponseBody
    public Result getDeptSearchList(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize,
                                    @RequestParam("deptSearchForm") String deptSearchFormStr){
        logger.info("==执行获取部门信息的方法==");
        DeptSearchForm deptSearchForm = JSONObject.parseObject(deptSearchFormStr, DeptSearchForm.class);
        System.out.println(deptSearchFormStr);
        if(deptSearchForm.getDeptLevel()==9){
            deptSearchForm.setDeptLevel(null);
        }
        if(deptSearchForm.getDeptType()==9){
            deptSearchForm.setDeptType(null);
        }
        if(deptSearchForm.getStatus()==9){
            deptSearchForm.setStatus(null);
        }
        return ResultUtil.success(departmentService.getDeptSearchList(pageNum,pageSize,deptSearchForm));
    }
    @RequestMapping("/management/getDeptList")
    @ResponseBody
    public Result getDeptList(@RequestParam("deptSearchForm") String deptSearchFormStr){
        logger.info("==执行获取部门信息的方法==");
        DeptSearchForm deptSearchForm = JSONObject.parseObject(deptSearchFormStr, DeptSearchForm.class);

        if(deptSearchForm.getDeptLevel()==9){
            deptSearchForm.setDeptLevel(null);
        }
        if(deptSearchForm.getDeptType()==9){
            deptSearchForm.setDeptType(null);
        }
        if(deptSearchForm.getStatus()==9){
            deptSearchForm.setStatus(null);
        }
        return ResultUtil.success(departmentService.getDeptExportInfoList(deptSearchForm));
    }
    @RequiresPermissions("department:modifyDepartment")
    @RequestMapping("/management/getCityName")
    @ResponseBody
    public Result getCityName(@RequestParam("interCode") String interCode,@RequestParam("isCity") String isCity){
        return ResultUtil.success(departmentService.getCityName(interCode, isCity));
    }

    @RequiresPermissions("department:addSubDepartment")
    @RequestMapping("/management/getDeptCount")
    @ResponseBody
    public Result getDeptCount(@RequestParam("deptId") String deptId){
        return ResultUtil.success(departmentService.getDeptCountById(deptId));
    }
    @RequiresPermissions("department:modifyDepartment")
    @RequestMapping("/management/getEmpDisplayName")
    @ResponseBody
    public Result getEmpDisplayName(@RequestParam("empId") String empId){
        System.out.println("传入的empId值："+empId);
        String str = ComUtil.getEmpStr(Long.parseLong(empId));
        System.out.println("返回的empDisplayName："+str);
        List<String> list=new ArrayList<>();
        list.add(str);
        return ResultUtil.success(list);
    }

    @RequiresPermissions("department:addSubDepartment")
    @RequestMapping("/management/getSpotCount")
    @ResponseBody
    public Result getSpotCount(@RequestParam("superDeptId") String superDeptId, @RequestParam("officeSpot")String officeSpot){
        return ResultUtil.success(departmentService.getSpotCount(superDeptId,officeSpot));
    }

}
