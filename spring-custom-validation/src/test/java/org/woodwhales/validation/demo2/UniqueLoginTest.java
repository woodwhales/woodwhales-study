package org.woodwhales.validation.demo2;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.woodwhales.validation.demo2.controller.Demo2Controller;
import org.woodwhales.validation.demo2.dto.User;
import org.woodwhales.validation.demo2.repository.UserRepository;
import org.woodwhales.validation.utils.MockPostTools;

import com.alibaba.fastjson.JSON;

@RunWith(SpringRunner.class)
@WebMvcTest(Demo2Controller.class)
public class UniqueLoginTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void demo2() throws Exception {
		when(userRepository.findByLogin("woodwhales")).thenReturn(Optional.of(new User("woodwhales", "admin")));

		Map<String, Object> parseObject1 = JSON.parseObject(
				MockPostTools.postTest(mockMvc, "/demo2/", new User("woodwhales", "admin")), HashMap.class);
		// 当前用户账号已存在，不可以注册
		assertEquals(false, parseObject1.get("code"));

		// 当前用户账号不存在，可以注册
		Map<String, Object> parseObject2 = JSON
				.parseObject(MockPostTools.postTest(mockMvc, "/demo2/", new User("木鲸鱼", "admin")), HashMap.class);
		assertEquals(true, parseObject2.get("code"));

	}

}
