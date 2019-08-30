package com.ucarinc.orgmanagement.entity;

import java.io.Serializable;

/**
 * @Date 2019/8/7
 * @Time 15:32
 * @Description 角色账号明细查询
 * @Author jiajin.chen@ucarinc.com
 */
public class RoleAccountDetail implements Serializable {
    private Long roleId;

    private String roleName;

    private Byte roleStatus;

    private Byte bussinessLineId;

    private String bussinessLineName;

    private String accountLogin;

    private String empId;

    private String empName;

    private String empDepart;

    private Byte  accountStatus;

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    private String  departName;

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

    public Byte getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Byte roleStatus) {
        this.roleStatus = roleStatus;
    }

    public Byte getBussinessLineId() {
        return bussinessLineId;
    }

    public void setBussinessLineId(Byte bussinessLineId) {
        this.bussinessLineId = bussinessLineId;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpDepart() {
        return empDepart;
    }

    public void setEmpDepart(String empDepart) {
        this.empDepart = empDepart;
    }

    public Byte getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Byte accountStatus) {
        this.accountStatus = accountStatus;
    }


    public String getBussinessLineName() {
        return bussinessLineName;
    }

    public void setBussinessLineName(String bussinessLineName) {
        this.bussinessLineName = bussinessLineName;
    }
}
