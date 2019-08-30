package com.ucarinc.orgmanagement.entity;

import lombok.Data;

import java.util.Date;

/**
 * @program: orgmanagement
 * @description: 公司实体类
 * @author: Wu.tengfei
 * @create: Create in 11:09 2019/8/1
 **/
public class Company {
    //公司物理主键
    private Long id;

    //公司名称
    private String coName;

    //统一社会信用代码
    private String coCreditCode;

    //公司类型
    private Byte coType;

    //营业期限
    private Date businessTime;

    //住址
    private String coAddress;

    //经营范围
    private String businessScope;

    //法人代表
    private String legalRepresentative;

    //注册资本
    private String registeredCapital;

    //成立时间
    private Date setDate;

    //登记机关
    private String registeredAuthority;

    //核准日期
    private Date approvalDate;

    //登记状态
    private String registeredStatus;

    //营业执照
    private String businessLicense;

    //公司性质
    private Byte coNature;

    //注册地址
    private String registeredAddress;

    //开户银行
    private String openBank;

    //公司账号
    private String coAccount;

    //注册电话
    private Long registeredPhone;

    //总公司标志
    private Byte coMark;

    //修改人
    private Long modifyEmp;

    //创建时间
    private Date createTime;

    //修改时间
    private Date modifyTime;

    //备注
    private String remark;

    //是否已删除
    private Byte isDelete;

    //修改人
    private Long createEmp;

    //修改人名字
    private String modifyEmpName;

    //创建人名字
    private String createEmpName;

    //状态
    private Byte status;

    public String getModifyEmpName() {
        return modifyEmpName;
    }

    public void setModifyEmpName(String modifyName) {
        this.modifyEmpName = modifyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName == null ? null : coName.trim();
    }

    public String getCoCreditCode() {
        return coCreditCode;
    }

    public void setCoCreditCode(String coCreditCode) {
        this.coCreditCode = coCreditCode == null ? null : coCreditCode.trim();
    }

    public String getCreateEmpName() {
        return createEmpName;
    }

    public void setCreateEmpName(String createEmpName) {
        this.createEmpName = createEmpName;
    }

    public Byte getCoType() {
        return coType;
    }

    public void setCoType(Byte coType) {
        this.coType = coType;
    }

    public Date getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
    }

    public String getCoAddress() {
        return coAddress;
    }

    public void setCoAddress(String coAddress) {
        this.coAddress = coAddress == null ? null : coAddress.trim();
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital == null ? null : registeredCapital.trim();
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }

    public String getRegisteredAuthority() {
        return registeredAuthority;
    }

    public void setRegisteredAuthority(String registeredAuthority) {
        this.registeredAuthority = registeredAuthority == null ? null : registeredAuthority.trim();
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getRegisteredStatus() {
        return registeredStatus;
    }

    public void setRegisteredStatus(String registeredStatus) {
        this.registeredStatus = registeredStatus == null ? null : registeredStatus.trim();
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    public Byte getCoNature() {
        return coNature;
    }

    public void setCoNature(Byte coNature) {
        this.coNature = coNature;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress == null ? null : registeredAddress.trim();
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank == null ? null : openBank.trim();
    }

    public String getCoAccount() {
        return coAccount;
    }

    public void setCoAccount(String coAccount) {
        this.coAccount = coAccount == null ? null : coAccount.trim();
    }

    public Long getRegisteredPhone() {
        return registeredPhone;
    }

    public void setRegisteredPhone(Long registeredPhone) {
        this.registeredPhone = registeredPhone;
    }

    public Byte getCoMark() {
        return coMark;
    }

    public void setCoMark(Byte coMark) {
        this.coMark = coMark;
    }

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", coName='" + coName + '\'' +
                ", coCreditCode='" + coCreditCode + '\'' +
                ", coType=" + coType +
                ", businessTime=" + businessTime +
                ", coAddress='" + coAddress + '\'' +
                ", businessScope='" + businessScope + '\'' +
                ", legalRepresentative='" + legalRepresentative + '\'' +
                ", registeredCapital='" + registeredCapital + '\'' +
                ", setDate=" + setDate +
                ", registeredAuthority='" + registeredAuthority + '\'' +
                ", approvalDate=" + approvalDate +
                ", registeredStatus='" + registeredStatus + '\'' +
                ", businessLicense='" + businessLicense + '\'' +
                ", coNature=" + coNature +
                ", registeredAddress='" + registeredAddress + '\'' +
                ", openBank='" + openBank + '\'' +
                ", coAccount='" + coAccount + '\'' +
                ", registeredPhone=" + registeredPhone +
                ", coMark=" + coMark +
                ", modifyEmp=" + modifyEmp +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", remark='" + remark + '\'' +
                ", isDelete=" + isDelete +
                ", createEmp=" + createEmp +
                ", modifyEmpName='" + modifyEmpName + '\'' +
                ", createEmpName='" + createEmpName + '\'' +
                ", status=" + status +
                '}';
    }
}