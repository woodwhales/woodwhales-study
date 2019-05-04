package org.woodwhales.king.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义密码校验器
 */
@Slf4j
public class MyCredentialMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        String password = new String(usernamePasswordToken.getPassword());

        String dbPassword = (String)info.getCredentials();
        log.debug("password = [{}], dbPassword=[{}]", password, dbPassword);
        return this.equals(password, dbPassword);
    }
}

