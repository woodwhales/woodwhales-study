package org.woodwhales.validation.utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockPostTools {

	private MockPostTools() {}
	
	public static String postTest(MockMvc mockMvc, String url, Object object) throws Exception {
		String content = JSON.toJSONString(object);
		
		ResultActions resultActions = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
		        .content(content));
		
		String responseString = resultActions.andDo(print())
											.andExpect(status().isOk())
											.andReturn()
									        .getResponse()
									        .getContentAsString();
		log.debug("responseString -> {}", responseString);
		return responseString;
	}
}
