package com.ucarinc.orgmanagement.entity.form;

import lombok.Data;

import java.io.Serializable;
/**
 * @program: orgmanagement
 * @description: 区域模块查询类
 * @author: Wu.tengfei
 * @create: Create in 11:09 2019/8/1
 **/
@Data
public class AreaForm implements Serializable {

    //国际代码
    private String interCode;

    //省份名称
    private String provinceName;

    //城市名称
    private String cityName;

    //区县名称
    private String countyName;

    //状态
    private Byte status;

    public String getInterCode() {
        return interCode;
    }

    public void setInterCode(String interCode) {
        this.interCode = interCode;
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

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
