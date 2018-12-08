package com.ruoyi.common.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 已登录权限验证注解
 * User: LErry.li
 * Date: 2018-12-08
 * Time: 17:03
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginAuth {

}
