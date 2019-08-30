package com.ucarinc.orgmanagement.service.impl;

import com.ucarinc.orgmanagement.common.ResultEnum;
import com.ucarinc.orgmanagement.dao.AccountResourceMapper;
import com.ucarinc.orgmanagement.dao.LoginHistoryMapper;
import com.ucarinc.orgmanagement.dao.ResourceMapper;
import com.ucarinc.orgmanagement.entity.Account;
import com.ucarinc.orgmanagement.entity.LoginHistory;
import com.ucarinc.orgmanagement.entity.Resource;
import com.ucarinc.orgmanagement.entity.exception.GlobalException;
import com.ucarinc.orgmanagement.entity.vo.Result;
import com.ucarinc.orgmanagement.service.ISecurityService;
import com.ucarinc.orgmanagement.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:安全相关业务实现类
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-07 10:48
 */
@Service
@Component
public class SecurityServiceImpl implements ISecurityService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private AccountResourceMapper accountResourceMapper;
    @Autowired
    private LoginHistoryMapper loginHistoryMapper;
    @Autowired
    @Qualifier("ehcacheSessionDao")
    private SessionDAO sessionDAO;

    @Override
    public Result login(Map<String, Object> requestBody) {
        String userName = requestBody.get("userName").toString();
        String password = requestBody.get("password").toString();
        //获取shiro subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户信息
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        //执行登录方法
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            //用户名不存在;
            System.out.println("用户名不存在");
            return ResultUtil.error(ResultEnum.ERROR.getCode(), "用户不存在!");
        } catch (IncorrectCredentialsException e) {
            //密码错误
            System.out.println("密码错误");
            return ResultUtil.error(ResultEnum.ERROR.getCode(), "密码错误!");
        } catch (DisabledAccountException e) {
            return ResultUtil.error(ResultEnum.FORBIDDEN.getCode(), e.getMessage());
        }
        //成功登录,获取sessionId
        Serializable sessionId = subject.getSession().getId();
        //loginAccount写入session
        subject.getSession().setAttribute("loginAccount", userName);
        //登陆成功,判定是否首次登录,查询登录历史
        LoginHistory loginHistory;
        try {
            loginHistory = loginHistoryMapper.selectByLoginAccount(userName);
        } catch (Exception e) {
            return ResultUtil.error(500, "系统内部错误");
        }
        if (loginHistory == null) {
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("SUID", sessionId);
            infoMap.put("ACCOUNTLOGIN", userName);
            return ResultUtil.success(ResultEnum.ACCEPT.getCode(), "首次登录,请重置密码!", infoMap);
        }
        //session过期时间
        Map<String, Object> infoMap = getActiveAccountMenuPermissionInfo(subject);
        infoMap.put("SUID", sessionId);
        infoMap.put("ACCOUNTLOGIN", userName);
        return ResultUtil.success(infoMap);
    }

    @Override
    public Result isSessionAlive(String sessionId) {
        DefaultSessionKey sessionKey = new DefaultSessionKey(sessionId);
        Session session = SecurityUtils.getSecurityManager().getSession(sessionKey);
        Map<String, Object> resultMap = new HashMap<>();
        if (session == null) {
            resultMap.put("isSessionAlive", "false");
            return ResultUtil.success(ResultEnum.SUCCESS.getCode(), null, resultMap);
        } else {
            resultMap.put("isSessionAlive", "true");
            resultMap.put("loginAccount", session.getAttribute("loginAccount"));
            return ResultUtil.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), resultMap);
        }
    }

    @Override
    public Result killSessionBySessionId(String sessionId) {
        DefaultSessionKey sessionKey = new DefaultSessionKey(sessionId);
        Session session = SecurityUtils.getSecurityManager().getSession(sessionKey);
        session.stop();
        return ResultUtil.success();
    }

    @Override
    public Map<String, Object> getActiveAccountMenuPermissionInfo(Subject currentSubject) {
        //用户未登录,必须通过转型取loginAccount
        Account loginUser = (Account) currentSubject.getPrincipal();
        //账号关联角色的权限
        List<Resource> roleResource = resourceMapper.selectMenuCodeAndPermissionCodeByLoginAccount(loginUser.getAccountLogin());
        //账号特殊权限
        List<Resource> accountResource = accountResourceMapper.selectAccountResourceByLoginAccount(loginUser.getAccountLogin());
        if (roleResource.isEmpty() && accountResource.isEmpty()) {
            //无任何权限,拒绝登录
            throw new GlobalException(ResultEnum.FORBIDDEN.getCode(), "该账号无任何权限,请与管理员联系!");
        }
        return sortPermissionInfoMap(roleResource, accountResource);
    }

    /**
     * description:汇总生成账号关联资源列表,菜单列表,父级菜单列表信息
     *
     * @param roleResourceList    角色拥有资源列表,
     * @param accountResourceList 账号特殊资源列表
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/13 14:59
     */
    private Map<String, Object> sortPermissionInfoMap(List<Resource> roleResourceList, List<Resource> accountResourceList) {
        //父级菜单IdList
        List<Long> parentMenuIdList = new ArrayList<>();
        //菜单编号List
        List<String> menuList = new ArrayList<>();
        //权限编号List
        List<String> permissionList = new ArrayList<>();
        //账号角色权限处理
        if (!roleResourceList.isEmpty()) {
            for (Resource resource : roleResourceList) {
                if (!parentMenuIdList.contains(resource.getParentId())) {
                    parentMenuIdList.add(resource.getParentId());
                }
                if (!menuList.contains(resource.getMenuCode())) {
                    menuList.add(resource.getMenuCode());
                }
                permissionList.add(resource.getPermissionCode());
            }
        }
        //账号特殊权限处理
        if (!accountResourceList.isEmpty()) {
            for (Resource resource : accountResourceList) {
                if (!menuList.contains(resource.getMenuCode())) {
                    menuList.add(resource.getMenuCode());
                }
                if (!parentMenuIdList.contains(resource.getParentId())) {
                    parentMenuIdList.add(resource.getParentId());
                }
                permissionList.add(resource.getPermissionCode());
            }
        }
        List<Resource> parentMenu = resourceMapper.selectResourceByResourceIdList(parentMenuIdList);
        List<String> parentMenuList = new ArrayList<>();
        for (Resource menu : parentMenu) {
            parentMenuList.add(menu.getMenuCode());
        }
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("parentMenuList", parentMenuList);
        infoMap.put("menuList", menuList);
        infoMap.put("permissionList", permissionList);
        return infoMap;
    }
}
