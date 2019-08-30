package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.AccountRole;
import com.ucarinc.orgmanagement.entity.RoleApprove;
import com.ucarinc.orgmanagement.entity.form.RoleApproveForm;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date 2019/8/5
 * @Time 16:34
 * @Description 角色申请审批
 * @Author jiajin.chen@ucarinc.com
 */
@Repository(value = "RoleApproveMapper")
public interface RoleApproveMapper {

    /**
     * description:<获取角色申请审核分页方法>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:57
     * @param roleApproveForm
     * @return java.util.List<com.ucarinc.orgmanagement.entity.form.RoleApproveForm> <返回值说明>
     */
    List<RoleApproveForm> getRoleApproveList(RoleApproveForm roleApproveForm);

    /**
     * description:<审核拒绝>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:58
     * @param roleApprove
     * @return int <返回值说明>
     */
    int approveRefuse(RoleApprove roleApprove);

    /**
     * description:<审核通过，更新角色申请表>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:59
     * @param roleApprove
     * @return int <返回值说明>
     */
    int  updateAgreeStatus(RoleApprove roleApprove);

    /**
     * description:<审核通过，当角色账号表中没有该数据，插入申请添加的账号>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 13:59
     * @param accountRole
     * @return int <返回值说明>
     */
    int  insertAgreeAccount(AccountRole accountRole);

    /**
     * description:<审核通过，删除申请移除的账号>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 14:03
     * @param accountRole
     * @return int <返回值说明>
     */
    int  deleteAgreeAccount(AccountRole accountRole);

    /**
     * description:<审核通过，当角色账号表中已有该数据，更新is_delete字段>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 14:06
     * @param accountRole
     * @return int <返回值说明>
     */
    int updateAgreeAccount(AccountRole accountRole);

    /**
     * description:<审核通过，查询角色账号表中是否有对应数据 >
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 14:04
     * @param accountRole
     * @return int <返回值说明>
     */
    int getCount(AccountRole accountRole);
}
