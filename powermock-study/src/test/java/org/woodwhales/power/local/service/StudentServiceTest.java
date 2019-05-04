package org.woodwhales.power.local.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.woodwhales.power.local.dao.StudentDao;
import org.woodwhales.power.local.entity.Student;

/**
 * 一定要增加：PowerMockRunner 的扩展和@PrepareForTest，其中可以传Class数组
 * 说明：@PrepareForTest作用就是表示其中的值是mock 的，不是真实的东西
 * 
 * @author Administrator
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StudentService.class})
public class StudentServiceTest {

	/**
	 * 如何将StudentService 的私有属性注入单元测试的结果进行测试？
	 */
	@Test
	public void testQueryStudentCount() {
		try {
			StudentService studentService = new StudentService();

			// StudentService 实用到对象在这里事先mock出来
			// mock 出无参的 StudentDao
			StudentDao studentDao = mock(StudentDao.class);
			whenNew(StudentDao.class).withNoArguments().thenReturn(studentDao);

			doReturn(10).when(studentDao).getCount();
			int result = studentService.queryStudentCount();
			assertEquals(10, result);

		} catch (Throwable e) {
			fail();
		}
	}

	@Test
	public void testSaveStudent() throws Exception {
		try {
			Student student = new Student();
			StudentService studentService = new StudentService();
			
			StudentDao studentDao = mock(StudentDao.class);
			whenNew(StudentDao.class).withAnyArguments().thenReturn(studentDao);
			doNothing().when(studentDao).insertStudent(student);
			
			studentService.saveStudent(student);
			
			// 验证 studentDao 的  insertStudent() 只被调用了一次
			Mockito.verify(studentDao, Mockito.times(1)).insertStudent(student);
		} catch (Throwable e) {
			fail();
		}
	}
}
