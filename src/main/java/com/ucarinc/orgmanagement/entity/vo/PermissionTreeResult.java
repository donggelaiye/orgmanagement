package com.ucarinc.orgmanagement.entity.vo;

import com.ucarinc.orgmanagement.entity.vo.TreeResult;

/**
 * description: 树状菜单
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-05 17:01
 */
public class PermissionTreeResult extends TreeResult {

    private String permissionName;

    private String permissionCode;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }


}
