package com.ucarinc.orgmanagement.entity.form;

import com.ucarinc.orgmanagement.entity.Account;

/**
 * description: 员工搜索表单
 * @author ruoyao.gao （ruoyao.gao@ucarinc.com）
 * @date 2019-08-19 10:21:00
 * @version 1.0
 */
public class AccountSelectForm extends Account {

    private String empName;
    private String empDepart;
    private String deptName;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
