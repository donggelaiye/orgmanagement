package com.ucarinc.orgmanagement.entity.vo;

import com.ucarinc.orgmanagement.entity.Department;

/**
 * description: 部门信息导出结果封装
 * @author daijianzhao
 * @version 1.0
 * @date 2019-08-13 23:15
 */
public class DeptExportInfoResult {
    private String deptId;
    private String deptName;
    private String empId;
    private String empName;
    private String empPhone;
    private String cityName;
    private String deptLevel;
    private String deptType;
    private String officeSpot;
    private String superDeptName;
    private String bussinessLineList;
    private String companyList;
    private String status;
    private static final String[] deptLevelName={"总部","分公司","管理部","区域","办公点"};
    private static final String[] deptTypeName={"门店","停车场","交车中心","维修厂"};

    public DeptExportInfoResult(Department dept){
        this.deptId=dept.getDeptId();
        this.deptName=dept.getDeptName();
        this.empId=String.valueOf(dept.getEmpId());
        this.empName=dept.getEmpName();
        this.empPhone=dept.getEmpPhone();
        if(dept.getDeptLevel()!=null){
            this.deptLevel=deptLevelName[dept.getDeptLevel()];
        }
        if(dept.getDeptType()!=null){
            this.deptType=deptTypeName[dept.getDeptType()];
        }
        this.officeSpot=String.valueOf(dept.getOfficeSpot());
        if(dept.getStatus()==1){
            this.status="有效";
        }else {
            this.status="无效";
        }
    }
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getOfficeSpot() {
        return officeSpot;
    }

    public void setOfficeSpot(String officeSpot) {
        this.officeSpot = officeSpot;
    }

    public String getSuperDeptName() {
        return superDeptName;
    }

    public void setSuperDeptName(String superDeptName) {
        this.superDeptName = superDeptName;
    }

    public String getBussinessLineList() {
        return bussinessLineList;
    }

    public void setBussinessLineList(String bussinessLineList) {
        this.bussinessLineList = bussinessLineList;
    }

    public String getCompanyList() {
        return companyList;
    }

    public void setCompanyList(String companyList) {
        this.companyList = companyList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\ndeptExportInfoResult{" +
                "deptId='" + deptId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empPhone='" + empPhone + '\'' +
                ", cityName='" + cityName + '\'' +
                ", deptLevel='" + deptLevel + '\'' +
                ", deptType='" + deptType + '\'' +
                ", officeSpot='" + officeSpot + '\'' +
                ", superDeptName='" + superDeptName + '\'' +
                ", bussinessLineList='" + bussinessLineList + '\'' +
                ", companyList='" + companyList + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
