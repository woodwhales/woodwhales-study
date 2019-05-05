package org.woodwhale.poi.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

public class PoiUtils {
	private static String OFFICE_EXCEL_2003_POSTFIX = "xls";
	private static String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

	/**
	 * 下载 excel 文件到客户端
	 * 
	 * @param workbook
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	public static void downloadExcel(Workbook workbook, HttpServletRequest request, HttpServletResponse response,
			String fileName) throws IOException {
		
		if(workbook == null) {
			return;
		}
		
		// 添加后缀名
		String suffix = null;
		if (workbook instanceof HSSFWorkbook) {
			suffix = "." + OFFICE_EXCEL_2003_POSTFIX;
		} else if (workbook instanceof XSSFWorkbook) {
			suffix = "." + OFFICE_EXCEL_2010_POSTFIX;
		} else {
			throw new RuntimeException("Unexpected workbook (" + workbook + ")");
		}
		fileName += suffix;

		// 获取客户端浏览器版本
		String agent = request.getHeader("user-agent");
		fileName = encodeDownloadFilename(fileName, agent);

		// 清空response，设置响应头
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

		// 输出文件
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
	}

	/**
	 * 火狐浏览器下载文件名乱码解决
	 * 
	 * @param filename
	 * @param agent
	 * @return
	 * @throws IOException
	 */
	public static String encodeDownloadFilename(String fileName, String agent) throws IOException {
		if (agent.contains("Firefox")) { // 火狐浏览器
			fileName = "=?UTF-8?B?" + Base64.getEncoder().encodeToString(fileName.getBytes("utf-8")) + "?=";
		} else { // IE及其他浏览器
			fileName = URLEncoder.encode(fileName, "utf-8");
		}
		return fileName;
	}

	/**
	 * 加载excel文件
	 * 
	 * @param
	 * @return file
	 * @throws IOException
	 */
	public static Workbook getWorkBook(String fileName) throws IOException {
		Workbook workbook = null;

		File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + fileName);
		String bookName = file.getName();
		FileInputStream in = new FileInputStream(file);
		String suffix = bookName.substring(bookName.lastIndexOf(".") + 1); // 获取扩展名

		if (OFFICE_EXCEL_2003_POSTFIX.equals(suffix)) {
			workbook = new HSSFWorkbook(in);
		} else if (OFFICE_EXCEL_2010_POSTFIX.equals(suffix)) {
			workbook = new XSSFWorkbook(in);
		}
		return workbook;
	}

	public static byte[] getPicture(URL picFileURL, String formatName) throws IOException {
		BufferedImage bufferImg = getPicture(picFileURL);
		return getImageByteArray(bufferImg, formatName);
	}

	public static BufferedImage getPicture(URL picFileURL) throws IOException {
		BufferedImage bufferImg = ImageIO.read(picFileURL);
		return bufferImg;
	}
	
	public static ImageObject getImageObject(BufferedImage bufferImg) throws IOException {
		int height = bufferImg.getHeight();
		int width = bufferImg.getWidth();
		return new ImageObject(height, width);
	}
	
	public static byte[] getImageByteArray(BufferedImage bufferImg, String formatName) throws IOException {
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		ImageIO.write(bufferImg, formatName, byteArrayOut);
		return byteArrayOut.toByteArray();
	}

	public static byte[] getPicture(String picFileName, String formatName) throws IOException {
		BufferedImage bufferImg = getPicture(picFileName);
		return getImageByteArray(bufferImg, formatName);
	}

	public static BufferedImage getPicture(String picFileName) throws IOException {
		File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + picFileName);
		BufferedImage bufferImg = ImageIO.read(new File(file.getPath()));
		return bufferImg;
	}
	
	/**
	 * 获取单元格的内容
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		CellType cellType = cell.getCellTypeEnum();
		switch (cellType) {
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case STRING:
			return cell.getRichStringCellValue().getString();
		case BOOLEAN:
			return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
		case BLANK:
			return "";
		case ERROR:
			return FormulaError.forInt(cell.getErrorCellValue()).getString();
		default:
			throw new RuntimeException("Unexpected celltype (" + cellType + ")");
		}
	}

	/**
	 * 设置单元格的水平和垂直对齐方式
	 * 
	 * @param workbook
	 * @param row
	 * @param column
	 * @param cellValue
	 * @param halign
	 * @param valign
	 */
	public static void setCellStyle(Workbook workbook, Row row, int column, String cellValue,
			HorizontalAlignment halign, VerticalAlignment valign) {
		CellStyle cellStyle = workbook.createCellStyle(); // 创建样式
		Cell cell = row.createCell(column);
		cellStyle.setAlignment(halign); // 设置单元格水平方向对齐方式
		cellStyle.setVerticalAlignment(valign); // 设置单元格垂直方向对齐方式
		cell.setCellStyle(cellStyle);
		cell.setCellValue(cellValue);
	}
	
	
	public static void setCellStyle(Workbook workbook, Row row, int column, RichTextString cellValue,
			HorizontalAlignment halign, VerticalAlignment valign) {
		CellStyle cellStyle = workbook.createCellStyle(); // 创建样式
		Cell cell = row.createCell(column);
		cellStyle.setAlignment(halign); // 设置单元格水平方向对齐方式
		cellStyle.setVerticalAlignment(valign); // 设置单元格垂直方向对齐方式
		cell.setCellStyle(cellStyle);
		cell.setCellValue(cellValue);
	}

	/**
	 * 设置单元格四周边框为黑色实线样式
	 * 
	 * @param workbook
	 * @param cell
	 * @return
	 */
	public static Cell setCell_BLANK_THIN_BorderStyle(Workbook workbook, Cell cell) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cell.setCellStyle(cellStyle);
		return cell;
	}
	
	static class ImageObject {
		private int height;
		private int weight;

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public ImageObject(int height, int weight) {
			this.height = height;
			this.weight = weight;
		}
	}
	
}
