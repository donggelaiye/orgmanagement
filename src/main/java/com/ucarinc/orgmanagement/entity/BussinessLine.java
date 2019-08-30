package com.ucarinc.orgmanagement.entity;

import java.io.Serializable;
import java.util.Date;

public class BussinessLine implements Serializable {
    private Long id;

    private Byte bussinessLineId;

    private String bussinessLineName;

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

    public Byte getBussinessLineId() {
        return bussinessLineId;
    }

    public void setBussinessLineId(Byte bussinessLineId) {
        this.bussinessLineId = bussinessLineId;
    }

    public String getBussinessLineName() {
        return bussinessLineName;
    }

    public void setBussinessLineName(String bussinessLineName) {
        this.bussinessLineName = bussinessLineName == null ? null : bussinessLineName.trim();
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
}