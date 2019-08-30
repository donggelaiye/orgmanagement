package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.Employee;
import com.ucarinc.orgmanagement.entity.form.EmpSearchForm;
import com.ucarinc.orgmanagement.entity.vo.EmployeeResult;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

import java.util.List;

/**
 * description: 员工服务接口类
 * @author jinbin,chen （jinbin,chen@ucarinccom）
 * @date 2019-08-04 15:32:00
 * @version 1.0
 */
public interface IEmployeeService {

    /**
     *  通过ID获取员工数据
     * @param empId 员工ID
     * @return 员工实体
     */
    EmployeeResult getEmployeeById(String empId);

    /**
     * 插入一条新员工数据
     * @param record 员工实体类
     * @return 修改条数
     */
    int insertEmployee(Employee record);

    /**
     * 通过ID删除员工数据
     * @param empId 员工ID
     * @return 删除条数
     */
    int deleteEmployeeById(String empId);

    /**
     * 通过ID修改员工数据
     * @param record 员工实体
     * @return 修改条数
     */
    int updateEmployeeById(Employee record);

    /**
     * 获取员工数据集合
     * @param pageNum 当前页面
     * @param pageSize 每页条数
     * @return 当前页数据集合
     */
    PageResult getEmployeeList(Integer pageNum, Integer pageSize, EmpSearchForm empSearchForm);

    /**
     * 判断员工ID是否已存在
     *
     * @param empId 员工ID
     * @return 统计条数
     */
    int checkEmployeeId(String empId);

    /**
     * 通过ID修改员工状态为离职
     *
     * @param empId 员工ID
     * @return 修改条数
     */
    int updateDimission(String empId);

    /**
     * 通过ID修改员工状态为正常
     *
     * @param empId 员工ID
     * @return 修改条数
     */
    int updateReDimission(String empId);

    /**
     * 通过ID修改员工所属部门
     *
     * @param empId 员工ID
     * @param empDepart 部门ID
     * @return 修改条件
     */
    int updateEmployeeDept(String empId, String empDepart);

    /**
     * 通过登陆账号获取员工数据
     *
     * @param accountLogin 登陆账号
     * @return 员工数据
     */
    EmployeeResult getEmployeeByAccount(String accountLogin);

    /**
     * 获取员工数据集合，但删除已关联的
     * @param pageNum 当前页面
     * @param pageSize 每页条数
     * @return 当前页数据集合
     */
    PageResult getEmployeeListOnly(Integer pageNum, Integer pageSize, EmpSearchForm empSearchForm);

    /**
     * 获取符合搜索条件的员工数据
     *
     * @param empSearchForm 搜索条件
     * @return 员工数据
     */
    List<EmployeeResult> getEmployeeListAll(EmpSearchForm empSearchForm);

}
