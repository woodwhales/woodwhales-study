package org.woodwhales.power.helloworld.service;

import org.woodwhales.power.helloworld.dao.UserDao;
import org.woodwhales.power.helloworld.entity.User;

public class UserService {
	
	private UserDao userDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public int queryUserCount() {
		return userDao.getCount();
	}
	
	public void saveUser(User user) {
		userDao.insertUser(user);
	}
	
}
