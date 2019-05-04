package org.woodwhales.power.local.service;

import org.woodwhales.power.local.dao.StudentDao;
import org.woodwhales.power.local.entity.Student;

public class StudentService {
	
	public int queryStudentCount() {
		StudentDao studentDao = new StudentDao();
		return studentDao.getCount();
	}
	
	public void saveStudent(Student student) {
		StudentDao studentDao = new StudentDao();
		studentDao.insertStudent(student);
	}
	
}
