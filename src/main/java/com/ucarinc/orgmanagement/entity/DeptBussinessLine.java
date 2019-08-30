package com.ucarinc.orgmanagement.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class DeptBussinessLine implements Serializable {
    private Long id;

    private String deptId;

    private Byte bussinessLineId;

    private Byte isDelete;

    private Long createEmp;

    private Date createTime;

    private Long modifyEmp;

    private Date modifyTime;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public Byte getBussinessLineId() {
        return bussinessLineId;
    }

    public void setBussinessLineId(Byte bussinessLineId) {
        this.bussinessLineId = bussinessLineId;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(Long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptBussinessLine that = (DeptBussinessLine) o;
        return deptId.equals(that.deptId) &&
                bussinessLineId.equals(that.bussinessLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptId, bussinessLineId);
    }
}