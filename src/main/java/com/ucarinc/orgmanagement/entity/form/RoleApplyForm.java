package com.ucarinc.orgmanagement.entity.form;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @Description:
 * @Author: 徐才艺（caiyi.xu@ucarinc.com）
 * @CreateDate: 2019/8/5 14:08
 * @Version: 1.0
 */
public class RoleApplyForm {

    private String applyCode;

    private Long roleId;

    private String roleName;

    private String roleApproveName;

    private String applyAccountLogin;

    private Long applyEmpId;

    private String applyEmpName;

    private String applyDept;

    private Byte status;
    private Date applyTimeStart;
    private Date applyTimeEnd;
    private Date operateTimeStart;
    private Date operateTimeEnd;

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleApproveName() {
        return roleApproveName;
    }

    public void setRoleApproveName(String roleApproveName) {
        this.roleApproveName = roleApproveName;
    }

    public String getApplyAccountLogin() {
        return applyAccountLogin;
    }

    public void setApplyAccountLogin(String applyAccountLogin) {
        this.applyAccountLogin = applyAccountLogin;
    }

    public Long getApplyEmpId() {
        return applyEmpId;
    }

    public void setApplyEmpId(Long applyEmpId) {
        this.applyEmpId = applyEmpId;
    }

    public String getApplyEmpName() {
        return applyEmpName;
    }

    public void setApplyEmpName(String applyEmpName) {
        this.applyEmpName = applyEmpName;
    }

    public String getApplyDept() {
        return applyDept;
    }

    public void setApplyDept(String applyDept) {
        this.applyDept = applyDept;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
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

    @Override
    public String toString() {
        return "RoleApplyForm{" +
                "applyCode='" + applyCode + '\'' +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleApproveName='" + roleApproveName + '\'' +
                ", applyAccountLogin='" + applyAccountLogin + '\'' +
                ", applyEmpId=" + applyEmpId +
                ", applyEmpName='" + applyEmpName + '\'' +
                ", applyDept='" + applyDept + '\'' +
                ", status=" + status +
                ", applyTimeStart=" + applyTimeStart +
                ", applyTimeEnd=" + applyTimeEnd +
                ", operateTimeStart=" + operateTimeStart +
                ", operateTimeEnd=" + operateTimeEnd +
                '}';
    }
}
