package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.RoleApplyAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "RoleApplyAccountMapper")
public interface RoleApplyAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleApplyAccount record);

    int insertSelective(RoleApplyAccount record);

    RoleApplyAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleApplyAccount record);

    int updateByPrimaryKey(RoleApplyAccount record);

    List<RoleApplyAccount> getAccountListByApplyCode(String applyCode);

    /**
     * 通过角色申请编号和申请账号，删除ApplyAccount的账号信息
     * @Author: 徐才艺(caiyi.xu@ucarinc.com)
     * @date: 2019/8/10 16:02
     * @param
     * @return:
     */
    int deleteAccount(@Param("applyCode") String applyCode, @Param("account") String account);

    int updateAccount(RoleApplyAccount roleApplyAccount);

    int insertOrUpdate(RoleApplyAccount roleApplyAccount);
}