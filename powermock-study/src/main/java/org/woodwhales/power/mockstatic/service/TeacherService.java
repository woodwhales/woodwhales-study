package org.woodwhales.power.mockstatic.service;

import org.woodwhales.power.mockstatic.dao.TeacherDao;
import org.woodwhales.power.mockstatic.entity.Teacher;

public class TeacherService {

	public int queryTeacherCount() {
		return TeacherDao.getCount();
	}
	
	public void saveTeacher(Teacher teacher) {
		TeacherDao.insertTeacher(teacher);
	}
}
