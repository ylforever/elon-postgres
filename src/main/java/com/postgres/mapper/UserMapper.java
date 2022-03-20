package com.postgres.mapper;

import com.postgres.manager.SchemaInterceptAnnotation;
import com.postgres.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@SchemaInterceptAnnotation(schemaType = "business")
public interface UserMapper {
    /**
     * 从schema获取user数据
     *
     * @return user列表
     */
    List<User> getUserFromSchema (@Param("name") String name);

    /**
     * 插入用户数据到schema
     *
     * @param userList 用户列表
     */
    void insertUser2Schema(@Param("list") List<User> userList);
}
