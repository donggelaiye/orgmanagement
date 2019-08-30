package com.ucarinc.orgmanagement.entity;

import java.util.Date;

/**
 * description: 部门实体类
 * @author 赖家美 （2477637880@qq.com）
 * @date 2019-08-08 9:36:00
 * @version 1.0
 */
public class Department {
    private Long id;

    private String deptId;

    private Short officeSpot;

    private String deptName;

    private Long empId;

    private String empName;

    private String empPhone;

    private String empEmail;

    private String cityInterCode;

    private String countyInterCode;

    private Byte isCity;

    private String address;

    private String lon;

    private String lat;

    private String telephone;

    private Integer deptLevel;

    private String superDeptId;

    private Byte deptType;

    private Byte status;

    private Byte isDelete;

    private Long createEmp;

    private Date createTime;

    private Long modifyEmp;

    private Date modifyTime;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public Short getOfficeSpot() {
        return officeSpot;
    }

    public void setOfficeSpot(Short officeSpot) {
        this.officeSpot = officeSpot;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
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
        this.empName = empName == null ? null : empName.trim();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon == null ? null : lon.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(Integer deptLevel) {
        this.deptLevel = deptLevel;
    }

    public String getSuperDeptId() {
        return superDeptId;
    }

    public void setSuperDeptId(String superDeptId) {
        this.superDeptId = superDeptId == null ? null : superDeptId.trim();
    }

    public Byte getDeptType() {
        return deptType;
    }

    public void setDeptType(Byte deptType) {
        this.deptType = deptType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(Long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "\nDepartment{" +
                "id=" + id +
                ", deptId='" + deptId + '\'' +
                ", officeSpot=" + officeSpot +
                ", deptName='" + deptName + '\'' +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empPhone='" + empPhone + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", cityInterCode='" + cityInterCode + '\'' +
                ", countyInterCode='" + countyInterCode + '\'' +
                ", isCity=" + isCity +
                ", address='" + address + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", telephone='" + telephone + '\'' +
                ", deptLevel=" + deptLevel +
                ", superDeptId='" + superDeptId + '\'' +
                ", deptType=" + deptType +
                ", status=" + status +
                ", isDelete=" + isDelete +
                ", createEmp=" + createEmp +
                ", createTime=" + createTime +
                ", modifyEmp=" + modifyEmp +
                ", modifyTime=" + modifyTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}