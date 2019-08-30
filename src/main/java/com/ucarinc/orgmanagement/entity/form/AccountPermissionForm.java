package com.ucarinc.orgmanagement.entity.form;

import java.util.List;

/**
 * description:账号管理:权限分配页面提交参数
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-10 10:22
 */
public class AccountPermissionForm {

    private String loginAccount;
    private List<Long> revokeRoleIdList;
    private List<Long> grantRoleIdList;
    private List<Long> revokePermissionIdList;
    private List<Long> grantPermissionIdList;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public List<Long> getRevokeRoleIdList() {
        return revokeRoleIdList;
    }

    public void setRevokeRoleIdList(List<Long> revokeRoleIdList) {
        this.revokeRoleIdList = revokeRoleIdList;
    }

    public List<Long> getGrantRoleIdList() {
        return grantRoleIdList;
    }

    public void setGrantRoleIdList(List<Long> grantRoleIdList) {
        this.grantRoleIdList = grantRoleIdList;
    }

    public List<Long> getRevokePermissionIdList() {
        return revokePermissionIdList;
    }

    public void setRevokePermissionIdList(List<Long> revokePermissionIdList) {
        this.revokePermissionIdList = revokePermissionIdList;
    }

    public List<Long> getGrantPermissionIdList() {
        return grantPermissionIdList;
    }

    public void setGrantPermissionIdList(List<Long> grantPermissionIdList) {
        this.grantPermissionIdList = grantPermissionIdList;
    }
}
