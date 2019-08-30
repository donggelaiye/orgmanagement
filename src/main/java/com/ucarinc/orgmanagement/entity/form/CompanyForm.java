package com.ucarinc.orgmanagement.entity.form;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: orgmanagement
 * @description: 公司模块查询类
 * @author: Wu.tengfei
 * @create: Create in 14:24 2019/8/5
 **/
@Data
public class CompanyForm implements Serializable {
    //公司名称
    private String coName;

    //统一社会信用代码
    private String coCreditCode;

    //公司类型
    private Byte coType;

    //公司性质
    private Byte coNature;

    //状态
    private Byte status;

    //营业开始时间
    private Date startTime;

    //营业结束时间
    private Date endTime;

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getCoCreditCode() {
        return coCreditCode;
    }

    public void setCoCreditCode(String coCreditCode) {
        this.coCreditCode = coCreditCode;
    }

    public Byte getCoType() {
        return coType;
    }

    public void setCoType(Byte coType) {
        this.coType = coType;
    }

    public Byte getCoNature() {
        return coNature;
    }

    public void setCoNature(Byte coNature) {
        this.coNature = coNature;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
