package org.woodwhale.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	public void insertUser(int age) {
		userDao.insert(age);
		int i = 10 / 0;
	}
	
	public void insertUserWithOutTx(int age) {
		userDao.insert(age);
		System.out.println("插入完成...");
	}

}