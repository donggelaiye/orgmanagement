package com.ucarinc.orgmanagement.security.shiro.realm;

import com.ucarinc.orgmanagement.common.ResultEnum;
import com.ucarinc.orgmanagement.dao.AccountMapper;
import com.ucarinc.orgmanagement.dao.AccountResourceMapper;
import com.ucarinc.orgmanagement.dao.ResourceMapper;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import com.ucarinc.orgmanagement.utils.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * description:自定义ShiroRealm
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019/07/26 15:04:33
 */
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private AccountResourceMapper accountResourceMapper;

    /**
     * description:realm授权逻辑
     *
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/2 15:41
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Account loginUser = (Account) principalCollection.getPrimaryPrincipal();
        List<String> rolePermissionList = resourceMapper.selectAccountPermissionCodeListByLoginAccount(loginUser.getAccountLogin());
        //获取账号特殊权限
        List<String> accountPermissionList = accountResourceMapper.selectAccountPermissionCodeByLoginAccount(loginUser.getAccountLogin());
        if (!accountPermissionList.isEmpty()) {
            //整合账号权限
            for (String permissionCode : accountPermissionList) {
                if (!rolePermissionList.contains(permissionCode)) {
                    rolePermissionList.add(permissionCode);
                }
            }
        }
        if (!rolePermissionList.isEmpty()) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addStringPermissions(rolePermissionList);
            return authorizationInfo;
        }
        return null;
    }

    /**
     * description:realm登录逻辑
     *
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/2 15:42
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Account userAccount = accountMapper.selectByLoginAccount(token.getUsername());
        if (userAccount == null) {
            return null;
        }
        //获取数据库账号信息
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userAccount, userAccount.getAccountPassword(), "");
        CredentialsMatcher matcher = this.getCredentialsMatcher();
        if (!matcher.doCredentialsMatch(token, authenticationInfo)) {
            throw new IncorrectCredentialsException("密码错误!");
        }
        if (userAccount.getStatus().equals(Account.ACCOUNT_STATUS_INVALID)) {
            throw new DisabledAccountException("该账号无效,不允许登录!");
        } else if (userAccount.getStatus().equals(Account.ACCOUNT_STATUS_FROZZEN)) {
            throw new DisabledAccountException("该账号已被冻结,不允许登录!");
        }
        if (userAccount.getEmpId() == null) {
            throw new DisabledAccountException("该账号未关联员工,不允许登录!");
        }
        return authenticationInfo;
    }

    /**
     * description:realm清除缓存方法
     *
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/2 15:42
     */

    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
