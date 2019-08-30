package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.DeptCompany;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "DeptCompanyMapper")
public interface DeptCompanyMapper {
    /**
     * description：逻辑删除对应部门id所关联的公司
     * @param deptId 部门id
     * @return int
     */
    int deleteDeptCompany(String deptId);

    /**
     * description：获取部门对应的所有关联的公司列表
     * @param deptId 部门id
     * @return 关联公司列表
     */
    List<DeptCompany> getDeptCompanyList(String deptId);

    /**
     * description：添加部门与公司的关联
     * @param deptCompany 关联公司
     */
    int insertDeptCompany(DeptCompany deptCompany);

    /**
     * description：删除部门与公司的关联
     * @param deptId 部门id
     * @param coCreditCode 公司统一信用代码
     */
    int removeDeptCompany(@Param("deptId") String deptId, @Param("coCreditCode") String coCreditCode);
}