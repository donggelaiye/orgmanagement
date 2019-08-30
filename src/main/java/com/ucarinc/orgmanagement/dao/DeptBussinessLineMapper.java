package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.DeptBussinessLine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "DeptBussinessLineMapper")
public interface DeptBussinessLineMapper {

    /**
     * description：新建子部门，插入部门业务线关联信息操作
     * @param deptBussinessLine 部门业务线信息
     * @return int 修改条数
     */
    int insertDeptBussinessLine(DeptBussinessLine deptBussinessLine);

    /**
     * description：物理删除关联业务线
     * @param deptBussinessLine 部门关联业务线
     * @return  <返回值说明>
     */
    int removeDeptBussinessLine(DeptBussinessLine deptBussinessLine);

    /**
     * description：根据部门id获取关联业务线
     * @param deptId 部门id
     * @return  部门关联业务线的集合
     */
    List<DeptBussinessLine> getDeptBussinessLineListByDeptId(String deptId);

    /**
     * description：逻辑删除部门所关联的业务线
     * @param deptId 部门id
     * @return int
     */
    int deleteDeptBussinessList(String deptId);

    /**
     * description：通过本部门及子部门id，获取所包含的业务线
     * @param list 部门id集合
     * @return  返回业务线集合（去重）
     */
    List<Integer> getBussinessLineGroop(List<String> list);
}