package com.ucarinc.orgmanagement.entity.form;


import java.util.List;

public class AccountSearchForm{

    private String accountLogin;

    private String empId;

    private String empName;

    private String empDepart;

    private Integer dataPermissionId;

    private Byte isLinkEmp;

    private Byte status;

    private List<String> grantDeptList;

    private String accountName;

    public String getEmpDepart() {
        return empDepart;
    }

    public void setEmpDepart(String empDepart) {
        this.empDepart = empDepart;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String  getEmpId() {
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

    public List<String> getGrantDeptList() {
        return grantDeptList;
    }

    public void setGrantDeptList(List<String> grantDeptList) {
        this.grantDeptList = grantDeptList;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
