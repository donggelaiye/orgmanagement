package com.ucarinc.orgmanagement.controller;

import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.entity.vo.TreeResult;
import com.ucarinc.orgmanagement.service.IDepartmentService;
import com.ucarinc.orgmanagement.service.impl.DepartmentServiceImpl;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 部门模块控制器
 * @author 赖家美 （邮箱）
 * @date yyyy-MM-dd HH:mm:ss
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/dept")
public class DeptController {

    @Autowired
    private IDepartmentService deptService;

    @RequestMapping(value = "/tree",method = RequestMethod.GET)
    @CrossOrigin
    public Result departmentManage(){
        List list = new ArrayList();
        list.add(deptService.selectDepartment(false));
        return ResultUtil.success(list);
    }

    @RequestMapping(value = "/validTree",method = RequestMethod.GET)
    @CrossOrigin
    public Result getValidDeptTree(){
        List list = new ArrayList();
        list.add(deptService.selectDepartment(true));
        return ResultUtil.success(list);
    }

}
