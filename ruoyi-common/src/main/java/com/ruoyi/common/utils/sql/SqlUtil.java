package com.ruoyi.common.utils.sql;

import cn.hutool.core.util.StrUtil;

/**
 * sql操作工具类
 * 
 * @author ruoyi
 */
public class SqlUtil {

    private SqlUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
     */
    private static final String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value){
        if (StrUtil.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            return StrUtil.EMPTY;
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }
}
