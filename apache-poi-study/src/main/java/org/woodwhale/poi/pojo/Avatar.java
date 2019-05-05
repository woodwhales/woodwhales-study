package org.woodwhale.poi.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

@ExcelTarget("avatars")
public class Avatar {

	@Excel(name = "演员编号")
	private String id;
	
	@Excel(name = "演员名称")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Avatar(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Avatar() {
		super();
	}

}
