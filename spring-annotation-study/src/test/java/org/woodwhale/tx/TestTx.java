package org.woodwhale.tx;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestTx {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
	
	@Test
	public void testWithTx() {
		UserService userService = applicationContext.getBean(UserService.class);
		userService.insertUser(20);
		applicationContext.close();
	}
	
	@Test
	public void testWithOutTx() {
		UserService userService = applicationContext.getBean(UserService.class);
		userService.insertUserWithOutTx(18);
		applicationContext.close();
	}
}
