package org.woodwhales.power.construction.dao;

import lombok.Data;

@Data
public class FishDao {

	private String username;
	private String password;
	
	public FishDao(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public void insert() {
		/*
		 * Fish fish = new Fish(username, password);
		 * session.save(fish);
		 */
		throw new UnsupportedOperationException();
	}
}
