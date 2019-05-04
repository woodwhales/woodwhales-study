package org.woodwhales.power.construction.service;

import org.woodwhales.power.construction.dao.FishDao;

public class FishService {

	public void saveFish(String username, String password) {
		FishDao fishDao = new FishDao(username, password);
		fishDao.insert();
	}
}
