package com.ruoyi.web.core.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.annotation.LoginAuth;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 已登录权限验证拦截器 备注：通过{@link LoginAuth}配合使用
 * @author LErry.li
 * Date: 2018-12-08
 * Time: 17:47
 */
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();

            if (clazz.isAnnotationPresent(LoginAuth.class) || method.isAnnotationPresent(LoginAuth.class)) {
                SysUser loginUser = ShiroUtils.getSysUser();
                return ObjectUtil.isNotNull(loginUser);
            }
        }
        return true;
    }
}
