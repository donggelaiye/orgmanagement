package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.Role;
import com.ucarinc.orgmanagement.entity.RoleAccountDetail;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.entity.vo.RoleLineResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date 2019/8/7
 * @Time 16:10
 * @Description
 * @Author jiajin.chen@ucarinc.com
 */
@Repository(value = "RoleAccountDetailMapper")
public interface RoleAccountDetailMapper {

    /**
     * description:<角色账户明细查询>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 14:14
     * @param roleAccountDetail
     * @return java.util.List<com.ucarinc.orgmanagement.entity.RoleAccountDetail> <返回值说明>
     */
    List<RoleAccountDetail> getRoleAccountList(RoleAccountDetail roleAccountDetail);



}
