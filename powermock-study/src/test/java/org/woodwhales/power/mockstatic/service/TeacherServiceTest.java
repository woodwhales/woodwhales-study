package org.woodwhales.power.mockstatic.service;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.woodwhales.power.mockstatic.dao.TeacherDao;
import org.woodwhales.power.mockstatic.entity.Teacher;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TeacherDao.class})
public class TeacherServiceTest {
	
	@Test
	public void testQueryTeacherCount() {
		mockStatic(TeacherDao.class);
		when(TeacherDao.getCount()).thenReturn(10);
		
		TeacherService teacherService = new TeacherService();
		int result = teacherService.queryTeacherCount();
		assertEquals(10, result);
	}
	
	@Test
	public void testSaveTeacher() {
		Teacher teacher = new Teacher();
		
		mockStatic(TeacherDao.class);
		doNothing().when(TeacherDao.class);
		
		TeacherService teacherService = new TeacherService();
		teacherService.saveTeacher(teacher);
		
		PowerMockito.verifyStatic();
	}
}
