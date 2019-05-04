package org.woodwhales.power.construction.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.woodwhales.power.construction.dao.FishDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FishService.class, FishDao.class})
public class FishServiceTest {

	@Test
	public void testSaveFish() throws Exception {
		FishDao fishDao = PowerMockito.mock(FishDao.class);
		String username = "woodwhales";
		String password = "king";
		PowerMockito.whenNew(FishDao.class).withArguments(username, password).thenReturn(fishDao);
		
		PowerMockito.doNothing().when(fishDao).insert();
		
		FishService fishService = new FishService();
		// 注意： 这里的 saveFish(username, password)必须和 模拟出的 全参fishDao中的参数值保持一致
		fishService.saveFish(username, password);
		
		Mockito.verify(fishDao).insert();
	}
}
