package com.ucarinc.orgmanagement.entity.vo;

import com.ucarinc.orgmanagement.entity.GlobalEntity;

import java.util.List;

/**
 * description: 账号输出的结果类
 * @author 赖家美 （2477637880@qq.com）
 * @date 2019-08-08 9:35:00
 * @version 1.0
 */
public class AccountResult extends GlobalEntity {

    private String accountLogin;
    private String empId;
    private String empName;
    private String deptName;
    private Integer dataPermissionId;
    private Byte status;
    private String accountEmail;
    private Byte isLinkEmp;
    private String empDepart;
    private List<String> originPermission;

    public List<String> getOriginPermission() {
        return originPermission;
    }

    public void setOriginPermission(List<String> originPermission) {
        this.originPermission = originPermission;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsLinkEmp() {
        return isLinkEmp;
    }

    public void setIsLinkEmp(Byte isLinkEmp) {
        this.isLinkEmp = isLinkEmp;
    }

    public String getEmpDepart() {
        return empDepart;
    }

    public void setEmpDepart(String empDepart) {
        this.empDepart = empDepart;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

}
