package org.woodwhale.tx;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insert(int age) {
		String sql = "INSERT INTO `tbl_user`(username, age) VALUES(?, ?)";
		String username = UUID.randomUUID().toString().substring(0, 5);
		jdbcTemplate.update(sql, username, age);
	}
}
