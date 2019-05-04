package org.woodwhales.king.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.woodwhales.king.model.Permission;
import org.woodwhales.king.model.Role;
import org.woodwhales.king.model.User;
import org.woodwhales.king.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class MyAuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User)principals.fromRealm(this.getClass().getName()).iterator().next();

        List<String> permissionList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        Set<Role> roleSet = user.getRoles();
        if(CollectionUtils.isNotEmpty(roleSet)) {
            roleSet.stream().forEach(role -> {
                // 角色集合
                roleList.add(role.getName());

                //　权限集合
                Set<Permission> permissionSet = role.getPermissions();
                if(CollectionUtils.isNotEmpty(permissionSet)) {
                    permissionSet.stream().forEach(permission -> {
                        permissionList.add(permission.getRname());
                    });
                }
            });
        }

        log.debug("permissionList : {}", permissionList.toString());
        log.debug("roleList : {}", roleList.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 添加权限
        info.addStringPermissions(permissionList);
        // 添加角色
        info.addRoles(roleList);
        return info;
    }

    /**
     * 认证登录
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        String username = usernamePasswordToken.getUsername();
        User user = userService.findByUsername(username);
        if(Objects.isNull(user)) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }
}
