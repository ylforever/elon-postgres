package com.postgres.constant;

import com.elon.base.util.ListUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 数组类型转换器. 用于将Java中的List转换为数据库识别的数组类型。
 *
 * @param <T> 元素类型
 * @author elon
 * @since 2022-09-03
 */
@MappedJdbcTypes(JdbcType.ARRAY)
public class ElonArrayTypeHandler<T> extends BaseTypeHandler<List<T>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<T> values, JdbcType jdbcType) throws SQLException {
        if (ListUtil.isEmpty(values)) {
            return;
        }

        T firstElement = values.get(0);
        EnumPGDBDataType dataType = null;

        // 常用的字符串，整型，小数类型做处理. 也可以新增其它类型
        if (firstElement instanceof String) {
            dataType = EnumPGDBDataType.TEXT;
        } else if (firstElement instanceof Integer) {
            dataType = EnumPGDBDataType.INTEGER;
        } else if (firstElement instanceof Double) {
            dataType = EnumPGDBDataType.DOUBLE;
        } else {
            return;
        }

        Connection connection = preparedStatement.getConnection();
        Object[] objects = values.toArray();
        Array array = connection.createArrayOf(dataType.getDataType(), objects);
        preparedStatement.setArray(i, array);
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public List<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
