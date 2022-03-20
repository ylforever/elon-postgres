package com.postgres.manager;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * StatementHandler拦截器. 在prepare方法执行前拦截，修改sql语句，增加schema.
 *
 * @author elon
 * @since 2022-03-20
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class StatementHandlerInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatementHandlerInterceptor.class);

    /**
     * 业务数据分schema存储
     */
    private static final String BUSINESS_SCHEMA = "business";

    /**
     * 公共的配置信息(不分schema)
     */
    private static final String COMMON_SCHEMA = "common";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String mapperMethod = mappedStatement.getId();

        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();

        String mapperClass = mapperMethod.substring(0, mappedStatement.getId().lastIndexOf("."));
        Class<?> classType = Class.forName(mapperClass);

        SchemaInterceptAnnotation interceptAnnotation = classType.getAnnotation(SchemaInterceptAnnotation.class);
        String schemaType = interceptAnnotation.schemaType();
        String newSql = replaceSqlWithSchema(schemaType, sql, mapperMethod);

        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, newSql);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object object) {
        if (object instanceof StatementHandler) {
            return Plugin.wrap(object, this);
        } else {
            return object;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private String replaceSqlWithSchema(String schemaType, String originalSql, String mapperMethod){
        // 替换sql中的表名，加上schema
        if (BUSINESS_SCHEMA.equals(schemaType)) {
            String schema = SchemaHolder.get();
            return originalSql.replaceAll(" t_", " " + schema + ".t_");
        } else if (COMMON_SCHEMA.equals(schemaType)) {
            return originalSql.replaceAll(" t_", " " + COMMON_SCHEMA + ".t_");
        } else {
            LOGGER.error("Invalid SchemaInterceptAnnotation. mapperMethod:{}", mapperMethod);
            throw new IllegalArgumentException("Invalid SchemaInterceptAnnotation.");
        }
    }
}
