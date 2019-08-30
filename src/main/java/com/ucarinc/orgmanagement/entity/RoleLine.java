package com.ucarinc.orgmanagement.entity;

import java.util.Date;

public class RoleLine extends GlobalEntity{

    private Long roleId;

    private Byte bussinessLineId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Byte getBussinessLineId() {
        return bussinessLineId;
    }

    public void setBussinessLineId(Byte bussinessLineId) {
        this.bussinessLineId = bussinessLineId;
    }
}