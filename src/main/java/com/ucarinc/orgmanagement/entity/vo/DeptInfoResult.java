package com.ucarinc.orgmanagement.entity.vo;

import com.ucarinc.orgmanagement.entity.Company;
import com.ucarinc.orgmanagement.entity.Department;

import java.io.Serializable;
import java.util.List;

/**
 * description: 部门查询返回结果
 * @author daijianzhao
 * @version 1.0
 * @date 2019-08-11 22:24
 */
public class DeptInfoResult implements Serializable {
    private Department dept;
    private String cityName;
    private String superDeptName;
    private String logogramBussinessLine;
    private String wholeBussinessLine;
    private String[] bussinessLineArr;

    private String logogramCompany;
    private String wholeCompany;
    private List<Company> companyList;

    private List<String> cityList;

    private String createEmpName;
    private String modifyEmpName;

    public String getCreateEmpName() {
        return createEmpName;
    }

    public void setCreateEmpName(String createEmpName) {
        this.createEmpName = createEmpName;
    }

    public String getModifyEmpName() {
        return modifyEmpName;
    }

    public void setModifyEmpName(String modifyEmpName) {
        this.modifyEmpName = modifyEmpName;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSuperDeptName() {
        return superDeptName;
    }

    public void setSuperDeptName(String superDeptName) {
        this.superDeptName = superDeptName;
    }

    public String getLogogramBussinessLine() {
        return logogramBussinessLine;
    }

    public void setLogogramBussinessLine(String logogramBussinessLine) {
        this.logogramBussinessLine = logogramBussinessLine;
    }

    public String getWholeBussinessLine() {
        return wholeBussinessLine;
    }

    public void setWholeBussinessLine(String wholeBussinessLine) {
        this.wholeBussinessLine = wholeBussinessLine;
    }

    public String[] getBussinessLineArr() {
        return bussinessLineArr;
    }

    public void setBussinessLineArr(String[] bussinessLineArr) {
        this.bussinessLineArr = bussinessLineArr;
    }

    public String getLogogramCompany() {
        return logogramCompany;
    }

    public void setLogogramCompany(String logogramCompany) {
        this.logogramCompany = logogramCompany;
    }

    public String getWholeCompany() {
        return wholeCompany;
    }

    public void setWholeCompany(String wholeCompany) {
        this.wholeCompany = wholeCompany;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

}
