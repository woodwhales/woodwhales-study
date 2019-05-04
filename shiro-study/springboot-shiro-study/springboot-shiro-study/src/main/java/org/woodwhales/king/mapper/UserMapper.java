package org.woodwhales.king.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.woodwhales.king.entity.User;

@Service
public interface UserMapper {

	User findUserByUsername(@Param("userName") String userName);

	User findById(@Param("id") Long id);
}
