package com.postgres.model;

import com.postgres.manager.MetaFieldAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户模型定义。
 */
@ApiModel(value = "用户模型")
@Getter
@Setter
public class User extends UserParent {
    @ApiModelProperty(value = "年龄")
    @MetaFieldAnnotation(fieldCode = "age_1")
    private int age = 0;
}
