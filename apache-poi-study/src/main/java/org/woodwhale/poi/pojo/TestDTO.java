package org.woodwhale.poi.pojo;

import org.woodwhale.poi.annotation.ExcelAnnotation;

public class TestDTO {
	private Integer id;

	@ExcelAnnotation(exportName = "姓名", isMerge = true, mergeFlag = "id")
	private String name;
	
	@ExcelAnnotation(exportName = "性别", exportConvertSign = true)
	private Integer sex;
	
	@ExcelAnnotation(exportName = "学科")
	private String subject;
	
	@ExcelAnnotation(exportName = "分数")
	private Integer score;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public TestDTO(Integer id, String name, Integer sex, String subject, Integer score) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.subject = subject;
		this.score = score;
	}

	public TestDTO() {
		super();
	}

	public String getSexConvert() {
        switch (sex) {
            case 0:
                return "未知";
            case 1:
                return "男";
            case 2:
                return "女";
            default:
                return "未知";
        }
    }	
}
