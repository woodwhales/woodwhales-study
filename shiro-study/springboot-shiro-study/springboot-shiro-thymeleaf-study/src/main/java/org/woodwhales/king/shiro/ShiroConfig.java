package org.woodwhales.king.shiro;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    /**
     * 配置4: 核心过滤配置
     *  过滤器的名称为 org.apache.shiro.web.filter.mgt.DefaultFilter 枚举中的属性变量值
     * @param manager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);

        bean.setLoginUrl("/login"); //设置登录url
        bean.setSuccessUrl("/index"); // 设置成功登录url
        bean.setUnauthorizedUrl("/unauthorized"); // 未授权会跳转到的url

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index", "authc");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginUser", "anon");
        filterChainDefinitionMap.put("/admin", "roles[admin]");
        filterChainDefinitionMap.put("/druid/**", "anon"); // 设置 druid 任意访问，不需要拦截
        filterChainDefinitionMap.put("/edit", "perms[edit]");

        /**
         * org.apache.shiro.web.filter.authc.UserFilter
         * isAccessAllowed()方法中，只进行了 isLoginRequest(request, response) 判断是否登录，
         * 如果登录过则可以放行
         *
         */
        filterChainDefinitionMap.put("/**", "user"); // user 所有的请求必须是已登录的用户才可以访问

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /**
     * 配置3: 将自定义的Realm 设置到 DefaultWebSecurityManager
     * @param myAuthRealm
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("myAuthRealm") MyAuthRealm myAuthRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myAuthRealm);
        return manager;
    }

    /**
     * 配置2:自定义的Realm
     * @param matcher
     * @return
     */
    @Bean("myAuthRealm")
    public MyAuthRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        MyAuthRealm authRealm = new MyAuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    /**
     * 配置1:自定义的密码比较器
     * @return
     */
    @Bean("credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new MyCredentialMatcher();
    }


    /**
     * 配置5: securityManager 与 spring 进行关联
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 配置6: 开启代理
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setExposeProxy(true);
        return creator;
    }

}
