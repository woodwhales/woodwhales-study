package org.woodwhales.king.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.woodwhales.king.shiro.realm.UserRealm;

@Configuration
public class ShiroConfig {

	/**
	 * 配置3: 将自定义的 securityManager 注入 ShiroFilterFactoryBean
	 * 		  配置自定义的过滤条件
	 * @param defaultWebSecurityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		// 设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

		/**
		 * 	添加shiro 内置过滤器
		 * 	anon：无需认证（登录）就可以访问
		 * 	authc: 必须是已认证的才可以访问
		 *  user：如果使用了 rememberMe 的功能，可以直接访问
		 *  perms：该资源必须得到资源权限才可以访问
		 *  roles: 该资源必须得到角色权限才可以访问
		 */
		Map<String, String> filterMap = new LinkedHashMap<String, String>();
		/*
			设置： /user/ 路径下的所有资源都需要认证用户才能访问
			filterMap.put("/user/add", "authc");
			filterMap.put("/user/update", "authc");
		*/

		filterMap.put("/user/*", "authc");

		// 设置权限访问,注意权限控制越精细的路径过滤配置一定要放到最前面
		filterMap.put("/user/add", "roles[user:add]");
		filterMap.put("/user/update", "roles[user:update]");

		// 设置主页和登录请求路径可以任何用户访问
		filterMap.put("/login", "anon");
		filterMap.put("/logout", "logout");

        filterMap.put("/", "anon");
        // 其他的访问全部需要认证才能访问
		filterMap.put("/**", "user");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		
		// 自定义跳转的登录url
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		// 自定义未授权跳转的url
		shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
		
		return shiroFilterFactoryBean;
	}

	/**
	 *	 配置2：创建 DefaultWebSecurityManager
	 * @param userRealm
	 * @return
	 */
	@Bean("securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm,
																  @Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		
		// 关联 AuthorizingRealm
		securityManager.setRealm(userRealm);
		securityManager.setRememberMeManager(cookieRememberMeManager);

		return securityManager;
	}

	/**
	 * 配置 cookieRememberMeManager
	 * @param simpleCookie
	 * @return
	 */
	@Bean("cookieRememberMeManager")
	public CookieRememberMeManager rememberMeManager(@Qualifier("simpleCookie") SimpleCookie simpleCookie){
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(simpleCookie);
		return cookieRememberMeManager;
	}

	/**
	 * 配置 simpleCookie
	 * @return
	 */
	@Bean("simpleCookie")
	public SimpleCookie rememberMeCookie(){
		//这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		//<!-- 记住我cookie生效时间,单位秒;-->
		simpleCookie.setMaxAge(20);
		return simpleCookie;
	}

	/**
	 * 	配置1：创建 AuthorizingRealm
	 * @return
	 */
	@Bean("userRealm")
	public UserRealm getUserRealm() {
		return new UserRealm();
	}

	/**
	 * 配置4: 用于thymeleaf 和shiro 的整合
	 * @return
	 */
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
}
