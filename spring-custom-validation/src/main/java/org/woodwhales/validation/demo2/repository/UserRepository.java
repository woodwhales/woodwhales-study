package org.woodwhales.validation.demo2.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.woodwhales.validation.demo2.dto.User;

@Repository
public class UserRepository {
	
	private List<User> registeredUsers = new LinkedList<>();
	
	public boolean save(User user) {
        registeredUsers.add(user);
        return true;
    }
	
	public Optional<User> findByLogin(String login) {
		save(new User("woodwhales", "admin"));
		
        return registeredUsers.stream()
				                .filter(user -> user.getLogin().equals(login))
				                .findFirst();
    }
	
}

