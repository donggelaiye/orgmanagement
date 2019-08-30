package com.ucarinc.orgmanagement.entity.form;

import java.io.Serializable;

/**
 * description: 前端表单数据封装
 * @author daijianzhao
 * @version 1.0
 * @date 2019-08-11 22:35
 */
public class DeptSearchForm implements Serializable {
    private String deptName;
    private Long empId;
    private String empName;
    private String empPhone;
    private String cityInterCode;
    private String countyInterCode;
    private Byte isCity;
    private Byte deptLevel;
    private String superDeptId;
    private Byte status;
    private Byte deptType;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getCityInterCode() {
        return cityInterCode;
    }

    public void setCityInterCode(String cityInterCode) {
        this.cityInterCode = cityInterCode;
    }

    public String getCountyInterCode() {
        return countyInterCode;
    }

    public void setCountyInterCode(String countyInterCode) {
        this.countyInterCode = countyInterCode;
    }

    public Byte getIsCity() {
        return isCity;
    }

    public void setIsCity(Byte isCity) {
        this.isCity = isCity;
    }

    public Byte getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(Byte deptLevel) {
        this.deptLevel = deptLevel;
    }

    public String getSuperDeptId() {
        return superDeptId;
    }

    public void setSuperDeptId(String superDeptId) {
        this.superDeptId = superDeptId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getDeptType() {
        return deptType;
    }

    public void setDeptType(Byte deptType) {
        this.deptType = deptType;
    }


}
