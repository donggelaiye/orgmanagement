package com.ucarinc.orgmanagement.entity.form;

import com.ucarinc.orgmanagement.entity.GlobalEntity;

import java.util.List;

public class RoleAccountForm extends GlobalEntity {

    private Long roleId;

    private String accountLogin;

    private List<String> accountLoginList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public List<String> getAccountLoginList() {
        return accountLoginList;
    }

    public void setAccountLoginList(List<String> accountLoginList) {
        this.accountLoginList = accountLoginList;
    }

    @Override
    public String toString() {
        return "RoleAccountForm{" +
                "roleId=" + roleId +
                ", accountLogin='" + accountLogin + '\'' +
                ", accountLoginList=" + accountLoginList +
                '}';
    }
}
