package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.DeptPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* description:数据权限:手动选择表 Dao接口
* @author dihan.chen(dihan.chen@ucarinc.com)
* @date 2019/8/17 16:41
*/
@Mapper
@Repository
public interface DeptPermissionMapper {

    /**
    * description:依照登录账号获取账号数据权限
    * @author dihan.chen(dihan.chen@ucarinc.com)
    * @date 2019/8/17 16:41
    * @param loginAccount
    * @return  java.util.List<java.lang.String>
    */

    List<String> selectDeptIdListByLoginAccount(@Param("loginAccount") String loginAccount);

    /**
     * description:批量插入
     *
     * @param deptPermissionList 待插入列表
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 15:44
     */
    int insetBatch(@Param("deptPermissionList") List<DeptPermission> deptPermissionList,
                   @Param("modifyEmpId") Long modifyEmpId);

    /**
     * description:批量逻辑删除
     *
     * @param loginAccount 登录账号
     * @param deptIdList   部门Id列表
     * @param modifyEmpId  修改人Id
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 15:51
     */

    int deleteBatch(@Param("loginAccount") String loginAccount,
                    @Param("deptIdList") List<String> deptIdList,
                    @Param("modifyEmpId") Long modifyEmpId);

    /**
     * 增加部门
     */
    int insertAll(DeptPermission record);
}
