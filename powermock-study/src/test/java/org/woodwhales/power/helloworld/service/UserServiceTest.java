package org.woodwhales.power.helloworld.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.woodwhales.power.helloworld.dao.UserDao;
import org.woodwhales.power.helloworld.entity.User;

public class UserServiceTest {
	
	private UserService userService;
	
	@Before
	public void setup() {
		userService = new UserService(new UserDao());
	}
	
	/**
	 * 	PowerMockito 单元测试，有参数返回
	 * 	这种单元测试所有的测试元素都在这个方法里面，和外界的任何东西无关
	 * 
	 */
	@Test
	public void testQueryUserCountWithPowerMock() {
		UserDao uDao = PowerMockito.mock(UserDao.class);
		
		PowerMockito.doReturn(10).when(uDao).getCount();
		// 第二种预制方法
//		PowerMockito.when(uDao.getCount()).thenReturn(10);
		UserService uService = new UserService(uDao);
		int result = uService.queryUserCount();
		assertEquals(10, result);
	}
	
	/**
	 * 	PowerMockito 单元测试，无参数返回
	 * 	注意：PowerMockito.doNothing() 只能用于void 修饰的方法
	 */
	@Test
	public void testSaveUserCountWithPowerMock() {
		UserDao uDao = PowerMockito.mock(UserDao.class);

		User user = new User();
		PowerMockito.doNothing().when(uDao).insertUser(user);
		UserService uService = new UserService(uDao);
		
		uService.saveUser(user);
		
		// 怎么确定 UserDao 的 insertUser() 一定被调用了呢
		// 使用 Mockito.verify() 断言一定被调用了
		Mockito.verify(uDao, Mockito.times(1)).insertUser(user);
	}
	
	@Mock
	private UserDao userDao;
	
	/**
	 * 	Mockito 单元测试
	 */
	@Test
	public void testQueryUserCountWithMockito() {
		MockitoAnnotations.initMocks(this);
		
		userService = new UserService(userDao);
		
		// 预制 userDao 的getCount()行为的结果
		Mockito.when(userDao.getCount()).thenReturn(10);
		
		int result = userService.queryUserCount();
		assertEquals(10, result);
	}

	/**
	 * 	传统的junit单元测试
	 */
	@Test
	public void testQueryUserCount() {
		try {
			userService.queryUserCount();
			fail("should mot process to here");
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	/**
	 * 	传统的junit单元测试
	 */
	@Test
	public void testSaveUser() {
		try {
			userService.saveUser(new User());
			fail("should mot process to here");
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
}
