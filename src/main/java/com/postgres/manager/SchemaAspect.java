package com.postgres.manager;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Schema切面, 提取header头中的schema保存到SchemaHolder中
 *
 * @author elon
 * @since 2022-03-20
 */
@Aspect
@Component
@Order(9999)
public class SchemaAspect {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) "
            + "|| @annotation(org.springframework.web.bind.annotation.PostMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    void schema() {

    }

    /**
     * 从请求头提取
     *
     * @param joinPoint
     */
    @Before("schema()")
    public void setSchema(JoinPoint joinPoint) {
        String schema = getSchemaFromHeader();
        SchemaHolder.set(schema);
    }

    @After("schema()")
    public void clearSchema(JoinPoint joinPoint) {
        SchemaHolder.clear();
    }

    /**
     * 从请求头中后去schema信息
     *
     * @return schema
     */
    private String getSchemaFromHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String schema = request.getHeader("schema");
        return schema;
    }
}
