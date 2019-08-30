package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.AccountRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:账号角色表Dao接口
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019/8/5 15:42
 */
@Mapper
@Repository
public interface AccountRoleMapper {

    /**
     * description:批量插入
     *
     * @param accountRoleList
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 15:39
     */
    int batchInsert(@Param("accountRoleList") List<AccountRole> accountRoleList, @Param("modifyEmpId") Long modifyEmpId);

    /**
    * description:获取指定登录账号用的的角色IdList
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/12 15:26
    * @param loginAccount 登录账号
    * @return  java.util.List<java.lang.Long>
    */
    List<Long> selectAccountRoleIdListByLoginAccount(@Param("loginAccount") String loginAccount);

    /**
     * description:批量逻辑删除指定账户下角色
     *
     * @param loginAccount, roleIdList, modifyEmp 账号,角色IdList,操作人Id
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/5 15:39
     */
    int batchLogicalDelete(@Param("loginAccount") String loginAccount,
                           @Param("roleIdList") List<Long> roleIdList,
                           @Param("modifyEmpId") Long modifyEmpId);
}
