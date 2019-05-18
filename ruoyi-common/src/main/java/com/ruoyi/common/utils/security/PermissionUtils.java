package com.ruoyi.common.utils.security;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.constant.PermissionConstants;
import com.ruoyi.common.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * permission 工具类
 *
 * @author ruoyi
 */
@Slf4j
public class PermissionUtils {

    private PermissionUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 查看数据的权限
     */
    private static final String VIEW_PERMISSION = "no.view.permission";

    /**
     * 创建数据的权限
     */
    private static final String CREATE_PERMISSION = "no.create.permission";

    /**
     * 修改数据的权限
     */
    private static final String UPDATE_PERMISSION = "no.update.permission";

    /**
     * 删除数据的权限
     */
    private static final String DELETE_PERMISSION = "no.delete.permission";

    /**
     * 导出数据的权限
     */
    private static final String EXPORT_PERMISSION = "no.export.permission";

    /**
     * 其他数据的权限
     */
    private static final String PERMISSION = "no.permission";

    /**
     * 权限错误消息提醒
     *
     * @param permissionsStr 错误信息
     * @return 提示信息
     */
    public static String getMsg(String permissionsStr) {
        String permission = StringUtils.substringBetween(permissionsStr, "[", "]");
        String msg = MessageUtils.message(PERMISSION, permission);
        if (StrUtil.endWithIgnoreCase(permission, PermissionConstants.ADD_PERMISSION)) {
            msg = MessageUtils.message(CREATE_PERMISSION, permission);
        } else if (StrUtil.endWithIgnoreCase(permission, PermissionConstants.EDIT_PERMISSION)) {
            msg = MessageUtils.message(UPDATE_PERMISSION, permission);
        } else if (StrUtil.endWithIgnoreCase(permission, PermissionConstants.REMOVE_PERMISSION)) {
            msg = MessageUtils.message(DELETE_PERMISSION, permission);
        } else if (StrUtil.endWithIgnoreCase(permission, PermissionConstants.EXPORT_PERMISSION)) {
            msg = MessageUtils.message(EXPORT_PERMISSION, permission);
        } else if (StrUtil.endWithAny(permission,
                PermissionConstants.VIEW_PERMISSION, PermissionConstants.LIST_PERMISSION)) {
            msg = MessageUtils.message(VIEW_PERMISSION, permission);
        }
        return msg;
    }

    /**
     * 返回用户属性值
     *
     * @param property 属性名称
     * @return 用户属性值
     */
    public static Object getPrincipalProperty(String property) {
        Subject subject = SecurityUtils.getSubject();
        if (ObjectUtil.isNotNull(subject)) {
            Object principal = subject.getPrincipal();
            try {
                BeanInfo bi = Introspector.getBeanInfo(principal.getClass());
                for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
                    if (pd.getName().equals(property)) {
                        return pd.getReadMethod().invoke(principal, (Object[]) null);
                    }
                }
            } catch (Exception e) {
                log.error("Error reading property [{}] from principal of type [{}]", property,
                        principal.getClass().getName());
            }
        }
        return null;
    }
}
