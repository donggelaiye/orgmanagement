package com.ucarinc.orgmanagement.entity.form;

import java.util.Date;
import java.util.List;

/**
 * 角色申请 表单查询类
 * @Author: 徐才艺（caiyi.xu@ucarinc.com）
 * @CreateDate: 2019/8/19 17:02
 * @Version: 1.0
 */
public class RoleApplySearchForm {

    private String applyCode;

    private String roleId;

    private String roleName;

    private String roleApproveName;

    private String applyAccountLogin;

    private String applyEmpId;

    private String applyEmpName;

    private String applyDept;

    private Byte status;
    private Date applyTimeStart;
    private Date applyTimeEnd;
    private Date operateTimeStart;
    private Date operateTimeEnd;
    private String account;
    private List<String> grantDeptList;

    public List<String> getGrantDeptList() {
        return grantDeptList;
    }

    public void setGrantDeptList(List<String> grantDeptList) {
        this.grantDeptList = grantDeptList;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
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
}
