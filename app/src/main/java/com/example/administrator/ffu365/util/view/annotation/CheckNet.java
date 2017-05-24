package com.example.administrator.ffu365.util.view.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * ============================================================
 * 
 * project name : 
 * 
 * copyright ZENG HUI (c) 2015
 * 
 * author : HUI
 * 
 * version : 1.0
 * 
 * date created : On October, 2015
 * 
 * description : 注解检测网络
 * 
 * revision history :
 * 
 * ============================================================
 * 
 */
// 注解方法
@Target(ElementType.METHOD)
// 运行时检测
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {
    
}
