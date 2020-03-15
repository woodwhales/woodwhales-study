package org.woodwhales.validation.demo3;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.woodwhales.validation.demo3.controller.Demo3Controller;
import org.woodwhales.validation.demo3.dto.User;
import org.woodwhales.validation.utils.MockPostTools;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(Demo3Controller.class)
public class EnumValidatorTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void demo3() throws Exception {
		
		Map<String, Object> parseObject1 = JSON.parseObject(MockPostTools.postTest(mockMvc, "/demo3/", new User(1)), HashMap.class);
		assertEquals(true, parseObject1.get("code"));

		Map<String, Object> parseObject2 = JSON.parseObject(MockPostTools.postTest(mockMvc, "/demo3/", new User(3)), HashMap.class);
		assertEquals(false, parseObject2.get("code"));
	}

}
