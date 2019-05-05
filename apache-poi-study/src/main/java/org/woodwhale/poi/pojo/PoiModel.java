package org.woodwhale.poi.pojo;

public class PoiModel {
	private int rowIndex;
	private String content;

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PoiModel(int rowIndex, String content) {
		super();
		this.rowIndex = rowIndex;
		this.content = content;
	}

	public PoiModel() {
		super();
	}

}
