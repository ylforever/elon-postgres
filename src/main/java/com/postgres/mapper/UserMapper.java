package com.postgres.mapper;

import com.postgres.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 插入用户数据
     * @param user
     * @return
     */
    void insertUserV1(@Param("user") User user);

    /**
     * 插入用户数据
     * @param user
     * @return
     */
    void insertUserV2(User user);

    /**
     * 批量插入用户数据。
     *
     * @param userList
     */
    void insertUserV3(List<User> userList);
}
