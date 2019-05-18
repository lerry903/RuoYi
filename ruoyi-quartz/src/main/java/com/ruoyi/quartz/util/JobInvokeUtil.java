package com.ruoyi.quartz.util;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.SpringUtils;
import com.ruoyi.quartz.domain.SysJob;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 任务执行工具
 *
 * @author ruoyi
 */
class JobInvokeUtil {

    private JobInvokeUtil(){
        throw new IllegalStateException("Utility class");
    }
    /**
     * 执行方法
     *
     * @param sysJob 系统任务
     */
    public static void invokeMethod(SysJob sysJob) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Object bean = SpringUtils.getBean(sysJob.getJobName());
        String methodName = sysJob.getMethodName();
        String methodParams = sysJob.getMethodParams();

        invokeSpringBean(bean, methodName, methodParams);
    }

    /**
     * 调用任务方法
     *
     * @param bean         目标对象
     * @param methodName   方法名称
     * @param methodParams 方法参数
     */
    private static void invokeSpringBean(Object bean, String methodName, String methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (StrUtil.isNotEmpty(methodParams)) {
            Method method = bean.getClass().getDeclaredMethod(methodName, String.class);
            method.invoke(bean, methodParams);
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }
}
