package com.dazhou.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验
 * @Target 表示这个注解可以注解在类或者方法上面
 * METHOD 用于描述方法，PACKAGE 用来描述包  TYPE:用于描述类、接口(包括注解类型) 或enum声明
 * @Retention(RetentionPolicy.RUNTIME)  该注解的内容在运行时还会保留 此注解用于表示当前注解的生命周期
 *
 * @author dazhou
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 有任何一个角色
     *
     * @return
     */
    String[] anyRole() default "";

    /**
     * 必须有某个角色
     *
     * @return
     */
    String mustRole() default "";

}

