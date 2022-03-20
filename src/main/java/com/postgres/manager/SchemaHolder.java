package com.postgres.manager;

/**
 * Schema持有类. 用于在异步线程或者跨多个方法传递schema信息
 *
 * @author elon
 * @since 2022-03-19
 */
public class SchemaHolder {
    private static ThreadLocal<String> schema = new ThreadLocal<>();

    public static void set(String sch) {
        schema.set(sch);
    }

    public static String get() {
        return schema.get();
    }

    public static void clear() {
        schema.remove();
    }
}
