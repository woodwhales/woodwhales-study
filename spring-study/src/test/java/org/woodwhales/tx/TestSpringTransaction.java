package org.woodwhales.tx;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.woodwhales.tx.dao.BookShopDao;
import org.woodwhales.tx.service.BookShopService;
import org.woodwhales.tx.service.Cashier;

public class TestSpringTransaction {
	private ApplicationContext ctx = null;
	private BookShopDao bookShopDao = null;
	private BookShopService bookShopService = null;
	private Cashier cashier = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("tx/applicationContext.xml");
		bookShopDao = ctx.getBean(BookShopDao.class);
		bookShopService = ctx.getBean(BookShopService.class);
		cashier = ctx.getBean(Cashier.class);
	}
	
	/**
	 * 事务的传播行为
	 */
	@Test
	public void testTransactionlPropagation(){
		cashier.checkout("AA", Arrays.asList("1001", "1002"));
	}
	
	@Test
	public void testBookShopService(){
		bookShopService.purchase("AA", "1001");
	}
	
	/**
	 * 更新账户余额
	 */
	@Test
	public void testBookShopDaoUpdateUserAccount(){
		bookShopDao.updateUserAccount("AA", 200);
	}
	
	/**
	 * 库存减1
	 */
	@Test
	public void testBookShopDaoUpdateBookStock(){
		bookShopDao.updateBookStock("1001");
	}
	
	/**
	 * 根据书的编号查价格
	 */
	@Test
	public void testBookShopDaoFindPriceByIsbn() {
		System.out.println(bookShopDao.findBookPriceByIsbn("1001"));
	}
}
