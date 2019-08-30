package com.ucarinc.orgmanagement.entity.form;

import com.ucarinc.orgmanagement.entity.Account;

import java.util.List;

/**
 * description:修改账号,涉及数据权限变更
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-17 16:51
 */
public class AccountModifyForm extends Account {
    private List<String> grantDeptList;
    private List<String> revokeDeptList;




    public List<String> getGrantDeptList() {
        return grantDeptList;
    }

    public void setGrantDeptList(List<String> grantDeptList) {
        this.grantDeptList = grantDeptList;
    }

    public List<String> getRevokeDeptList() {
        return revokeDeptList;
    }

    public void setRevokeDeptList(List<String> revokeDeptList) {
        this.revokeDeptList = revokeDeptList;
    }
}
