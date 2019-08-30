package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.Company;
import com.ucarinc.orgmanagement.entity.Department;
import com.ucarinc.orgmanagement.entity.DeptBussinessLine;
import com.ucarinc.orgmanagement.entity.form.DeptSearchForm;
import com.ucarinc.orgmanagement.entity.vo.*;

import java.util.List;

/**
 * description: 部门管理服务接口
 * @date 2019-08-03 09:28
 * @author daijianzhao
 * @version 1.0
 */
public interface IDepartmentService {

    /**
     * description：新建子部门
     * @param dept 部门信息
     * @param deptBussinessLineList 业务线关联信息
     */
    void addDepartment(Department dept, List<DeptBussinessLine> deptBussinessLineList);

    /**
     * description：修改部门信息
     * @param dept 部门信息
     * @param deptBussinessLineList 业务线关联信息
     */
    void updateDepartment(Department dept, List<DeptBussinessLine> deptBussinessLineList);

    /**
     * description：删除部门
     * @param deptId 部门id
     */
    void deleteDepartment(String deptId);

    /**
     * description：修改上级部门
     * @param deptId 部门id
     * @param superDeptId 上级部门id
     */
    void updateSuperDept(String deptId, String superDeptId);

    /**
     * description：获取部门信息列表
     * @return 返回部门信息列表
     */
    List<Department> getDepartmentList();

    /**
     * description：获取部门关联业务线
     * @param deptId <参数说明>
     * @return 部门关联业务线
     */
    String[] getDeptBussinessLine(String deptId);

    /**
     * description：新建子部门方法
     * @param dept 部门信息
     * @param integerList 业务线id列表
     */
    void addDepartment(Department dept, List<Integer> integerList, int length);
    /**
     * description：修改部门方法
     * @param dept 部门信息
     * @param integerList 业务线id列表
     */
    void updateDepartment(Department dept, List<Integer> integerList, int length);

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
     * description：获取可选树
     * @param deptId 部门id
     * @return  带可选状态的部门树
     */
    OptionalTreeResult getOptionalDeptTree(String deptId);

    /**
     * description：保存部门所关联的公司
     * @param deptId 部门id
     * @param companyList 该部门所关联的公司
     */
    void updateDeptCompany(String deptId, List<Company> companyList);

    TreeResult selectDepartment(Boolean showDelete);

    /**
     * description：获取完整的部门信息（包括关联公司和关联城市）
     * @param pageNum 当前页数
     * @param pageSize 页大小
     * @param deptSearchForm 前端搜索表单
     * @return 返回根据条件搜索得的分页数据
     */
    PageResult getDeptSearchList(Integer pageNum, Integer pageSize, DeptSearchForm deptSearchForm);

    /**
     * description：根据id获取城市或区县名称
     * @param interCode city代码或county代码
     * @param isCity 用来判断是city还是county
     * @return 返回cityName或countyName
     */
    String getCityName(String interCode, String isCity);

    /**
     * description：获取部门信息用以导出
     * @param deptSearchForm 前端搜索表单
     * @return 返回根据条件搜索得的数据
     */
    List<DeptExportInfoResult> getDeptExportInfoList(DeptSearchForm deptSearchForm);

    /**
     * description：通过部门ID获取统计条数
     * @param deptId 部门ID
     * @return 统计条数
     */
    int getDeptCountById(String deptId);

    int getSpotCount(String superDeptId, String officeSpot);

    /**
     * description：获取所有下级部门的业务线和
     * @param deptId <参数说明>
     * @return  <返回值说明>
     */
    String[] getSubBussinessLine(String deptId);

}
