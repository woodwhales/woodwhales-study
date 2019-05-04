package org.woodwhales.king.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.woodwhales.king.model.User;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);
}
