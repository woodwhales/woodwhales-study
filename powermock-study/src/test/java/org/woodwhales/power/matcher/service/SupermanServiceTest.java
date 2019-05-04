package org.woodwhales.power.matcher.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.woodwhales.power.matcher.dao.SupermanDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SupermanService.class})
public class SupermanServiceTest {
	
	@Test
	public void testFindWithAnswer() throws Exception {
		SupermanDao supermanDao = PowerMockito.mock(SupermanDao.class);
		PowerMockito.whenNew(SupermanDao.class).withAnyArguments().thenReturn(supermanDao);

		PowerMockito.when(supermanDao.queryByName(Mockito.anyString()))
		.then(invocation -> {
			String arg = (String) invocation.getArguments()[0];
			switch (arg) {
				case "tom":
					return "hi tom";	
				case "woodwhales":
					return "hi woodwhales";	
				case "king":
					return "hello king";	
				case "superman":
					return "ok superman";	
				default:
					throw new RuntimeException("not support" + arg);	
			}
		});
		
		SupermanService supermanService = new SupermanService();
		
		assertEquals("hi tom", supermanService.find("tom"));
		assertEquals("hello king", supermanService.find("king"));

		try {
			supermanService.find("anyValues");
			fail("never process to here");
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
	}
	
	@Test
	public void testFindWithMatcher() throws Exception {
		SupermanDao supermanDao = PowerMockito.mock(SupermanDao.class);
		PowerMockito.whenNew(SupermanDao.class).withAnyArguments().thenReturn(supermanDao);

		PowerMockito.when(supermanDao.queryByName(Matchers.argThat(new MyArgumentMatcher())))
		.thenReturn("hi");
		
		SupermanService supermanService = new SupermanService();
		assertEquals("hi", supermanService.find("tom"));
		assertEquals("hi", supermanService.find("woodwhales"));
		assertEquals("hi", supermanService.find("king"));
		assertEquals("hi", supermanService.find("superman"));
	}
	

	/**
	 * 	要测试的supermanDao 接口返回很多测试的值
	 * @throws Exception
	 */
	@Test
	public void testFind() throws Exception {
		SupermanDao supermanDao = PowerMockito.mock(SupermanDao.class);
		PowerMockito.whenNew(SupermanDao.class).withAnyArguments().thenReturn(supermanDao);

		SupermanService supermanService = new SupermanService();
		
		PowerMockito.when(supermanDao.queryByName("woodwhales")).thenReturn("hello");
		String woodwhales = supermanService.find("woodwhales");
		assertEquals("hello", woodwhales);
		
		PowerMockito.when(supermanDao.queryByName("tom")).thenReturn("hello");
		String tom = supermanService.find("tom");
		assertEquals("hello", tom);
		
		PowerMockito.when(supermanDao.queryByName("rose")).thenReturn("hello");
		String rose = supermanService.find("rose");
		assertEquals("hello", rose);
	}
	
	static class MyArgumentMatcher extends ArgumentMatcher<String> {

		@Override
		public boolean matches(Object argument) {
			String arg = (String)argument;
			switch (arg) {
				case "tom":
				case "woodwhales":
				case "king":
				case "superman":
					return true;	
			default:
				return false;
			}
			
		}
		
	}
}
