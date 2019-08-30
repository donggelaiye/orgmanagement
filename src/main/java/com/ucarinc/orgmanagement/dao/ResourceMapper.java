package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:资源权限表Dao接口
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019/8/5 15:46
 */
@Mapper
@Repository
public interface ResourceMapper {

    /**
     * description:以用户登录账号连表查询该账号相应权限
     *
     * @param loginAccount<String> 用户登录账号
     * @return java.util.List<java.lang.String>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/2 15:45
     */
    List<String> selectAccountPermissionCodeListByLoginAccount(@Param("loginAccount") String loginAccount);

    /**
     * description:获取登录账号拥有的菜单编号与资源权限编号
     *
     * @param loginAccount String 登录账号
     * @return java.util.List<com.ucarinc.orgmanagement.entity.Resource>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/8 10:37
     */
    List<Resource> selectMenuCodeAndPermissionCodeByLoginAccount(@Param("loginAccount") String loginAccount);

    /**
    * description:获取所有资源权限信息
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/12 12:40
    * @return  java.util.List<com.ucarinc.orgmanagement.entity.Resource>
    */
    List<Resource> selectAllResource();

    /**
    * description:获取指定IdList资源权限信息
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/12 12:41
    * @param resourceIdList 资源IdList
    * @return  java.util.List<com.ucarinc.orgmanagement.entity.Resource>
    */
    List<Resource> selectResourceByResourceIdList(@Param("resourceIdList") List<Long> resourceIdList);

    /**
     * 获取权限数据集合
     *
     * @return 权限链表
     */
    List<Resource> selectResource();

}
