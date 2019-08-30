package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.Role;
import com.ucarinc.orgmanagement.entity.RoleAccountDetail;
import com.ucarinc.orgmanagement.entity.vo.PageResult;
import com.ucarinc.orgmanagement.entity.vo.RoleLineResult;

import java.util.List;

/**
 * @Date 2019/8/7
 * @Time 15:46
 * @Description 角色账户明细查询业务接口
 * @Author jiajin.chen@ucarinc.com
 */
public interface IRoleAccountDetailService {

    /**
     * description:<角色账户明细查询分页方法>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 14:10
     * @param pageNum
     * @param pageSize
     * @param roleAccountDetail
     * @return com.ucarinc.orgmanagement.entity.vo.PageResult <返回值说明>
     */
    PageResult getRoleAccountList(Integer pageNum, Integer pageSize, RoleAccountDetail roleAccountDetail);

    /**
     * description:<角色账户明细查询方法，不分页用于导出>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 14:10
     * @param roleAccountDetail
     * @return java.util.List<com.ucarinc.orgmanagement.entity.RoleAccountDetail> <返回值说明>
     */
    List<RoleAccountDetail> getRoleAccountList( RoleAccountDetail roleAccountDetail);


}
