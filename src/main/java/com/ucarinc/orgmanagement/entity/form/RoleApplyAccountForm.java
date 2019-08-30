package com.ucarinc.orgmanagement.entity.form;

import java.util.Date;

/**
 * @Description: 角色申请新建页面中，添加账号的表格属性
 * @Author: 徐才艺（caiyi.xu@ucarinc.com）
 * @CreateDate: 2019/8/8 21:50
 * @Version: 1.0
 */
public class RoleApplyAccountForm{

    private String accountLogin;
    private Long empId;
    private String empName;
    private String deptName;
    private Integer dataPermissionId;
    private Byte isLinkEmp;
    private Byte status;
    private String modifyEmp;
    private Date modifyTime;
    /**
     * 表单中有一个申请操作
     */
    private Byte operateType;

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDataPermissionId() {
        return dataPermissionId;
    }

    public void setDataPermissionId(Integer dataPermissionId) {
        this.dataPermissionId = dataPermissionId;
    }

    public Byte getIsLinkEmp() {
        return isLinkEmp;
    }

    public void setIsLinkEmp(Byte isLinkEmp) {
        this.isLinkEmp = isLinkEmp;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(String modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Byte getOperateType() {
        return operateType;
    }

    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }

    @Override
    public String toString() {
        return "RoleApplyAccountForm{" +
                "accountLogin='" + accountLogin + '\'' +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", dataPermissionId=" + dataPermissionId +
                ", isLinkEmp=" + isLinkEmp +
                ", status=" + status +
                ", modifyEmp=" + modifyEmp +
                ", modifyTime=" + modifyTime +
                ", operateType=" + operateType +
                '}';
    }
}
