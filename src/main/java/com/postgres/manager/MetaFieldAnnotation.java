package com.postgres.manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元数据字段注解，用于修饰基础模型字段, 指定属性映射的字段编码.
 * 使用FastJSON的@JSONField(name=字段编码)能达到同样的目的，不使用是考虑@JSONField会影响JSON序列化和反序列化的结果。加了该注解后
 * 基础模型就不能再像普通对象那样做JSON转换。基础模型的应用面很广，尽量不改变其基本行为。
 *
 * @author elon
 * @since 2022-04-05
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaFieldAnnotation {
    String fieldCode() default "";
}
