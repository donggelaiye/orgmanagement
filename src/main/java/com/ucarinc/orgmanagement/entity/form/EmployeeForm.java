package com.ucarinc.orgmanagement.entity.form;

/**
 * description: 员工输入的表单类
 * @author 赖家美 （2477637880@qq.com）
 * @date 2019-08-08 9:34:00
 * @version 1.0
 */
public class EmployeeForm {

    private String empId;
    private String empName;
    private String accountLogin;
    private Integer isDimission;
    private String empDepartName;
    private String superDepartName;

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

    public String getEmpDepartName() {
        return empDepartName;
    }

    public void setEmpDepartName(String empDepartName) {
        this.empDepartName = empDepartName;
    }

    public String getSuperDepartName() {
        return superDepartName;
    }

    public void setSuperDepartName(String superDepartName) {
        this.superDepartName = superDepartName;
    }
}
