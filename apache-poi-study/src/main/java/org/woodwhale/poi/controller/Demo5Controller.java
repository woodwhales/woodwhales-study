package org.woodwhale.poi.controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIDocument;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhale.poi.util.PoiUtils;

@RestController
public class Demo5Controller {

	@RequestMapping("/demo5/createHeaderAndFooter")
	public void createHeaderAndFooter(HttpServletRequest request, HttpServletResponse response) {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");

		Header header = sheet1.getHeader();// 得到页眉
		header.setLeft("页眉左边");
		header.setRight("页眉右边");
		header.setCenter("页眉中间");

		Footer footer = sheet1.getFooter();// 得到页脚
		footer.setLeft("页脚左边");
		footer.setRight("页脚右边");
		footer.setCenter("页脚中间");

		try {
			String fileName = "demo5-1-带有页眉页脚的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo5/useFormula")
	public void useFormula(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");

		// 1. 基本计算
		Row row = sheet1.createRow(0);
		row.createCell(0).setCellValue("基本计算");
		row.createCell(1).setCellFormula("2+3*4");
		row.createCell(2).setCellValue(10);
		row.createCell(3).setCellFormula("A1*B1");

		// 2. SUM函数
		Row row1 = sheet1.createRow(1);
		row1.createCell(0).setCellValue("SUM函数");
		row1.createCell(1).setCellValue(1);
		row1.createCell(2).setCellValue(2);
		row1.createCell(3).setCellValue(3);
		row1.createCell(4).setCellValue(4);
		row1.createCell(5).setCellValue(5);

		Row row2 = sheet1.createRow(2);
		row2.createCell(1).setCellFormula("sum(B2,C2)"); // 等价于"B2+C2"
		row2.createCell(2).setCellFormula("sum(B2:E2)"); // 等价于"B2+C2+D2+E2"

		CellStyle style = workbook.createCellStyle();
		style.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd"));

		Row row3 = sheet1.createRow(3);
		Calendar date = Calendar.getInstance();// 日历对象

		// 3. 日期函数
		row3.createCell(0).setCellValue("日期函数");
		Cell cell;

		cell = row3.createCell(1);
		date.set(2011, 2, 7);
		cell.setCellValue(date.getTime());
		cell.setCellStyle(style);// 第一个单元格开始时间设置完成

		cell = row3.createCell(2);
		date.set(2014, 4, 25);
		cell.setCellValue(date.getTime());
		cell.setCellStyle(style);// 第一个单元格结束时间设置完成
		row3.createCell(3).setCellFormula("CONCATENATE(DATEDIF(B4,C4,\"y\"),\"年\")");
		row3.createCell(4).setCellFormula("CONCATENATE(DATEDIF(B4,C4,\"m\"),\"月\")");
		row3.createCell(5).setCellFormula("CONCATENATE(DATEDIF(B4,C4,\"d\"),\"日\")");

		// 4. 字符串相关函数
		Row row4 = sheet1.createRow(4);
		row4.createCell(0).setCellValue("字符串相关函数");
		row4.createCell(1).setCellValue("abcdefg");
		row4.createCell(2).setCellValue("aa bb cc dd ee fF GG");
		row4.createCell(3).setCellFormula("UPPER(B4)");
		row4.createCell(4).setCellFormula("PROPER(C4)");

		// 5. IF函数
		Row row5 = sheet1.createRow(5);
		row5.createCell(0).setCellValue("IF函数");
		row5.createCell(1).setCellValue(12);
		row5.createCell(2).setCellValue(23);
		row5.createCell(3).setCellFormula("IF(B6>C6,\"B6大于C6\",\"B6小于等于C6\")");

		// 6. CountIf和SumIf函数
		// COUNTIF(range,criteria)：满足某条件的计数的函数。参数range：需要进行读数的计数；参数criteria：条件表达式，只有当满足此条件时才进行计数。
		// SumIF(criteria_range, criteria,sum_range)：用于统计某区域内满足某条件的值的求和。
		// 参数criteria_range：条件测试区域，第二个参数Criteria中的条件将与此区域中的值进行比较；
		// 参数criteria：条件测试值，满足条件的对应的sum_range项将进行求和计算；
		// 参数sum_range：汇总数据所在区域，求和时会排除掉不满足Criteria条件的对应的项。
		Row row6 = sheet1.createRow(6);
		row6.createCell(0).setCellValue("CountIf和SumIf函数");
		row6.createCell(1).setCellValue(57);
		row6.createCell(2).setCellValue(89);
		row6.createCell(3).setCellValue(56);
		row6.createCell(4).setCellValue(67);
		row6.createCell(5).setCellValue(60);
		row6.createCell(6).setCellValue(73);
		row6.createCell(7).setCellFormula("COUNTIF(B7:F7,\">=60\")");
		row6.createCell(8).setCellFormula("SUMIF(B7:F7,\">=60\",B7:F7)");

		// 7. 随机数函数
		Row row7 = sheet1.createRow(7);
		row7.createCell(0).setCellValue("随机数函数");
		row7.createCell(1).setCellFormula("RAND()"); // 取0-1之间的随机数
		row7.createCell(2).setCellFormula("int(RAND()*100)"); // 取0-100之间的随机整数
		row7.createCell(3).setCellFormula("rand()*10+10"); // 取10-20之间的随机实数
		row7.createCell(4).setCellFormula("CHAR(INT(RAND()*26)+97)"); // 随机小写字母
		row7.createCell(5).setCellFormula("CHAR(INT(RAND()*26)+65)"); // 随机大写字母
		row7.createCell(6).setCellFormula("CHAR(INT(RAND()*26)+if(INT(RAND()*2)=0,97,65))"); // 随机大小写字母

		// Lookup函数
		Sheet sheet2 = workbook.createSheet("Sheet2");
		Row sheet2_row = sheet2.createRow(0);
		sheet2_row.createCell(0).setCellValue(0);
		sheet2_row.createCell(1).setCellValue(59);
		sheet2_row.createCell(2).setCellValue("不及格");
		sheet2_row = sheet2.createRow(1);
		sheet2_row.createCell(0).setCellValue(60);
		sheet2_row.createCell(1).setCellValue(69);
		sheet2_row.createCell(2).setCellValue("及格");
		sheet2_row = sheet2.createRow(2);
		sheet2_row.createCell(0).setCellValue(70);
		sheet2_row.createCell(1).setCellValue(79);
		sheet2_row.createCell(2).setCellValue("良好");
		sheet2_row = sheet2.createRow(3);
		sheet2_row.createCell(0).setCellValue(80);
		sheet2_row.createCell(1).setCellValue(100);
		sheet2_row.createCell(2).setCellValue("优秀");
		sheet2_row = sheet2.createRow(4);
		sheet2_row.createCell(0).setCellValue(75);
		sheet2_row.createCell(1).setCellFormula("LOOKUP(A5,$A$1:$A$4,$C$1:$C$4)");
		sheet2_row.createCell(2).setCellFormula("VLOOKUP(A5,$A$1:$C$4,3,true)");

		try {
			String fileName = "demo5-2-带公式的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo5/createDocument")
	public void createDocument(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = new HSSFWorkbook();
		((POIDocument) workbook).createInformationProperties();// 创建文档信息
		DocumentSummaryInformation dsi = ((POIDocument) workbook).getDocumentSummaryInformation();// 摘要信息
		dsi.setCategory("类别:Excel文件");// 类别
		dsi.setManager("管理者:woodwhale'boss");// 管理者
		dsi.setCompany("公司:小木鲸鱼");// 公司
		SummaryInformation si = ((POIDocument) workbook).getSummaryInformation();// 摘要信息
		si.setSubject("主题:poi报表技术");// 主题
		si.setTitle("标题:文档信息");// 标题
		si.setAuthor("作者:woodwhale");// 作者
		si.setComments("备注:POI测试文档");// 备注

		try {
			String fileName = "demo5-3-带文档信息的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo5/setLock")
	public void setLock(HttpServletRequest request, HttpServletResponse response) {
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("sheet1");

		Row row = sheet1.createRow(1);
		Cell cell = row.createCell(1);
		cell.setCellValue("已锁定");
		CellStyle locked = workbook.createCellStyle();
		locked.setLocked(true);// 设置锁定
		cell.setCellStyle(locked);
		cell = row.createCell(2);
		cell.setCellValue("未锁定");
		CellStyle unlocked = workbook.createCellStyle();
		unlocked.setLocked(false);// 设置不锁定
		cell.setCellStyle(unlocked);
		sheet1.protectSheet("admin");// 设置保护密码, 在"审阅"工具栏，"撤销工作表保护"中输入密码解锁

		try {
			String fileName = "demo5-4-带文档信息的工作薄.xls";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo5/setPrintLandscape")
	public void setPrintLandscape(HttpServletRequest request, HttpServletResponse response) {
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("sheet1");

		// 数据准备
		for (int i = 0; i < 1000; i++) {
			Row row = sheet1.createRow(i);
			String content;
			content = "正文";
			if (i < 5) {
				content = "标题头";
			}

			for (int j = 0; j < 4; j++) {
				row.createCell(j).setCellValue(content + "--" + i + "--" + (j + 1));
			}
		}

		// 是否自适应界面
		sheet1.setFitToPage(true);
		// 设置每页固定打印标题
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 4, 0, 4);
		sheet1.setRepeatingRows(cellRangeAddress);

		// 打印参数设置
		PrintSetup printSetup = sheet1.getPrintSetup();
		printSetup.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)
		printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE); // 纸张类型

		// 设置每 20 行数据分页打印一次（不包含标题头）
		int lastRowNum = sheet1.getLastRowNum();
		for (int i = 0; i < lastRowNum; i++) {
			// 每30条数据分页一次
			if (i != 0 && i % 25 == 0) {
				sheet1.setRowBreak(i);
			}
		}

		// 设置打印页码信息
		Footer footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + "  of  " + HSSFFooter.numPages());
		
		try {
			String fileName = "demo5-5-横向打印及自定义打印设置的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
