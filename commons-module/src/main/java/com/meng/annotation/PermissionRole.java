package com.meng.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解处理角色权限定义
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionRole
{
    /**
     * 权限字符串，逗号分割，如：hotel_admin,admin
     */
    String roles();
}