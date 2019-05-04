package org.woodwhales.power.mockfinal.service;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.woodwhales.power.mockfinal.dao.DogDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DogDao.class})
public class DogServiceTest {

	@Mock
	private DogDao dogDao;
	
	/**
	 * mockito 不能单元测试final 修饰的类
	 */
	@Ignore
	@Test
	public void testQueryDogCountWithMockito() {
		MockitoAnnotations.initMocks(this);
		
		DogService dogService = new DogService(dogDao);
		Mockito.when(dogDao.getCount()).thenReturn(10);
		
		int result = dogService.queryDogCount();
		assertEquals(10, result);
	}
	
	@Test
	public void testQueryDogCountWithPowerMock() {
		DogDao dogDao = PowerMockito.mock(DogDao.class);
		PowerMockito.when(dogDao.getCount()).thenReturn(10);
		
		DogService dogService = new DogService(dogDao);
		
		int result = dogService.queryDogCount();
		assertEquals(10, result);
		
	}
	
	
}
