package com.postgres.rest;

import com.alibaba.fastjson.JSONObject;
import com.postgres.manager.SchemaHolder;
import com.postgres.mapper.UserMapper;
import com.postgres.model.ExamResult;
import com.postgres.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 用户接口类
 */
@RestController
@RequestMapping("/v1/user")
@Api(tags = "用户管理")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserMapper userMapper;

    @GetMapping("/user-from-schema")
    @ApiOperation(value = "从schema获取user数据")
    public List<User> getUserFromSchema(@RequestParam("name") String name) {
        List<User> userList = userMapper.getUserFromSchema(name);
        Map<String, Object> dataMap = userList.get(0).toMetaDataList();
        User user1 = new User();
        user1.fillByMetaData(dataMap);
        return userList;
    }

    @PostMapping("/insert-users-to-schema")
    @ApiOperation(value = "插入用户数据到schema")
    public List<Integer> insertUser2Schema(@RequestBody List<User> userList) {
        userMapper.insertUser2Schema(userList);
        List<Integer> ids = new ArrayList<>();
        userList.forEach((user) -> ids.add(user.getId()));
        return ids;
    }

    @GetMapping("/exam-result")
    @ApiOperation(value = "获取检测结果")
    public List<ExamResult> getExamResult() {
        return userMapper.getExamResult();
    }
}


