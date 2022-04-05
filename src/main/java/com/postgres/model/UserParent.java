package com.postgres.model;

import com.postgres.manager.MetaFieldAnnotation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserParent extends UserBase {
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "张三")
    @MetaFieldAnnotation(fieldCode = "name_1")
    private String name = "";
}
