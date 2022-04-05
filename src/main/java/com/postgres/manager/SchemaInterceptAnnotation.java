package com.postgres.manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * schema拦截器注解。修饰mapper接口类，用以区分访问的pg数据库schema
 *
 * @author elon
 * @since 2022-03-20
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SchemaInterceptAnnotation {
    /**
     * schema类型。取值范围：business, common
     *
     * @return
     */
    String schemaType() default "";
}
