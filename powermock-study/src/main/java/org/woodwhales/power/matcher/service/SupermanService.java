package org.woodwhales.power.matcher.service;

import org.woodwhales.power.matcher.dao.SupermanDao;

public class SupermanService {

	public String find(String name) {
		SupermanDao supermanDao = new SupermanDao();
		return supermanDao.queryByName(name);
	}
}
