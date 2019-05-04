package org.woodwhales.king.mapper;

import java.security.NoSuchAlgorithmException;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.woodwhales.king.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        User user = userMapper.findUserByUsername("admin");
        log.debug("{}", user);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println(signPassword("tom123"));

    }

    private static String signPassword(String password) {
        SimpleHash hash = new SimpleHash("MD5", password, null, 1024);
        return hash.toString();
    }



}
