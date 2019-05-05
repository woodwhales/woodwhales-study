package org.woodwhale.poi.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhale.poi.util.PoiUtils;

@RestController
public class Demo4Controller {

	@RequestMapping("/demo4/setFont")
	public void setFont(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");
		Row row1 = sheet1.createRow(1);

		// 创建一个字体处理类
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 24); // 设置字字体高度
		font.setFontName("Courier New"); // 设置字体
		font.setColor(Font.COLOR_RED);
		font.setItalic(true); // 设置斜体
		font.setStrikeout(true); // 设置删除线

		CellStyle cellStyle = workbook.createCellStyle(); // 创建样式
		cellStyle.setFont(font); // 将字体设置到样式

		Cell cell1 = row1.createCell(1);
		cell1.setCellValue("This is test of fonts");
		cell1.setCellStyle(cellStyle);

		try {
			String fileName = "demo4-1-字体处理的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo4/readAndWriteBook")
	public void readAndWriteBook(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = null;
		try {
			workbook = PoiUtils.getWorkBook("config/某个工作薄.xls");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// 获取sheet
		Sheet sheet1 = workbook.getSheetAt(0);

		// 获取行
		Row row3 = sheet1.getRow(3);
		// 获取单元格
		Cell cell0 = row3.getCell(0);
		Cell cell1 = row3.getCell(1);
		Cell cell2 = row3.getCell(2);
		String id = PoiUtils.getCellValue(cell0);
		String name = PoiUtils.getCellValue(cell1);
		String age = PoiUtils.getCellValue(cell2);
		System.out.println("id=" + id + ", name=" + name + ", age=" + age);

		// 获取行单元格样式
		CellStyle cellStyle0 = cell0.getCellStyle();
		CellStyle cellStyle1 = cell1.getCellStyle();
		CellStyle cellStyle2 = cell2.getCellStyle();

		// 获取行
		Row row4 = sheet1.getRow(4);
		if (row4 == null) {
			// 获取行不存在，需要创建行
			row4 = sheet1.createRow(4);

			// 创建单元格内容及样式
			Cell _cell0 = row4.createCell(0);
			_cell0.setCellValue(4); // 设置内容数据
			_cell0.setCellStyle(cellStyle0); // 设置样式

			Cell _cell1 = row4.createCell(1);
			_cell1.setCellValue("小波"); // 设置内容数据
			_cell1.setCellStyle(cellStyle1); // 设置样式

			Cell _cell2 = row4.createCell(2);
			_cell2.setCellValue(12); // 设置内容数据
			_cell2.setCellStyle(cellStyle2); // 设置样式
		}

		try {
			String fileName = "demo4-2-读取并重写后的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo4/createFormatData")
	public void createFormatData(HttpServletRequest request, HttpServletResponse response) {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1");

		// 创建一行
		Row row1 = sheet1.createRow(1);
		// 创建第1列单元格
		Cell cell0 = row1.createCell(0);
		cell0.setCellValue(111111.25);

		// 设置样式
		CellStyle cellStyle = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("0.0")); // 设置数据格式
		cell0.setCellStyle(cellStyle);

		// 创建一行
		Row row2 = sheet1.createRow(2);
		// 创建第2列单元格
		Cell cell1 = row2.createCell(1);
		cell1.setCellValue(111111.25);

		// 设置样式
		CellStyle cellStyle1 = workbook.createCellStyle();
		DataFormat format1 = workbook.createDataFormat();
		cellStyle1.setDataFormat(format1.getFormat("#,##0.000")); // 设置数据格式
		cell1.setCellStyle(cellStyle1);

		sheet1.autoSizeColumn(0); // 设置自适应内容列宽
		sheet1.autoSizeColumn(1); // 设置自适应内容列宽
		try {
			String fileName = "demo4-3-自定义数据格式的工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo4/drawPicture1")
	public void drawPicture1(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1"); // 创建 sheet

		// 通过网络获取图片资源
		// bufferImg = PoiUtils.getPicture("http://localhost:8080/pic2.jpg");

		BufferedImage bufferImg = null;
		try {
			bufferImg = PoiUtils.getPicture("static/pic.jpg");
			int height = bufferImg.getHeight();
			int width = bufferImg.getWidth();
			System.out.println("height : " + height + ", width : " + width);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// anchor 主要用于设置图片的属性
		CreationHelper helper = workbook.getCreationHelper();
		ClientAnchor anchor = helper.createClientAnchor();

		// 以图片左上角会顶格对齐下面设置的 col1 和 row1
		anchor.setCol1((short) 1);
		anchor.setRow1(1);
		anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);

		byte[] imageByteArray = null;
		try {
			imageByteArray = PoiUtils.getImageByteArray(bufferImg, "jpg");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int pictureIdx = workbook.addPicture(imageByteArray, HSSFWorkbook.PICTURE_TYPE_JPEG);

		Drawing<?> drawing = sheet1.createDrawingPatriarch();
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		pict.resize();

		try {
			String fileName = "demo4-4-1-插入图片到工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo4/drawPicture2")
	public void drawPicture2(HttpServletRequest request, HttpServletResponse response) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Sheet1"); // 创建 sheet

		HSSFPatriarch patriarch = (HSSFPatriarch) sheet1.createDrawingPatriarch();
		// 以单元格的左上角为起点，在第1行第1列的单元格的左上角，相对偏移量 x=110，y=100 的位置开始画。
		// 以单元格的左上角为起点，在第11行第11列的单元格的左上角，相对偏移量 x=1024/2，y=256/2 的位置,也就是单元格的中心点画结束。
		HSSFClientAnchor anchor = new HSSFClientAnchor(110, 100, (1024 / 2), (256 / 2), (short) 0, (short) 0,
				(short) 10, (short) 10);
		anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
		// 关于 HSSFClientAnchor(dx1, dy1, dx2, dy2, col1, row1, col2,
		// row2)的参数，有必要在这里说明一下：
		// dx1：起始单元格的x偏移量，
		// dy1：起始单元格的y偏移量，
		// dx2：终止单元格的x偏移量，
		// dy2：终止单元格的y偏移量，
		// x 偏移量的取值范围为0-1023，因为将单元格的水平方向高度分割成了1024等份
		// y 偏移量的取值范围为0-255，因为将单元格的垂直方向高度分割成了256等份
		// col1：起始单元格列序号，从0开始计算
		// row1：起始单元格行序号，从0开始计算
		// col2：终止单元格列序号，从0开始计算
		// row2：终止单元格行序号，从0开始计算
		// 添加多个图片时 : 多个 pic 应该share同一个 Drawing 在同一个 sheet 里面

		byte[] imageByteArray = null;
		try {
			imageByteArray = PoiUtils.getPicture("static/pic.jpg", "jpg");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		int pictureIdx = workbook.addPicture(imageByteArray, HSSFWorkbook.PICTURE_TYPE_JPEG);

		patriarch.createPicture(anchor, pictureIdx);

		// 关于偏移量的解释，设置一下单元格的边框，就可以一目了然
		Cell ceel0 = sheet1.createRow(0).createCell(0);
		Cell ceel10 = sheet1.createRow(10).createCell(10);
		PoiUtils.setCell_BLANK_THIN_BorderStyle(workbook, ceel0);
		PoiUtils.setCell_BLANK_THIN_BorderStyle(workbook, ceel10);

		// sheet1.createFreezePane( 0, 1, 0, 1 ); // 冻结第一行
		sheet1.createFreezePane(1, 0, 1, 0); // 冻结第一列

		try {
			String fileName = "demo4-4-2-插入图片到工作薄";
			PoiUtils.downloadExcel(workbook, request, response, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
