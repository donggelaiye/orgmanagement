package com.ucarinc.orgmanagement.entity.form;

import java.util.List;

/**
 * description: 员工搜索参数表单类
 * @author 陈锦宾 （1164857566@qq.com）
 * @date 2019-08-03 16:37:00
 * @version 1.0
 */
public class EmpSearchForm {

    private String empId;

    private String empName;

    private String accountLogin;

    private Integer isDimission;

    private String empDepart;

    private String superDepart;

    private List<String> grantDeptList;

    private String accountName;


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

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public Integer getIsDimission() {
        return isDimission;
    }

    public void setIsDimission(Integer isDimission) {
        this.isDimission = isDimission;
    }

    public String getEmpDepart() {
        return empDepart;
    }

    public void setEmpDepart(String empDepart) {
        this.empDepart = empDepart;
    }

    public String getSuperDepart() {
        return superDepart;
    }

    public void setSuperDepart(String superDepart) {
        this.superDepart = superDepart;
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
