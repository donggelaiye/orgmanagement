package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.Role;
import com.ucarinc.orgmanagement.entity.RoleLine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository(value = "RoleLineMapper")
public interface RoleLineMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Map map);

    int insertSelective(RoleLine record);

    RoleLine selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleLine record);

    int updateByPrimaryKey(RoleLine record);

    List<Long> getLineId(Long roleId);

    int deleteRoleLine(Long roleId);

    int updateRoleLine(Map map);

    int getRoleLineCount(Map map);
}