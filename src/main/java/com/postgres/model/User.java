package com.postgres.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户模型定义。
 */
@ApiModel(value = "用户模型")
@Data
public class User {

    /**
     * ID
     */
    @ApiModelProperty(value = "id", example = "-1")
    private int id = -1;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "张三")
    private String name = "";
}
