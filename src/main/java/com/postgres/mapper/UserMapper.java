package com.postgres.mapper;

import com.postgres.manager.SchemaInterceptAnnotation;
import com.postgres.model.ExamResult;
import com.postgres.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
@SchemaInterceptAnnotation(schemaType = "business")
public interface UserMapper {
    /**
     * 从schema获取user数据
     *
     * @return user列表
     */
    List<User> getUserFromSchema(@Param("name") String name);

    /**
     * 插入用户数据到schema
     *
     * @param userList 用户列表
     */
    void insertUser2Schema(@Param("list") List<User> userList);

    /**
     * 获取测试成绩.
     *
     * @return 测试成绩列表
     */
    List<ExamResult> getExamResult();

    /**
     * 用Any代替IN条件
     *
     * @param column 数据库字段列名
     * @param valueList IN条件值列表
     * @return 满足条件的user列表
     */
    <T> List<User> getUserByAny(@Param("column") String column, @Param("valueList") List<T> valueList);

    /**
     * 用不等于all代替not in
     *
     * @param column 数据库字段列名
     * @param valueList not in条件值列表
     * @return 不满足条件的user列表
     */
    <T> List<User> getUserByNotAll(@Param("column") String column, @Param("valueList")  List<T> valueList);
}
