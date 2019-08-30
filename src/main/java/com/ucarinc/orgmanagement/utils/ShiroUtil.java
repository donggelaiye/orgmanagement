package com.ucarinc.orgmanagement.utils;

import com.ucarinc.orgmanagement.entity.Account;
import org.apache.shiro.SecurityUtils;

/**
 * description:shiro工具类
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-06 10:57
 */
public class ShiroUtil {

    /**
     * description:获取当前登录用户Account实体信息
     *
     * @return com.ucarinc.orgmanagement.entity.Account
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/6 16:31
     */
    public static Account getActiveAccountInfo() {
        Account activeAccount = (Account) SecurityUtils.getSubject().getPrincipal();
        return activeAccount;
    }

    /**
     * description:获取当前登录账号关联员工Id
     *
     * @return java.lang.Long
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/6 16:35
     */
    public static Long getActiveEmpId() {
        Account activeAccount = (Account) SecurityUtils.getSubject().getPrincipal();
        return ComUtil.getEmpIdByAccount(activeAccount.getAccountLogin());
    }

    /**
     * description:获取当前登录账号关联员工编号
     *
     * @return java.lang.String
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/6 16:35
     */
    public static String getActiveEmpCode() {
        Account activeAccount = (Account) SecurityUtils.getSubject().getPrincipal();
        return activeAccount.getEmpId();
    }

    /**
     * description:获取当前登录账号
     *
     * @return java.lang.String
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/6 16:35
     */
    public static String getActiceLoginAccount() {
        Account activeAccount = (Account) SecurityUtils.getSubject().getPrincipal();
        return activeAccount.getAccountLogin();
    }

    /**
     * description:获取当前登录账号数据权限Id
     *
     * @return java.lang.Long
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/6 16:35
     */
    public static Integer getActiveAccountDataPermissionId() {
        Account activeAccount = (Account) SecurityUtils.getSubject().getPrincipal();
        return activeAccount.getDataPermissionId();
    }
}
