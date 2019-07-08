package org.woodwhale.annotation.code09;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig9 {

	@Profile("test")
	@Bean("testDataSource")
	public MyDataSource testDataSource() {
		MyDataSource dataSource = new MyDataSource();
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		return dataSource; 
	}
	
	@Profile("prod")
	@Bean("prodDataSource")
	public MyDataSource prodDataSource() {
		MyDataSource dataSource = new MyDataSource();
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/prod");
		return dataSource; 
	}
	
	@Profile("dev")
	@Bean("devDataSource")
	public MyDataSource devDataSource() {
		MyDataSource dataSource = new MyDataSource();
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/dev");
		return dataSource; 
	}
	
	@Bean("dataSource")
	public MyDataSource dataSource() {
		MyDataSource dataSource = new MyDataSource();
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/default");
		return dataSource; 
	}
}
