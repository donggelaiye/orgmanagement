package com.ucarinc.orgmanagement.entity.vo;

/**
 * @Date 2019/8/8
 * @Time 9:33
 * @Description 角色业务线关联查询返回内容
 * @Author jiajin.chen@ucarinc.com
 */
public class RoleLineResult {
    private Long roleId;

    private String roleName;

    private Byte  status;

    private Byte bussinesslineid;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getBussinesslineid() {
        return bussinesslineid;
    }

    public void setBussiness_line_id(Byte bussinesslineid) {
        this.bussinesslineid = bussinesslineid;
    }
}
