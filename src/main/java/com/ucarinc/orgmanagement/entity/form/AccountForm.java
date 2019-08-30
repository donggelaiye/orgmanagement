package com.ucarinc.orgmanagement.entity.form;

import com.ucarinc.orgmanagement.entity.Account;

import java.util.List;

/**
 * description: 账号输入的表单类
 * @author 赖家美 （2477637880@qq.com）
 * @date 2019-08-08 9:34:00
 * @version 1.0
 */
public class AccountForm {

    private String accountLogin;
    private String empId;
    private String empName;
    private Integer dataPermissionId;
    private String empDepartName;
    private String empDepart;
    private Byte isLinkEmp;
    private Byte status;
    private List<String> grantDeptList;

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

    public Integer getDataPermissionId() {
        return dataPermissionId;
    }

    public void setDataPermissionId(Integer dataPermissionId) {
        this.dataPermissionId = dataPermissionId;
    }

    public String getEmpDepartName() {
        return empDepartName;
    }

    public void setEmpDepartName(String empDepartName) {
        this.empDepartName = empDepartName;
    }

    public String getEmpDepart() {
        return empDepart;
    }

    public void setEmpDepart(String empDepart) {
        this.empDepart = empDepart;
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

    @Override
    public String toString() {
        return "AccountForm{" +
                "accountLogin='" + accountLogin + '\'' +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", dataPermissionId=" + dataPermissionId +
                ", empDepart='" + empDepart + '\'' +
                ", isLinkEmp=" + isLinkEmp +
                ", status=" + status +
                '}';
    }
}
