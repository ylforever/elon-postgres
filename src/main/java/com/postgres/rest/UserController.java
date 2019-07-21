package com.postgres.rest;

import com.postgres.mapper.UserMapper;
import com.postgres.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户接口类
 */
@RestController
@RequestMapping("/v1/user")
@Api(value = "UserController", description="用户管理")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户V1
     * @param user 用户对象
     * @return 新生成的用户ID
     */
    @PutMapping("/v1")
    @ApiOperation(value= "添加用户V1")
    public int addUserV1(@RequestBody User user) {
        userMapper.insertUserV1(user);
        return user.getId();
    }

    /**
     * 添加用户V1
     * @param user 用户对象
     * @return 新生成的用户ID
     */
    @PutMapping("/v2")
    @ApiOperation(value= "添加用户V2")
    public int addUserV2(@RequestBody User user) {
        userMapper.insertUserV2(user);
        return user.getId();
    }

    @PutMapping("/v3")
    @ApiOperation(value = "批量添加用户")
    public List<Integer> addUserV3(@RequestBody List<User> userList) {
        userMapper.insertUserV3(userList);
        return userList.stream().map(User::getId).collect(Collectors.toList());
    }
}


