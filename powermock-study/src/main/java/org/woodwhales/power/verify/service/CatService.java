package org.woodwhales.power.verify.service;

import org.woodwhales.power.verify.dao.CatDao;
import org.woodwhales.power.verify.entity.Cat;

public class CatService {

	public void saveOrUpdate(Cat cat) {
		CatDao catDao = new CatDao();
		
		if(catDao.getCount(cat) > 0) {
			catDao.updateCat(cat);
		} else {
			catDao.insertCat(cat);
		}
		
	}
}
