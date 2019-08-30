package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.RoleApprove;
import com.ucarinc.orgmanagement.entity.form.RoleApproveForm;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

/**
 * @Date 2019/8/5
 * @Time 16:17
 * @Description 角色申请审批业务接口
 * @Author jiajin.chen@ucarinc.com
 */
public interface IRoleApproveService {

    /**
     * description:<获取角色申请审核数据集合>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:46
     * @param pageNum 当前页面
     * @param pageSize 每页条数
     * @param roleApproveForm
     * @return com.ucarinc.orgmanagement.entity.vo.PageResult <当前页数据集合>
     */
    PageResult getRoleApproveList(Integer pageNum, Integer pageSize, RoleApproveForm roleApproveForm);

    /**
     * description:<审批拒绝>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:47
     * @param roleApprove
     * @return int <返回值说明>
     */
    int approveRefuse(RoleApprove roleApprove);

    /**
     * description:<审批通过>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:51
     * @param approveJSON
     * @return int <返回值说明>
     */
    int approveAgree(String approveJSON);
}
