package org.woodwhales.validation.demo1;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.woodwhales.validation.demo1.controller.Demo1Controller;
import org.woodwhales.validation.demo1.dto.User;
import org.woodwhales.validation.utils.MockPostTools;

import com.alibaba.fastjson.JSON;

@RunWith(SpringRunner.class)
@WebMvcTest(Demo1Controller.class)
public class PasswordEqualTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
    public void demo1() throws Exception {
		
		Map<String, Object> parseObject1 = JSON.parseObject(MockPostTools.postTest(mockMvc, "/demo1/", new User("123", "123")), HashMap.class);
		assertEquals(true, parseObject1.get("code"));
		
		Map<String, Object> parseObject2 = JSON.parseObject(MockPostTools.postTest(mockMvc, "/demo1/", new User("1233", "123")), HashMap.class);
		assertEquals(false, parseObject2.get("code"));
		
	}
	
}
