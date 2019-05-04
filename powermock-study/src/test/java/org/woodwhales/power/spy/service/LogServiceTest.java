package org.woodwhales.power.spy.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * spy 的作用是，当符合模拟的行为，那么就会走模拟的行为
 * 	当不符合模拟的行为，那么就会走真正的行为
 * 
 * @author Administrator
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LogService.class})
public class LogServiceTest {
	
	/**
	 * 	测试私有的方法
	 * @throws Exception 
	 */
	@Test
	public void testCheckExist() throws Exception {
		LogService logService = PowerMockito.spy(new LogService());
		// checkExist 是私有方法名，之后跟的是方法的参数
		PowerMockito.doReturn(true).when(logService, "checkExist", "woodwhales");
		
		boolean result = logService.exist("woodwhales");
		assertTrue(result);
	}
	
	@Test
	public void testFooWithSpy() {
		LogService logService = PowerMockito.spy(new LogService());
		
		String arg = "test";
		PowerMockito.doNothing().when(logService).foo(arg);
		
		logService.foo("test"); // 当参数和模拟的参数一致时走模拟
		logService.foo("other test"); // 当参数和模拟的参数不一致时走真实
	}

	/**
	 * 	这种单元测试没有真实调用foo()方法，控制台没有打印任何日志
	 */
	@Test
	public void testFoo() {
		LogService logService = PowerMockito.mock(LogService.class);
		logService.foo("woodwhales");
	}
	
}
