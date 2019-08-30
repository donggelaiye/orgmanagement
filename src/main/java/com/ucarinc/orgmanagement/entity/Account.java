package com.ucarinc.orgmanagement.entity;

/**
 * description: 账号实体类
 * @author 赖家美 （2477637880@qq.com）
 * @date 2019-08-08 9:36:00
 * @version 1.0
 */
public class Account extends GlobalEntity{

    private Long id;

    private String accountLogin;

    private String accountPassword;

    private Byte isLinkEmp;

    private String empId;

    private String accountEmail;

    private Integer dataPermissionId;

    private Byte status;

    public static final byte ACCOUNT_STATUS_FROZZEN = 0;

    public static final byte ACCOUNT_STATUS_INVALID = 2;

    public static final byte ACCOUNT_STATUS_NORMAL = 1;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin == null ? null : accountLogin.trim();
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword == null ? null : accountPassword.trim();
    }

    public Byte getIsLinkEmp() {
        return isLinkEmp;
    }

    public void setIsLinkEmp(Byte isLinkEmp) {
        this.isLinkEmp = isLinkEmp;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail == null ? null : accountEmail.trim();
    }

    public Integer getDataPermissionId() {
        return dataPermissionId;
    }

    public void setDataPermissionId(Integer dataPermissionId) {
        this.dataPermissionId = dataPermissionId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountLogin='" + accountLogin + '\'' +
                ", accountPassword='" + accountPassword + '\'' +
                ", isLinkEmp=" + isLinkEmp +
                ", empId=" + empId +
                ", accountEmail='" + accountEmail + '\'' +
                ", dataPermissionId=" + dataPermissionId +
                ", status=" + status +
                '}';
    }
}
