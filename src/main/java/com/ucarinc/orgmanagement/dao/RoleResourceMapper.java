package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.RoleResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:角色权限表Dao接口
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @date 2019/8/5 15:45
 * @version 1.0
 */

@Mapper
@Repository
public interface RoleResourceMapper {

    /**
    * description:获取指定角色Id角色拥有的资源权限
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/12 15:23
    * @param roleId
    * @return  java.util.List<java.lang.Long>
    */
    List<Long> selectResourceIdByRoleId(@Param("roleId") Long roleId);

    /**
     * description:批量插入
     *
     * @param roleResourceList 插入实体List
     * @param modifyEmpId 操作人Id
     * @return int 影响行数
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 11:11
     */
    int batchInsert(@Param("roleResourceList") List<RoleResource> roleResourceList, @Param("modifyEmpId") Long modifyEmpId);

    /**
    * description:批量删除指定Id角色所拥有的资源权限
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/5 15:50
    * @param roleId, resourceIdList, modifyEmpId 角色Id,资源权限id,操作人Id
    * @return  int
    */
    int batchLogicalDelete(@Param("roleId") Long roleId,
                           @Param("resourceIdList") List<Long> resourceIdList,
                           @Param("modifyEmpId") Long modifyEmpId);
}
