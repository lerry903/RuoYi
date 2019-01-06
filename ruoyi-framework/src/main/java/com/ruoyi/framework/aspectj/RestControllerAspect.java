package com.ruoyi.framework.aspectj;

import com.ruoyi.common.exception.DemoModeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *  演示模式拦截器
 * @author LErry.li
 * Date: 2018-06-16
 * Time: 16:31
 */
@Aspect
@Component
public class RestControllerAspect {

    private static final String METHOD = "POST";

    @Value("${ruoyi.demoMode}")
    private Boolean demoMode;


    /**
     * 环绕通知
     * @param joinPoint 连接点
     * @return 切入点返回值
     * @throws Throwable 异常信息
     */
    @Around("@within(org.springframework.stereotype.Controller) || @annotation(org.springframework.stereotype.Controller)")
    public Object controllerAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        if(demoMode && METHOD.equalsIgnoreCase(request.getMethod())){
            String methodName = this.getMethodName(joinPoint);
            this.demoMode(methodName);
        }
        return joinPoint.proceed();
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        String shortMethodNameSuffix = "(..)";
        if (methodName.endsWith(shortMethodNameSuffix)) {
            methodName = methodName.substring(0, methodName.length() - shortMethodNameSuffix.length());
        }
        return methodName;
    }

    /**
     * 演示模式下以下方法不允许操作
     * @param methodName 方法名
     */
    private void demoMode(String methodName){
        List<String> methodNameList = Arrays.asList("SysProfileController.resetPwd","SysProfileController.update");
        if(methodName.contains(".remove") || methodNameList.contains(methodName)){
            throw new DemoModeException();
        }
    }

}
