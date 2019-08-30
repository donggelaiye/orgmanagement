package com.ucarinc.orgmanagement.entity.form;

import java.util.List;

/**
 * description:角色管理:分配权限 页面提交参数
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-10 15:34
 */
public class GrantRoleResourceForm {
    private Long roleId;
    private List<Long> grantResourceIdList;
    private List<Long> revokeResourceIdList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getGrantResourceIdList() {
        return grantResourceIdList;
    }

    public void setGrantResourceIdList(List<Long> grantResourceIdList) {
        this.grantResourceIdList = grantResourceIdList;
    }

    public List<Long> getRevokeResourceIdList() {
        return revokeResourceIdList;
    }

    public void setRevokeResourceIdList(List<Long> revokeResourceIdList) {
        this.revokeResourceIdList = revokeResourceIdList;
    }
}
