package org.woodwhale.poi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhale.poi.pojo.CourseEntity;
import org.woodwhale.poi.pojo.StudentEntity;
import org.woodwhale.poi.pojo.TeacherEntity;
import org.woodwhale.poi.util.PoiUtils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

@RestController
public class Demo7Controller {

	@RequestMapping("/demo7")
	public void demo7(HttpServletRequest request, HttpServletResponse response) {
		List<StudentEntity> StudentEntityList = createStudentEntityData();

		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生", "学生"), StudentEntity.class,
				StudentEntityList);
		try {
			PoiUtils.downloadExcel(workbook, request, response, "测试数据库");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/demo7/one2One")
	public void one2One(HttpServletRequest request, HttpServletResponse response) {
		List<StudentEntity> list = createStudentEntityData();
		
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("花名册", "学生sheet"), StudentEntity.class, list);
		try {
			PoiUtils.downloadExcel(workbook, request, response, "测试数据库");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/demo7/one2N")
	public void one2N(HttpServletRequest request, HttpServletResponse response) {
		List<CourseEntity> list = createCourseEntityData();
		Iterator<CourseEntity> cIterator = list.iterator();
		
		while(cIterator.hasNext()) {
			cIterator.next().setStudents(createStudentEntityData());
		}
		
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("花名册", "学生sheet"), CourseEntity.class, list);
		try {
			PoiUtils.downloadExcel(workbook, request, response, "测试数据库");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<StudentEntity> createStudentEntityData() {
		StudentEntity stu1 = new StudentEntity("1","海贼王",1,new Date(),new Date());
		StudentEntity stu2 = new StudentEntity("2","柯南",1,new Date(),new Date());
		StudentEntity stu3 = new StudentEntity("3","美少女",2,new Date(),new Date());
		List<StudentEntity> list = new ArrayList<>();
		list.add(stu1);
		list.add(stu2);
		list.add(stu3);
		return list;
	}
	
	public List<CourseEntity> createCourseEntityData() {
		CourseEntity cus1 = new CourseEntity("1","必修1",new TeacherEntity("1","苍老师"));
		CourseEntity cus2 = new CourseEntity("2","必修2",new TeacherEntity("1","小泽老师"));
		List<CourseEntity> list = new ArrayList<>();
		list.add(cus1);
		list.add(cus2);
		return list;
	}
	
	public List<TeacherEntity> createTeacherEntityData() {
		TeacherEntity teh1 = new TeacherEntity("1","苍老师");
		TeacherEntity teh2 = new TeacherEntity("2","小泽老师");
		List<TeacherEntity> list = new ArrayList<>();
		list.add(teh1);
		list.add(teh2);
		return list;
	}

}
