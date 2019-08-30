package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.Employee;
import com.ucarinc.orgmanagement.entity.form.EmpSearchForm;
import com.ucarinc.orgmanagement.entity.vo.EmployeeResult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * description: 员工dao接口类
 * @author jinbin.chen （jinbin.chen@ucarinc.com）
 * @date 2019-08-02 16:06:00
 * @version 1.0
 */
@Repository(value = "EmployeeMapper")
public interface EmployeeMapper {

    /**
     *  通过ID获取员工数据
     *
     * @param empId 员工ID
     * @return 员工数据对象
     */
    EmployeeResult getEmployeeById(String empId);

    /**
     * 插入新员工数据
     *
     * @param record 员工数据对象
     * @return 修改条数
     */
    int insertEmployee(Employee record);

    /**
     * 通过ID删除员工数据
     *
     * @param empId 员工ID
     * @return
     */
    int deleteEmployeeById(String empId);

    /**
     * 通过ID修改员工数据
     *
     * @param record 员工数据对象
     * @return 修改条数
     */
    int updateEmployeeById(Employee record);

    /**
     * 获取员工数据集合
     *
     * @param empSearchForm 条件参数表单
     * @return 员工数组
     */
    List<EmployeeResult> getEmployeeList(EmpSearchForm empSearchForm);

    /**
     * 通过ID统计员工数量
     *
     * @param empId 员工ID
     * @return 数量
     */
    int getEmployeeCountById(String empId);

    /**
     * 通过ID统计员工数量
     *
     * @param empId 员工ID
     * @return 数量
     */
    int getCountById(String empId);

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
     * @return 修改条数
     */
    int updateEmployeeDept(String empId, String empDepart);

    /**
     * 通过部门ID获取部门名称
     *
     * @param deptId 部门ID
     * @return 部门名称
     */
    String getDeptNameById(String deptId);

    /**
     * 通过登陆账号获取员工数据
     *
     * @param accountLogin 登陆账号
     * @return 员工数据
     */
    EmployeeResult getEmployeeByAccount(String accountLogin);

    /**
     * 通过员工ID获取员工姓名
     *
     * @param empId 员工ID
     * @return 员工姓名
     */
    String getEmpNameByEmpId(String empId);

    /**
     * 获取员工数据集合，删除已经被选中的员工编号
     *
     * @param empSearchForm 条件参数表单
     * @return 员工数组
     */
    List<EmployeeResult> getEmployeeListOnly(EmpSearchForm empSearchForm);

    /**
     * 通过员工ID修改账号密保邮箱
     *
     * @param empId 员工ID
     * @param empEmail 员工邮箱
     * @return 修改条数
     */
    int updateAccountEmail(String empId, String empEmail);

    /**
     * 通过员工ID获取关联账号名称
     *
     * @param empId 员工ID
     * @return 关联账号名称
     */
    String getAccountLoginByEmpId(String empId);

    /**
     * 通过员工ID获取账号信息
     *
     * @param empId 员工ID
     * @return 账号实体
     */
    Account getAccountByEmpId(String empId);

    /**
     * 通过员工ID解绑账号
     *
     * @param empId 员工ID
     * @return 修改条数
     */
    int updateAccountUnBund(String empId);

    /**
     * 通过员工ID将关联账号删除
     *
     * @param empId 员工ID
     * @return 修改条数
     */
    int deleteAccountByEmpId(String empId);

    /**
     * 通过员工ID获取员工编号
     * @param id 员工ID
     * @return 员工编号
     */
    String getEmpIdById(Long id);

    /**
     * 通过账户获取关联员工ID
     * @param accountLogin 账号
     * @return 员工ID
     */
    Long getEmpIdByAccount(String accountLogin);

    /**
     * 通过员工编号获取在职离职信息
     * @param empId 员工编号
     * @return 职位信息
     */
    Integer getDimissionByEmpId(String empId);

    int updateAccountEmpId(Map map);

    int updateDeptEmpId(Map map);

    int updateOperateEmpId(Map map);

    int updateRoleEmpId(Map map);

    int updateRoleApplyEmpId(Map map);

    int updateRoleAccountEmpId(Map map);

    int updateDeptEmpName(Map map);

    int updateRoleEmpName(Map map);

    int updateRoleApplyEmpName(Map map);

    int updateRoleAccountEmpName(Map map);

    int updateRoleApplyDeptName(Map map);

    int updateRoleAccountDeptName(Map map);

}
