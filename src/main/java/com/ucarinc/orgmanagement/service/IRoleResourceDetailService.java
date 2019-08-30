package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.form.RoleResourceForm;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

import java.util.List;

/**
 * @Date 2019/8/9
 * @Time 11:31
 * @Description
 * @Author jiajin.chen@ucarinc.com
 */
public interface IRoleResourceDetailService {

    /**
     * description:<角色权限明细查询分页>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 14:20
     * @param pageNum
     * @param pageSize
     * @param roleResourceForm
     * @return com.ucarinc.orgmanagement.entity.vo.PageResult <返回值说明>
     */
    PageResult getRoleResourceList(Integer pageNum, Integer pageSize, RoleResourceForm roleResourceForm);

    /**
     * description:<角色权限明细查询部分不分页>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 14:18
     * @param roleResourceForm
     * @return java.util.List<com.ucarinc.orgmanagement.entity.form.RoleResourceForm> <返回值说明>
     */
    List<RoleResourceForm> roleResourceList(RoleResourceForm roleResourceForm);
}
