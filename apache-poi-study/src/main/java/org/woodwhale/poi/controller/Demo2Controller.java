package org.woodwhale.poi.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhale.poi.util.PoiUtils;

@RestController
public class Demo2Controller {

	@RequestMapping("/demo2/newDateCell")
	public void newDateCell(HttpServletRequest request, HttpServletResponse response) {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");
		Row row0 = sheet1.createRow(0);

		// 设置第一行的第1列单元格
		Cell cell0 = row0.createCell(0);
		cell0.setCellValue(new Date());

		// 设置第一行的第2列单元格
		Cell cell1 = row0.createCell(1);
		cell1.setCellValue(new Date());

		// 创建日期格式样式
		CreationHelper creationHelper = workbook.getCreationHelper();
		short format = creationHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss");

		// 创建样式并设置日期格式样式
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format);

		// 设置单元格式
		cell1.setCellStyle(cellStyle);

		// 设置第一行的第3列单元格
		Cell cell2 = row0.createCell(2);
		cell2.setCellValue(Calendar.getInstance());
		cell2.setCellStyle(cellStyle);

		try {
			String fileName = "demo2-1-带日期格式的单元格";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo2/newStylesCell")
	public void newStylesCell(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");
		Row row0 = sheet1.createRow(0);

		row0.createCell(0).setCellValue(new Date()); // 给单元格设置值
		row0.createCell(1).setCellValue(1);
		row0.createCell(2).setCellValue("一个字符串");
		row0.createCell(3).setCellValue(true);
		row0.createCell(4).setCellValue(false);
		row0.createCell(5).setCellType(CellType.ERROR);
		row0.createCell(6).setCellType(CellType.NUMERIC);
		row0.createCell(6).setCellValue("");
		row0.createCell(7).setCellType(CellType.BLANK);

		// 获取单元格内容类型
		CellType cellTypeEnum6 = row0.getCell(6).getCellTypeEnum();
		String name6 = cellTypeEnum6.name();
		System.out.println(name6); // STRING

		// 获取单元格内容类型的时候是name
		CellType cellTypeEnum7 = row0.getCell(7).getCellTypeEnum();
		String name7 = cellTypeEnum7.name();
		System.out.println(name7); // BLANK

		Row row = sheet1.createRow(1);

		// 设置日期格式--使用Excel内嵌的格式
		Cell cell = row.createCell(0);
		cell.setCellValue(new Date());
		CellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		cell.setCellStyle(style);

		// 设置保留2位小数--使用Excel内嵌的格式
		cell = row.createCell(1);
		cell.setCellValue(12.3456789);
		style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		cell.setCellStyle(style);

		// 设置货币格式--使用自定义的格式
		cell = row.createCell(2);
		cell.setCellValue(12345.6789);
		style = workbook.createCellStyle();
		style.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0"));
		cell.setCellStyle(style);

		// 设置百分比格式--使用自定义的格式
		cell = row.createCell(3);
		cell.setCellValue(0.123456789);
		style = workbook.createCellStyle();
		style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
		cell.setCellStyle(style);

		// 设置数字为中文大写格式--使用自定义的格式
		cell = row.createCell(4);
		cell.setCellValue(12345);
		style = workbook.createCellStyle();
		style.setDataFormat(workbook.createDataFormat().getFormat("[DbNum2][$-804]0"));
		cell.setCellStyle(style);

		// 设置科学计数法格式--使用自定义的格式
		cell = row.createCell(5);
		cell.setCellValue(12345);
		style = workbook.createCellStyle();
		style.setDataFormat(workbook.createDataFormat().getFormat("0.00E+00"));
		cell.setCellStyle(style);

		// 设置列自动根据内容伸展
		// int physicalNumberOfRows = sheet1.getPhysicalNumberOfRows(); // 逻辑上存在多少行
		int lastRowNum = sheet1.getLastRowNum();
		Row lastRow = sheet1.getRow(lastRowNum);
		short lastCellNum = lastRow.getLastCellNum();
		for (int i = 0; i < lastCellNum; i++) {
			sheet1.autoSizeColumn(i);
		}

		try {
			String fileName = "demo2-2-不同内容格式的单元格";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo2/getBook")
	public void getBook(HttpServletRequest request, HttpServletResponse response) {

		Workbook workbook = null;
		try {
			workbook = PoiUtils.getWorkBook("config/某个工作薄.xlsx");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if (workbook == null) {
			return;
		}

		try {
			String fileName = "demo2-3-获取某个工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping("/demo2/getBookContents")
	public void getBookContents(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = null;
		try {
			workbook = PoiUtils.getWorkBook("config/某个工作薄.xlsx");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if (workbook == null) {
			return;
		}

		// 获取到了服务器中的工作薄
		Sheet sheet1 = workbook.getSheetAt(0); // 获取第一个Sheet页
		if (sheet1 == null) {
			return;
		}

		// 获取行数
		int lastRowNum = sheet1.getLastRowNum();
		// 遍历行
		for (int rowNum = 0; rowNum < lastRowNum; rowNum++) {
			Row row = sheet1.getRow(rowNum);
			if (row == null) {
				continue;
			}

			// 获取当前行的列数
			short lastCellNum = row.getLastCellNum();
			for (short cellNum = 0; cellNum < lastCellNum; cellNum++) {
				Cell cell = row.getCell(cellNum);
				if (cell == null) {
					continue;
				}
				System.out.print(PoiUtils.getCellValue(cell) + "\t");
			}
			// 换行
			System.out.println();
		}

		try {
			String fileName = "demo2-4-获取某个工作薄中的数据";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo2/getBookContentsByText")
	public void getBookContentsByText(HttpServletRequest request, HttpServletResponse response) {

		Workbook workbook = null;
		try {
			workbook = PoiUtils.getWorkBook("config/某个工作薄.xls");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if (workbook == null) {
			return;
		}

		try {
			ExcelExtractor excelExtractor = new ExcelExtractor((HSSFWorkbook) workbook);
			excelExtractor.setIncludeSheetNames(false); // 设置不获取sheet
			String text = excelExtractor.getText();
			// 控制台打印文本内容
			System.out.println(text);
			String fileName = "demo2-5-获取某个工作薄中的数据以文本格式提取";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
			excelExtractor.close(); // 注意关闭资源
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
