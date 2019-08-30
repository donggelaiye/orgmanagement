package com.ucarinc.orgmanagement.entity;

import lombok.Data;

import java.util.Date;

/**
 * @program: orgmanagement
 * @description: 城市实体类
 * @author: Wu.tengfei
 * @create: Create in 11:09 2019/8/1
 **/
public class City {

    //城市物理主键
    private Long id;

    //城市国际代码
    private String interCode;

    //省份国际代码
    private String provinceInterCode;

    //省份名称
    private String provinceName;

    //城市名称
    private String cityName;

    //城市拼音
    private String cityPhonetic;

    //状态
    private Byte status;

    //修改人
    private Long modifyEmp;

    //修改时间
    private Date modifyTime;

    //创建时间
    private Date createTime;

    //备注
    private String remark;

    //是否已删除
    private Byte isDelete;

    //创建时间
    private Long createEmp;

    //修改人字符串
    private String modifyEmpName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterCode() {
        return interCode;
    }

    public void setInterCode(String interCode) {
        this.interCode = interCode;
    }

    public String getProvinceInterCode() {
        return provinceInterCode;
    }

    public void setProvinceInterCode(String provinceInterCode) {
        this.provinceInterCode = provinceInterCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityPhonetic() {
        return cityPhonetic;
    }

    public void setCityPhonetic(String cityPhonetic) {
        this.cityPhonetic = cityPhonetic;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getModifyEmpName() {
        return modifyEmpName;
    }

    public void setModifyEmpName(String modifyEmpName) {
        this.modifyEmpName = modifyEmpName;
    }
}
