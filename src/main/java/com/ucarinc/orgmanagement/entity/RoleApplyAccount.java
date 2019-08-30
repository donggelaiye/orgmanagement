package com.ucarinc.orgmanagement.entity;

import java.util.Date;

public class RoleApplyAccount {
    private Long id;

    private String applyCode;

    private String applyAccount;

    private String empName;

    private Long empId;

    private String empDept;

    private Byte operateType;

    private Byte isDelete;

    private Date createTime;

    private Long createEmp;

    private Date modifyTime;

    private Long modifyEmp;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }

    public String getApplyAccount() {
        return applyAccount;
    }

    public void setApplyAccount(String applyAccount) {
        this.applyAccount = applyAccount == null ? null : applyAccount.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpDept() {
        return empDept;
    }

    public void setEmpDept(String empDept) {
        this.empDept = empDept == null ? null : empDept.trim();
    }

    public Byte getOperateType() {
        return operateType;
    }

    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(Long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "RoleApplyAccount{" +
                "id=" + id +
                ", applyCode='" + applyCode + '\'' +
                ", applyAccount='" + applyAccount + '\'' +
                ", empName='" + empName + '\'' +
                ", empId=" + empId +
                ", empDept='" + empDept + '\'' +
                ", operateType=" + operateType +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                ", createEmp=" + createEmp +
                ", modifyTime=" + modifyTime +
                ", modifyEmp=" + modifyEmp +
                ", remark='" + remark + '\'' +
                '}';
    }
}