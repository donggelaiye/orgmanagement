package com.ucarinc.orgmanagement.service;

import com.ucarinc.orgmanagement.entity.Account;

import java.util.List;

/**
 * description:数据权限service接口
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-17 9:21
 */
public interface IDataPermissionService {

    /**
     * description:获取当前登录账号数据权限(部门)
     *
     * @return java.util.List<java.lang.String>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 9:10
     */
    List<String> getActiveAccountDataPermissionDeptIdList();

    /**
     * description:获取指定Account实体数据权限(部门)
     * @param  account Account实体
     * @return java.util.List<java.lang.String>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/17 9:10
     */
    List<String> getAccountDataPermissionDeptIdList(Account account);
}
