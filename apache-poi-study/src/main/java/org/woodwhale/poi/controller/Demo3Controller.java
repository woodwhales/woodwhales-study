package org.woodwhale.poi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhale.poi.util.PoiUtils;

@RestController
public class Demo3Controller {

	/**
	 * 	水平对齐相关参数:
	 * 	如果是左侧对齐就是 HSSFCellStyle.ALIGN_FILL;
		如果是居中对齐就是 HSSFCellStyle.ALIGN_CENTER;
		如果是右侧对齐就是 HSSFCellStyle.ALIGN_RIGHT;
		如果是跨列居中就是 HSSFCellStyle.ALIGN_CENTER_SELECTION;
		如果是两端对齐就是 HSSFCellStyle.ALIGN_JUSTIFY;
		如果是填充就是 HSSFCellStyle.ALIGN_FILL;
		
		垂直对齐相关参数:
		如果是靠上就是 HSSFCellStyle.VERTICAL_TOP;
		如果是居中就是 HSSFCellStyle.VERTICAL_CENTER;
		如果是靠下就是 HSSFCellStyle.VERTICAL_BOTTOM;
		如果是两端对齐就是 HSSFCellStyle.VERTICAL_JUSTIFY;
	 * @param response
	 */
	@RequestMapping("/demo3/cellAlgin")
	public void cellAlgin(HttpServletRequest request, HttpServletResponse response) {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");

		Row row0 = sheet1.createRow(0);
		row0.setHeightInPoints(50);// 设置当前行的高度
		PoiUtils.setCellStyle(workbook, row0, 0, "第1列--水平居中", HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
		PoiUtils.setCellStyle(workbook, row0, 1, "第2列--水平居左", HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
		PoiUtils.setCellStyle(workbook, row0, 2, "第3列--水平居右", HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
		PoiUtils.setCellStyle(workbook, row0, 3, "第4列--水平默认对齐", HorizontalAlignment.GENERAL, VerticalAlignment.CENTER);
		// 将文本内容以一个单位的形式重复填充整个单元格, 内容超出就不显示, 不自动换行
		PoiUtils.setCellStyle(workbook, row0, 4, "第5列--水平填充", HorizontalAlignment.FILL, VerticalAlignment.CENTER);
		// 根据单元格宽度自适应, 内容超出就自动换行
		PoiUtils.setCellStyle(workbook, row0, 5, "第6列--水平对齐--根据单元格宽度自适应，内容超出就自动换行", HorizontalAlignment.JUSTIFY,
				VerticalAlignment.CENTER);

		Row row1 = sheet1.createRow(1);
		row1.setHeightInPoints(100);// 设置当前行的高度
		PoiUtils.setCellStyle(workbook, row1, 0, "第1列--垂直居中", HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM);
		PoiUtils.setCellStyle(workbook, row1, 1, "第2列--垂直底部对齐", HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM);
		PoiUtils.setCellStyle(workbook, row1, 2, "第3列--垂直顶对齐", HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM);
		PoiUtils.setCellStyle(workbook, row1, 5, "第6列--根据单元格高度自适应，内容超出就自动换行", HorizontalAlignment.LEFT,
				VerticalAlignment.JUSTIFY);
		try {
			String fileName = "demo3-1-有内容位置对齐样式的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo3/cellBorder")
	public void cellBorder(HttpServletRequest request, HttpServletResponse response) {
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");

		Row row0 = sheet1.createRow(2);

		CellStyle cellStyle = workbook.createCellStyle();

		// 设置单元格底部为绿色线边框
		cellStyle.setBottomBorderColor(IndexedColors.GREEN.getIndex());
		cellStyle.setBorderTop(BorderStyle.THIN);

		// 设置单元格底部为红色点边框
		cellStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
		cellStyle.setBorderBottom(BorderStyle.DOTTED);
		Cell cell3 = row0.createCell(3);
		cell3.setCellStyle(cellStyle);

		try {
			String fileName = "demo3-2-有单元格边框样式的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo3/setAutoColumn")
	public void setAutoColumn(HttpServletRequest request, HttpServletResponse response) {
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");

		Row row2 = sheet1.createRow(2); // 创建第三行

		row2.setHeightInPoints(50); // 设置当前行的高度

		Cell cell0 = row2.createCell(0);
		cell0.setCellValue("正文正文正文正文正文正文正文正文正文正文");

		CellStyle cellStyle = workbook.createCellStyle();

		// 设置字体
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 20); // 设置字号
		font.setColor(Font.COLOR_RED); // 字体颜色
		font.setFontName("Microsoft YaHei UI"); // 字体
		font.setBold(true); // 宽度
		font.setItalic(true); // 是否使用斜体
		cellStyle.setFont(font);

		cell0.setCellStyle(cellStyle);

		CellStyle cell1CellStyle = workbook.createCellStyle();
		cell1CellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cell1CellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		cell1CellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cell1CellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		cell1CellStyle.setBorderLeft(BorderStyle.THIN);
		cell1CellStyle.setBorderRight(BorderStyle.THIN);
		cell1CellStyle.setBorderBottom(BorderStyle.THIN);
		cell1CellStyle.setBorderTop(BorderStyle.THIN);
		Cell cell1 = row2.createCell(1);
		cell1.setCellValue("abcdef");
		cell1.setCellStyle(cell1CellStyle);

		sheet1.autoSizeColumn(0); // 设置自适应内容列宽
		sheet1.setColumnWidth(1, 4 * 256);// 设置第2列的宽度是5个字符宽度
		
		try {
			String fileName = "demo3-3-根据单元格自动调整列宽的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo3/setCellColorAndContent")
	public void setCellColorAndContent(HttpServletRequest request, HttpServletResponse response) {
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");
		Row row2 = sheet1.createRow(2); // 创建第三行

		row2.setHeightInPoints(50); // 设置当前行的高度

		Cell cell0 = row2.createCell(0);
		cell0.setCellValue("XXXXX");

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex()); // 背景色
		cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
		cell0.setCellStyle(cellStyle);

		Cell cell1 = row2.createCell(1);
		cell1.setCellValue("YYYYY");
		CellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setFillForegroundColor(IndexedColors.RED.getIndex()); // 前景色
		cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell1.setCellStyle(cellStyle1);
		
		try {
			String fileName = "demo3-4-单元格带颜色和填充背景";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/demo3/mergrCell")
	public void mergrCell(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");

		Row row2 = sheet1.createRow(2); // 创建第三行

		Cell cell1 = row2.createCell(1);
		cell1.setCellValue("单元格合并操作");
		
		CellRangeAddress cellRangeAddress = new CellRangeAddress(2,3,1,3); // 单元格范围：起始行，终止行，起始列，终止列
		sheet1.addMergedRegion(cellRangeAddress);
		
		PoiUtils.setCell_BLANK_THIN_BorderStyle(workbook, cell1); // 设置单元格四周边框样式为黑色实线，由于下边和右边被合并了，所以设置无效
		
		Cell cell3 = row2.createCell(3);
		PoiUtils.setCell_BLANK_THIN_BorderStyle(workbook, cell3); // 设置单元格四周边框样式为黑色实线，由于下边和左边被合并了，所以设置无效
		
		try {
			String fileName = "demo3-5-合并的单元格";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
