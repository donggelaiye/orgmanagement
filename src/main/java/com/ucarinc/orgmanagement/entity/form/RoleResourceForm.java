package com.ucarinc.orgmanagement.entity.form;

public class RoleResourceForm {

    private Long roleId;
    private String roleName;
    private Byte bussinessLineId;
    private String bussinessLineName;
    private String approveAccount;
    private String approveId;
    private String approveName;
    private String deptName;
    private String approveDept;
    private String approveDeptName;
    private String permissionName;
    //前台搜随条件
    private Long resourceId;
    //返回的权限id集合
    private String resourceIdStr;
    private Byte status;

    public String getResourceIdStr() {
        return resourceIdStr;
    }

    public void setResourceIdStr(String resourceIdStr) {
        this.resourceIdStr = resourceIdStr;
    }

    public String getBussinessLineName() {
        return bussinessLineName;
    }

    public void setBussinessLineName(String bussinessLineName) {
        this.bussinessLineName = bussinessLineName;
    }

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

    public Byte getBussinessLineId() {
        return bussinessLineId;
    }

    public void setBussinessLineId(Byte bussinessLineId) {
        this.bussinessLineId = bussinessLineId;
    }

    public String getApproveAccount() {
        return approveAccount;
    }

    public void setApproveAccount(String approveAccount) {
        this.approveAccount = approveAccount;
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
        this.approveName = approveName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getApproveDept() {
        return approveDept;
    }

    public void setApproveDept(String approveDept) {
        this.approveDept = approveDept;
    }

    public String getApproveDeptName() {
        return approveDeptName;
    }

    public void setApproveDeptName(String approveDeptName) {
        this.approveDeptName = approveDeptName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
