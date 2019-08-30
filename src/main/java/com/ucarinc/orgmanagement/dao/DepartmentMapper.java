package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.Department;
import com.ucarinc.orgmanagement.entity.DeptTree;
import com.ucarinc.orgmanagement.entity.form.DeptSearchForm;
import com.ucarinc.orgmanagement.entity.vo.DeptInfoResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "DepartmentMapper")
public interface DepartmentMapper {
    /**
     * description：新建子部门,插入部门信息操作
     * @param dept 部门信息实体对象
     * @return int 修改条数
     */
    int insertDepartment(Department dept);

    /**
     * description：修改部门信息
     * @param dept 部门信息实体对象
     * @return int 修改条数
     */
    int updateDepartment(Department dept);

    /**
     * description：逻辑删除部门信息
     * @param deptId 部门id
     * @return int
     */
    int deleteDepartment(String deptId);

    /**
     * description：获取部门子节点数
     * @param deptId 部门id
     * @return int 返回子节点数
     */
    int getCountOfSubDept(String deptId);

    /**
     * description：修改上级部门字段
     * @param deptId 部门id
     * @param superDeptId 上级部门id
     * @return int
     */
    int updateSuperDept(@Param("deptId") String deptId, @Param("superDeptId") String superDeptId);

    /**
     * description：获取部门信息列表
     * @return List<Department> 返回部门信息列表
     */
    List<Department> getDepartmentList();


    /**
     * description：获取部门信息列表
     * @return List<Department> 返回部门信息列表
     */
    List<Department> selectDepartment();

    /**
     * description：通过部门id获取部门信息
     * @param deptId 部门id
     * @return 部门信息
     */
    Department getDepartmentById(String deptId);

    /**
     * description：通过上级部门id获取上级部门名称
     * @param superDeptId 上级部门id
     * @return 上级部门名称
     */
    String getSuperDeptName(String superDeptId);

    /**
     * description：通过前端发送的请求参数，获取部门信息列表
     * @param deptSearchForm 请求的表单参数
     * @return 返回部门信息列表
     */
    List<Department> getDeptSearchList(DeptSearchForm deptSearchForm);

    String getCityNameById(String cityInterCode);
    String getCountyNameById(String countyInterCode);

    /**
     * description: 通过部门ID统计对应条数
     * @param deptId 部门ID
     * @return 统计条数
     */
    int getDeptCountById(String deptId);


    int getSpotCount(@Param("superDeptId") String superDeptId,@Param("officeSpot") String officeSpot);

    /**
     * description：获取上级部门所属下级部门所有的办公点标识
     * @param superDeptId 上级部门id
     * @return 返回办公点标识列表
     */

    List<Integer> getSpotBySuperDeptId(String superDeptId);
}