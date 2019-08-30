package com.ucarinc.orgmanagement.security.shiro;

import com.ucarinc.orgmanagement.security.shiro.filter.AccountLogoutFilter;
import com.ucarinc.orgmanagement.security.shiro.filter.AccountUserFilter;
import com.ucarinc.orgmanagement.security.shiro.realm.AccountRealm;
import com.ucarinc.orgmanagement.security.shiro.sessiondao.EhcacheSessionDao;
import com.ucarinc.orgmanagement.security.shiro.sessionmanager.CrossSessionManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * describe:shiro配置类
 *
 * @author dihan.chen
 * @date 2019/07/26
 */
@Configuration
public class ShiroConfig {

    /**
     * description:shiroFactory配置
     *
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/2 15:19
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /*
         * 添加shiro内置过滤器
         *   常用过滤器:
         *   anon : 无需认证,可直接访问
         *   authc : 需认证后才可访问
         *   user : 如使用rememberMe功能可直接访问
         *   perms : 需拥有资源权限才可访问
         *   role : 资源必须得到角色权限才可访问
         * */
        Map<String, String> filterMap = new LinkedHashMap<>();
        //放行登录页请求
        filterMap.put("/img/**", "anon");
        filterMap.put("/**/management/resetPwd", "anon");
        filterMap.put("/**/login", "anon");
        filterMap.put("/security/login", "anon");
        filterMap.put("/security/unauthc", "anon");
        filterMap.put("/security/doneLogout", "anon");
        filterMap.put("/**/logout", "logout");
        filterMap.put("/**", "authc,perms");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //未登录走接口
        shiroFilterFactoryBean.setLoginUrl("/security/unauthc");
        //自定义authc,放行OPTION预请求
        AccountUserFilter userFilter = new AccountUserFilter();
        //配置自定义登出过滤器跳转地址
        AccountLogoutFilter accountLogoutFilter = new AccountLogoutFilter();
        accountLogoutFilter.setRedirectUrl("/security/doneLogout");
        Map<String, Filter> designFilter = new HashMap<>();
        //配置自定义登出过滤器
        designFilter.put("logout", accountLogoutFilter);
        designFilter.put("authc",userFilter);
        shiroFilterFactoryBean.setFilters(designFilter);
        return shiroFilterFactoryBean;
    }

    /**
     * description: shiro类安全管理
     * 配置securityManager,注册为bean
     * 依赖Realm
     *
     * @param accountRealm 自定义权限Realm:AccountRealm
     * @return securityManager shiroSecurityManager
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("AccountRealm") AccountRealm accountRealm,
                                                               @Qualifier("crossSessionManager") CrossSessionManager crossSessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //配置realm
        securityManager.setRealm(accountRealm);
        //配置sessionManager
        securityManager.setSessionManager(crossSessionManager);
        return securityManager;
    }

    /**
     * description:注册自定义realm
     *
     * @return UserRealm
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/2 14:48
     */
    @Bean(name = "AccountRealm")
    public AccountRealm getRealm() {
        return new AccountRealm();
    }

    /**
     * description:注册自定义sessionManager
     *
     * @return com.ucarinc.orgmanagement.security.shiro.sessionmanager.CrossSessionManager
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/6 13:54
     */
    @Bean(name = "crossSessionManager")
    public CrossSessionManager getSessionManager(@Qualifier("ehcacheSessionDao") SessionDAO sessionDAO) {
        CrossSessionManager crossSessionManager = new CrossSessionManager();
        crossSessionManager.setSessionDAO(sessionDAO);
        return crossSessionManager;
    }

    /**
     * description:EnterpriseCacheSessionDAO 会话管理器,整合ehcacheManager
     *
     * @return org.apache.shiro.session.mgt.eis.SessionDAO
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/16 15:26
     */
    @Bean(name = "ehcacheSessionDao")
    public SessionDAO getSessionDao(@Qualifier("ehCacheManager") EhCacheManager ehCacheManager) {
        EhcacheSessionDao sessionDAO = new EhcacheSessionDao();
        sessionDAO.setCacheManager(ehCacheManager);
        return sessionDAO;
    }

    /**
     * description:注册ehCacheManager
     *
     * @return org.apache.shiro.cache.ehcache.EhCacheManager
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/2 14:43
     */
    @Bean(name = "ehCacheManager")
    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return em;
    }

    /**
     * description: 开启shiroAOP代理
     *
     * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/2 14:44
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * description:开启ShiroAOP支持
     *
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/2 14:46
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
