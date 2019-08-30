package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.form.RoleResourceForm;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date 2019/8/9
 * @Time 11:30
 * @Description
 * @Author jiajin.chen@ucarinc.com
 */
@Repository(value = "RoleResourceDetailMapper")
public interface RoleResourceDetailMapper {

    /**
     * description:<角色权限明细查询>
     * @author  陈家进 （jiajin.chen@ucarinc.com）
     * @date 2019/8/19 14:20
     * @param roleResourceForm
     * @return java.util.List<com.ucarinc.orgmanagement.entity.form.RoleResourceForm> <返回值说明>
     */
    List<RoleResourceForm> getRoleResourceList(RoleResourceForm roleResourceForm);

}
