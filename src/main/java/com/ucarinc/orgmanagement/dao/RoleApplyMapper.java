package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.Role;
import com.ucarinc.orgmanagement.entity.RoleApply;
import com.ucarinc.orgmanagement.entity.form.RoleApplyForm;
import com.ucarinc.orgmanagement.entity.form.RoleApplySearchForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 角色申请
 * @Author: 徐才艺(caiyi.xu@ucarinc.com)
 * @date: 2019/8/3 10:38
 * @version 1.0
 */
@Repository(value = "RoleApplyMapper")
public interface RoleApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleApply record);

    int insertSelective(RoleApply record);

    RoleApply selectByPrimaryKey(Long id);
    /**
     * 角色列表页查询
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/19 17:07
     * @param roleApplyForm 查询条件
     * @return: 返回查询到的数据
     */
    List<RoleApply> query(RoleApplySearchForm roleApplyForm);
    int updateByPrimaryKeySelective(RoleApply record);

    int updateByPrimaryKey(RoleApply record);
 
     
    /**
     * @Description: 获取角色申请数据集合
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/7 10:07
     * @Param:
     * @return:  List<RoleApply>
     */
    List<RoleApply> getRoleApplyList();

    /**
     * 根据角色ID获取角色支持的业务线，多个业务线以逗号，分隔
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/9 17:12
     * @param
     * @return:
     */
    String getBussinessNameByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据角色申请编号查找
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/9 17:11
     * @param
     * @param applyCode
     * @return: RoleApply的全部信息
     */
    RoleApply getRoleApplyByApplyCode(String applyCode);

    /**
     * 根据RoleApply更新role_apply表
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/10 17:05
     * @param
     * @return:
     */
    int updateRoleApplyByRoleCode(RoleApply roleApply);

    /**
     * 获取当天的角色申请数量
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/11 11:11
     * @param codePrefix 编号的前缀：SQ+当天日期  SQ20190811
     * @return: 申请数量
     */
    int getApplyCount(String codePrefix);


    /**
     * 根据传入的RoleApply的applyCode删除数据，修改is_delete和status等相关信息
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/23 13:59
     * @param roleApply 要更改的状态，包括is_delete,status,修改和操作相关信息
     * @return:
     */
    int deleteRoleApply(RoleApply roleApply);
}