package org.woodwhale.poi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.StringUtils;
import org.woodwhale.poi.annotation.ExcelAnnotation;
import org.woodwhale.poi.pojo.PoiModel;

public class DownloadUtil {
	/**
	 * 生成导出Excel
	 *
	 * @param title
	 * @param pojoClass
	 * @param dataSet
	 * @param out
	 */
	public static Workbook getWorkbook(String title, Class<?> pojoClass, Collection<?> dataSet) {
		Workbook workbook = null;
		try {
			// 首先检查数据看是否是正确的
			if (dataSet == null || dataSet.size() == 0) {
				throw new Exception("导出数据为空！");
			}
			
			// 声明一个工作薄
			workbook = new HSSFWorkbook();
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);

			// 标题
			List<String> exportFieldTitle = new ArrayList<>();
			List<Integer> exportFieldWidth = new ArrayList<>();
			// 拿到所有列名，以及导出的字段的get方法
			List<Method> methodObj = new ArrayList<>();
			Map<String, Method> convertMethod = new HashMap<>();
			// 得到所有字段
			Field[] fields = pojoClass.getDeclaredFields();

			// 是否求和配置
			boolean isSum = false;
			List<BigDecimal> sumList = new ArrayList<>();
			List<Boolean> isSumList = new ArrayList<>();
			List<Integer> scaleList = new ArrayList<>();
			List<Boolean> isMergeList = new ArrayList<>();
			List<Method> mergeFlagList = new ArrayList<>();

			// 遍历整个filed
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				ExcelAnnotation excelAnnotation = field.getAnnotation(ExcelAnnotation.class);
				// 如果设置了annotation
				if (excelAnnotation != null) {
					// 添加到标题
					exportFieldTitle.add(excelAnnotation.exportName());
					
					// 添加标题的列宽
					exportFieldWidth.add(excelAnnotation.exportFieldWidth());
					
					// 添加到需要导出的字段的方法
					String fieldName = field.getName();
					StringBuffer getMethodName = new StringBuffer("get");
					getMethodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
					Method getMethod = pojoClass.getMethod(getMethodName.toString(), new Class[] {});
					methodObj.add(getMethod);

					if (excelAnnotation.exportConvertSign()) {
						StringBuilder getConvertMethodName = new StringBuilder("get");
						getConvertMethodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1)).append("Convert");
						Method getConvertMethod = pojoClass.getMethod(getConvertMethodName.toString(), new Class[] {});
						convertMethod.put(getMethodName.toString(), getConvertMethod);
					}
					// 记录是否求和配置
					if (i != 0) {
						if (excelAnnotation.isSum()) {
							isSum = true;
							isSumList.add(true);
							sumList.add(new BigDecimal(0));
							scaleList.add(excelAnnotation.scale());
						} else {
							isSumList.add(false);
							sumList.add(null);
							scaleList.add(null);
						}
					} else {
						isSumList.add(false);
						sumList.add(null);
						scaleList.add(null);
					}

					// 是否合并
					isMergeList.add(excelAnnotation.isMerge());
					if (excelAnnotation.isMerge()) {
						StringBuilder getMergeFlagName = new StringBuilder("get");
						String mergeFlag;
						if (StringUtils.isEmpty(excelAnnotation.mergeFlag())) {
							mergeFlag = getMethodName.toString();
						} else {
							getMergeFlagName.append(excelAnnotation.mergeFlag().substring(0, 1).toUpperCase()).append(excelAnnotation.mergeFlag().substring(1));
							mergeFlag = getMergeFlagName.toString();
						}
						Method getMergeFlag = pojoClass.getMethod(mergeFlag, new Class[] {});
						mergeFlagList.add(getMergeFlag);
					} else {
						mergeFlagList.add(null);
					}
				}
			}
			
			int index = 0;
			
			// 产生表格标题行
			Row row = sheet.createRow(index);
			for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
				RichTextString text = new HSSFRichTextString(exportFieldTitle.get(i));
				// cell.setCellValue(text);
				// 设置样式
				PoiUtils.setCellStyle(workbook, row, i, text, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			}

			// 设置每行的列宽
			for (int i = 0; i < exportFieldWidth.size(); i++) {
				// 256=65280/255
				sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
			}
			Iterator<?> its = dataSet.iterator();
			HashMap<String, PoiModel> poiModelMap = new HashMap<>();
			// 设置单元格样色
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// 循环插入剩下的集合
			while (its.hasNext()) {
				// 从第二行开始写，第一行是标题
				index++;
				row = sheet.createRow(index);
				Object t = its.next();
				for (int k = 0; k < methodObj.size(); k++) {
					Cell cell = row.createCell(k);
					Method getMethod = methodObj.get(k);
					Object value;
					if (convertMethod.containsKey(getMethod.getName())) {
						Method cm = convertMethod.get(getMethod.getName());
						value = cm.invoke(t, new Object[] {});
					} else {
						value = getMethod.invoke(t, new Object[] {});
					}
					cell.setCellValue(value == null ? "" : value.toString());

					// 合计计算操作
					if (isSumList.get(k)) {
						BigDecimal tempNum = sumList.get(k);
						if (value instanceof Number) {
							sumList.set(k, tempNum.add(new BigDecimal(value.toString())));
						} else if (value instanceof String) {
							sumList.set(k, tempNum.add(new BigDecimal(1)));
						} else {
							sumList.set(k, tempNum.add(new BigDecimal(1)));
						}
					}

					// 合并列
					if (isMergeList.get(k)) {
						String mergeValue;
						Method cm = mergeFlagList.get(k);
						mergeValue = cm.invoke(t, new Object[] {}).toString();
						PoiModel poiModel = poiModelMap.get(getMethod.getName());
						if (poiModel == null) {
							poiModel = new PoiModel();
							poiModel.setRowIndex(index);
							poiModel.setContent(mergeValue);
							poiModelMap.put(getMethod.getName(), poiModel);
						} else {
							// 判断值是否相等，不相等则合并
							if (!poiModel.getContent().equals(mergeValue)) {
								// 合并单元格必须是2个或以上
								if (poiModel.getRowIndex() != (index - 1)) {
									CellRangeAddress cra = new CellRangeAddress(poiModel.getRowIndex(), index - 1, k, k);
									sheet.addMergedRegion(cra);
									sheet.getRow(poiModel.getRowIndex()).getCell(k).setCellStyle(cellStyle);
								}
								poiModel.setContent(mergeValue);
								poiModel.setRowIndex(index);
								poiModelMap.put(getMethod.getName(), poiModel);
							} else {
								// 最后一行无法在进行比较，直接合并
								if (index == dataSet.size()) {
									if (poiModel.getRowIndex() != index) {
										CellRangeAddress cra = new CellRangeAddress(poiModel.getRowIndex(), index, k, k);
										sheet.addMergedRegion(cra);
										sheet.getRow(poiModel.getRowIndex()).getCell(k).setCellStyle(cellStyle);
									}
								}
							}
						}
					}
				}
			}
			
			// 合计行显示操作
			if (isSum) {
				row = sheet.createRow(++index);
				row.createCell(0).setCellValue("合计");
				for (int k = 0; k < isSumList.size(); k++) {
					if (isSumList.get(k)) {
						Cell cell = row.createCell(k);
						cell.setCellValue((sumList.get(k).setScale(scaleList.get(k), RoundingMode.HALF_UP)).toString());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}
}
