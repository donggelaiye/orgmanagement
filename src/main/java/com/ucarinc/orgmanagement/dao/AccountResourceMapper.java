package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.AccountResource;
import com.ucarinc.orgmanagement.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:账号特殊权限表Dao接口
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019/8/5 16:05
 */
@Mapper
@Repository
public interface AccountResourceMapper {

    /**
    * description:依照登录账号获取登录账号拥有的特殊权限编号
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/8 13:53
    * @param loginAccount <String> 登录账号
    * @return  java.util.List<java.lang.String>
    */
    List<String> selectAccountPermissionCodeByLoginAccount(@Param("loginAccount") String loginAccount);

    /**
     * description:依照登录账号获取登录账号拥有的特殊资源编号
     * @author dihan.chen(dihan.chen@ucarinc.com)
     * @date 2019/8/8 13:53
     * @param loginAccount <String> 登录账号
     * @return  java.util.List<java.lang.String>
     */
    List<String> selectAccountResourceIdByLoginAccount(@Param("loginAccount") String loginAccount);

    /**
    * description: 依照登录账号获取账号关联特殊权限编号及菜单信息
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/13 14:25
    * @param loginAccount 登录账号
    * @return  java.util.List<com.ucarinc.orgmanagement.entity.Resource>
    */

    List<Resource> selectAccountResourceByLoginAccount(@Param("loginAccount") String loginAccount);

    /**
     * description:批量插入指定用户的特殊权限
     *
     * @param accountResourceList 批量插入实体List
     * @param modifyEmpId 操作人Id
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 16:08
     */
    int batchInsert(@Param("accountResourceList") List<AccountResource> accountResourceList, @Param("modifyEmpId") Long modifyEmpId);

    /**
     * description:批量逻辑删除指定用户下的权限
     *
     * @param loginAccount, resourceIdList, modifyEmp
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 16:23
     */
    int batchLogicalDelete(@Param("loginAccount") String loginAccount,
                           @Param("resourceIdList") List<Long> resourceIdList,
                           @Param("modifyEmpId") Long modifyEmpId);

}
