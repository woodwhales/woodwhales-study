package org.woodwhales.power.mockfinal.service;

import org.woodwhales.power.mockfinal.dao.DogDao;

public class DogService {
	
	private DogDao dogDao;
	
	public DogService(DogDao dogDao) {
		super();
		this.dogDao = dogDao;
	}

	public int queryDogCount() {
		return dogDao.getCount();
	}
}
