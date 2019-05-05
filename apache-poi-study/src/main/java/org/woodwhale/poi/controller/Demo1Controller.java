package org.woodwhale.poi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhale.poi.util.PoiUtils;

@RestController
public class Demo1Controller {

	@RequestMapping("demo1/newBook")
	public void newBook(HttpServletRequest request, HttpServletResponse response) {
		
		// 定义一个新的工作簿对象
		Workbook workbook = new HSSFWorkbook();
		
		try {
			String fileName = "demo1-1-新建工作簿";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("demo1/newSheet")
	public void newSheet(HttpServletRequest request, HttpServletResponse response) {

		// 定义一个新的工作簿对象
		Workbook workbook = new HSSFWorkbook(); 
		
		// 创建 sheet
		workbook.createSheet("Sheet1"); 
		workbook.createSheet("Sheet2");
		
		try {
			String fileName = "demo1-2-新建sheet";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("demo1/newCell")
	public void newCell(HttpServletRequest request, HttpServletResponse response) {
		
		Workbook workbook = new HSSFWorkbook(); 
		Sheet sheet1 = workbook.createSheet("Sheet1"); 
		
		// 在上面创建的 sheet 中创建第一行
		Row row0 = sheet1.createRow(0);
		// 创建第一行的第一列
		Cell cell0 = row0.createCell(0);
		cell0.setCellValue(1);
		
		// 创建第二列并设置浮点数
		row0.createCell(1).setCellValue(1.2);
		// 设置单元格内容可以是字符串
		row0.createCell(2).setCellValue("这是一个字符串类型"); 
		// 设置单元格内容可以是布尔值
		row0.createCell(3).setCellValue(false); 
		
		try {
			String fileName = "demo1-3-新建单元格";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
