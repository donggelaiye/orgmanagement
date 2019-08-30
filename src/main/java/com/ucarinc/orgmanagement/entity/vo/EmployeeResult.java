package com.ucarinc.orgmanagement.entity.vo;

import com.ucarinc.orgmanagement.entity.Employee;

/**
 * description: 员工数据返回类型
 * @author jinbin,chen （jinbin,chen@ucarinccom）
 * @date 2019-08-05 14:41:00
 * @version 1.0
 */
public class EmployeeResult extends Employee {

    private String accountLogin;

    private String deptName;

    private String superDeptId;

    private String superDepartName;

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSuperDeptId() {
        return superDeptId;
    }

    public void setSuperDeptId(String superDeptId) {
        this.superDeptId = superDeptId;
    }

    public String getSuperDepartName() {
        return superDepartName;
    }

    public void setSuperDepartName(String superDepartName) {
        this.superDepartName = superDepartName;
    }

}
