package com.ucarinc.orgmanagement.entity.form;

import java.util.Date;

/**
 * @Date 2019/8/9
 * @Time 16:45
 * @Description 角色审核查询表单
 * @Author jiajin.chen@ucarinc.com
 */
public class RoleApproveForm {
    private Long id;

    private String applyCode;

    private String roleId;

    private String roleName;

    private String roleApproveName;

    private String bussinessLine;

    private String applyAccountLogin;

    private String applyEmpId;

    private String applyEmpName;

    private String applyDept;

    private Byte isDelete;

    private String operateEmp;

    private Date operateTime;

    private Byte status;

    private String approveAccountName;

    private Date approveTime;

    private String modifyAccountName;

    private Date createTime;

    private Long createEmp;

    private Date modifyTime;

    private Long modifyEmp;

    private String remark;

    private Date applyTimeStart;

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    private String accountLogin;

    public Date getApplyTimeStart() {
        return applyTimeStart;
    }

    public void setApplyTimeStart(Date applyTimeStart) {
        this.applyTimeStart = applyTimeStart;
    }

    public Date getApplyTimeEnd() {
        return applyTimeEnd;
    }

    public void setApplyTimeEnd(Date applyTimeEnd) {
        this.applyTimeEnd = applyTimeEnd;
    }

    public Date getOperateTimeStart() {
        return operateTimeStart;
    }

    public void setOperateTimeStart(Date operateTimeStart) {
        this.operateTimeStart = operateTimeStart;
    }

    public Date getOperateTimeEnd() {
        return operateTimeEnd;
    }

    public void setOperateTimeEnd(Date operateTimeEnd) {
        this.operateTimeEnd = operateTimeEnd;
    }

    private Date applyTimeEnd;
    private Date operateTimeStart;
    private Date operateTimeEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleApproveName() {
        return roleApproveName;
    }

    public void setRoleApproveName(String roleApproveName) {
        this.roleApproveName = roleApproveName == null ? null : roleApproveName.trim();
    }

    public String getBussinessLine() {
        return bussinessLine;
    }

    public void setBussinessLine(String bussinessLine) {
        this.bussinessLine = bussinessLine == null ? null : bussinessLine.trim();
    }

    public String getApplyAccountLogin() {
        return applyAccountLogin;
    }

    public void setApplyAccountLogin(String applyAccountLogin) {
        this.applyAccountLogin = applyAccountLogin == null ? null : applyAccountLogin.trim();
    }

    public String getApplyEmpId() {
        return applyEmpId;
    }

    public void setApplyEmpId(String applyEmpId) {
        this.applyEmpId = applyEmpId;
    }

    public String getApplyEmpName() {
        return applyEmpName;
    }

    public void setApplyEmpName(String applyEmpName) {
        this.applyEmpName = applyEmpName == null ? null : applyEmpName.trim();
    }

    public String getApplyDept() {
        return applyDept;
    }

    public void setApplyDept(String applyDept) {
        this.applyDept = applyDept == null ? null : applyDept.trim();
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getOperateEmp() {
        return operateEmp;
    }

    public void setOperateEmp(String operateEmp) {
        this.operateEmp = operateEmp == null ? null : operateEmp.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getApproveAccountName() {
        return approveAccountName;
    }

    public void setApproveAccountName(String approveAccountName) {
        this.approveAccountName = approveAccountName == null ? null : approveAccountName.trim();
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getModifyAccountName() {
        return modifyAccountName;
    }

    public void setModifyAccountName(String modifyAccountName) {
        this.modifyAccountName = modifyAccountName == null ? null : modifyAccountName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(Long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
