package com.postgres.constant;

import lombok.Getter;

/**
 * 定义常用的postgres数据字段类型
 *
 * @author elon
 * @since 2022-09-03
 */
public enum EnumPGDBDataType {
    TEXT("text"),

    DOUBLE("double"),

    INTEGER("integer");

    @Getter
    private String dataType;

    EnumPGDBDataType(String dataType) {
        this.dataType = dataType;
    }
}
