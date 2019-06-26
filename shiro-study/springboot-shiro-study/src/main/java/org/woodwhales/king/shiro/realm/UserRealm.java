package org.woodwhales.king.shiro.realm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.woodwhales.king.entity.User;
import org.woodwhales.king.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	private CredentialsMatcher credentialsMatcher = getCredentialsMatcher();

	/**
	 *	授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.debug("授权逻辑");
		log.debug("principals = {}", principals);

		// 这里的 getPrincipal() 就是认证之后在 doGetAuthenticationInfo() 方法返回回来的 principal
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		return createSimpleAuthorizationInfo(user);
	}
	
	private SimpleAuthorizationInfo createSimpleAuthorizationInfo(User user) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(getRoleSet(user));
		info.addStringPermissions(getRoles(user));
		return info;
	}
	
	private List<String> getRoles(User user) {
		return Arrays.asList(StringUtils.split(user.getRoles(), ","));
	}
	
	private Set<String> getRoleSet(User user) {
		log.debug("roles of user : {}" , Arrays.asList(StringUtils.split(user.getRoles(), ",")).toString());
		
		List<String> roles = Arrays.asList(StringUtils.split(user.getRoles(), ","));
		
		HashSet<String> roleSet = new HashSet<>();
		roles.stream().forEach(role->{
			roleSet.add(role);
		});
		
		return roleSet;
	}

	/**
	 *	 认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		log.debug("认证逻辑");

		UsernamePasswordToken userToken = (UsernamePasswordToken)token;
		String username = userToken.getUsername();

		log.debug("从数据库读取用户信息");
		User user = userService.getUserByUsername(username);

		if(user == null) {
			log.warn("can not found user by username = [{}]", username);
			return null;
		}

		/**
		 * SimpleAuthenticationInfo(Object principal, Object credentials, String realmName)
		 * principal 不允许为空！ 可以传递当前已认证的对象，方便后期的授权方法进行授权操作
		 *
		 */
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

	public CredentialsMatcher getCredentialsMatcher() {
		// 设置加密算法
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("MD5");

		// 设置加密次数
		matcher.setHashIterations(1024);
		return matcher;
	}

}
