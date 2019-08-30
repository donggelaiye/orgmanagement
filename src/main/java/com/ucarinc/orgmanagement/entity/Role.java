package com.ucarinc.orgmanagement.entity;

/**
 * description: 角色实体类
 * @author 赖家美 （2477637880@qq.com）
 * @date 2019-08-08 9:37:00
 * @version 1.0
 */
public class Role extends GlobalEntity{

    private Long roleId;

    private String roleName;

    private String approveAccount;

    private String approveId;

    private String approveName;

    private String approveDepartment;

    private String description;

    private Byte status;

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
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getApproveAccount() {
        return approveAccount;
    }

    public void setApproveAccount(String approveAccount) {
        this.approveAccount = approveAccount == null ? null : approveAccount.trim();
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName == null ? null : approveName.trim();
    }

    public String getApproveDepartment() {
        return approveDepartment;
    }

    public void setApproveDepartment(String approveDepartment) {
        this.approveDepartment = approveDepartment == null ? null : approveDepartment.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}