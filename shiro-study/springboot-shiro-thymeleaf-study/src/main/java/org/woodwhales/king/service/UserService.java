package org.woodwhales.king.service;

import org.woodwhales.king.model.User;

public interface UserService {

    User findByUsername(String username);
}
