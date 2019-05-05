package org.woodwhale.poi.pojo;

import java.io.Serializable;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

@ExcelTarget("movie")
public class Movie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Excel(name = "动漫编号_movieId", orderNum = "2")
	private String id;

	@Excel(name = "源自动漫_movieId", width = 30, orderNum = "1")
	private String movieName;

	@ExcelCollection(name = "演员_movieId", orderNum = "3")
	private List<Avatar> avatars;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Movie(String id, String movieName) {
		super();
		this.id = id;
		this.movieName = movieName;
	}

	public Movie() {
		super();
	}

	public List<Avatar> getAvatars() {
		return avatars;
	}

	public void setAvatars(List<Avatar> avatars) {
		this.avatars = avatars;
	}

	public Movie(String id, String movieName, List<Avatar> avatars) {
		super();
		this.id = id;
		this.movieName = movieName;
		this.avatars = avatars;
	}

}
