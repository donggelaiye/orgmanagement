package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.AccountPermission;
import com.ucarinc.orgmanagement.entity.vo.PageResult;

import java.util.List;

public interface IAccountPermissionService {
    /**
     * @author dong.chen
     * @description 根据查询条件返回账号功能权限列表
     * @date 2019/8/9 16:56
     * @param accountPermission 查询表单
     * @param pageNum 当前页码
     * @param pageSize 每页总数
     * @return 当前页数集合
     */
     PageResult getAccountPermissionList(AccountPermission accountPermission, Integer pageNum, Integer pageSize);

    /**
     * @author dong.chen
     * @date 2019/8/10 16:56
     * @param accountPermission 查询表单
     * @return 账号功能权限集合
     */
    List<AccountPermission> accountPermissionList(AccountPermission accountPermission);
}
