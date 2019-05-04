package org.woodwhales.power.verify.service;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.woodwhales.power.verify.dao.CatDao;
import org.woodwhales.power.verify.entity.Cat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CatService.class})
public class CatServiceTest {

	@Test
	public void testSaveOrUpdateWillUseNewJoiner() throws Exception {
		Cat cat = mock(Cat.class);
		CatDao catDao = mock(CatDao.class);
		
		whenNew(CatDao.class).withNoArguments().thenReturn(catDao);
		
		// 模拟查询不到数据
		when(catDao.getCount(cat)).thenReturn(0);
		
		CatService catService = new CatService();
		
		catService.saveOrUpdate(cat);
		
		// 如何确定一定是新增了数据，而不是更新了数据呢？
		Mockito.verify(catDao).insertCat(cat);
		Mockito.verify(catDao, Mockito.never()).updateCat(cat);
	}
	
	@Test
	public void testSaveOrUpdateWillUseUpdateOriginal() throws Exception {
		Cat cat = mock(Cat.class);
		CatDao catDao = mock(CatDao.class);
		
		whenNew(CatDao.class).withNoArguments().thenReturn(catDao);
		
		// 模拟查询到了数据
		when(catDao.getCount(cat)).thenReturn(1);
		
		CatService catService = new CatService();
		
		catService.saveOrUpdate(cat);
		
		// 如何确定一定是更新了数据，而不是保存了新数据呢？
		Mockito.verify(catDao, Mockito.never()).insertCat(cat);
		Mockito.verify(catDao).updateCat(cat);
	}
}
