package com.ucarinc.orgmanagement.entity;

/**
 * description: 员工实体类
 * @author jinbin.chen （jinbin.chen@ucarinc.com）
 * @date 2019-08-02 16:02:00
 * @version 1.0
 */
public class Employee extends GlobalEntity {

    private String empId;

    private String empName;

    private Integer empSex;

    private String empPhone;

    private String empEmail;

    private String empDepart;

    private Integer isDimission;

    private String oldEmpId;

    private String oldEmpName;

    private String empDepartName;

    private String oldEmpDeptName;

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
        this.empName = empName == null ? null : empName.trim();
    }

    public Integer getEmpSex() {
        return empSex;
    }

    public void setEmpSex(Integer empSex) {
        this.empSex = empSex;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone == null ? null : empPhone.trim();
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail == null ? null : empEmail.trim();
    }

    public String getEmpDepart() {
        return empDepart;
    }

    public void setEmpDepart(String empDepart) {
        this.empDepart = empDepart == null ? null : empDepart.trim();
    }

    public Integer getIsDimission() {
        return isDimission;
    }

    public void setIsDimission(Integer isDimission) {
        this.isDimission = isDimission;
    }

    public String getOldEmpId() {
        return oldEmpId;
    }

    public void setOldEmpId(String oldEmpId) {
        this.oldEmpId = oldEmpId;
    }

    public String getOldEmpName() {
        return oldEmpName;
    }

    public void setOldEmpName(String oldEmpName) {
        this.oldEmpName = oldEmpName;
    }

    public String getOldEmpDeptName() {
        return oldEmpDeptName;
    }

    public void setOldEmpDeptName(String oldEmpDeptName) {
        this.oldEmpDeptName = oldEmpDeptName;
    }

    public String getEmpDepartName() {
        return empDepartName;
    }

    public void setEmpDepartName(String empDepartName) {
        this.empDepartName = empDepartName;
    }

}