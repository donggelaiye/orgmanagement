package com.ucarinc.orgmanagement.entity.vo;

import com.ucarinc.orgmanagement.entity.Role;

/**
 * @Description: 包含角色信息和角色包含业务线名称
 * @Author: 徐才艺（caiyi.xu@ucarinc.com）
 * @CreateDate: 2019/8/7 9:10
 * @Version: 1.0
 */
public class RoleInfoResult {
    private Role role;
    // 多条业务线用逗号分隔
    private String businessLineName;

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "RoleInfoResult{" +
                "role=" + role +
                ", businessLineName='" + businessLineName + '\'' +
                '}';
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getBusinessLineName() {
        return businessLineName;
    }

    public void setBusinessLineName(String businessLineName) {
        this.businessLineName = businessLineName;
    }
}
