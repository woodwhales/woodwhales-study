package org.woodwhale.poi.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget("teacherEntity")
public class TeacherEntity {

	/** id */
	// @Excel(name = "主讲老师", orderNum = "2",isImportField =
	// "true_major,true_absent")
	private String id;
	
	/** name */
	@Excel(name = "主讲老师_major,代课老师_absent", orderNum = "1", isImportField = "true_major,true_absent", needMerge=true)
	private String name;
}
