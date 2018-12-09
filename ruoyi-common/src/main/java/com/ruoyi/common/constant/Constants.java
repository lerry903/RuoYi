package com.ruoyi.common.constant;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String LOCAL_IP = "127.0.0.1";

    /**
     * 集合类初始容量
     */
    public static final int INITIAL_CAPACITY = 16;

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0" ;

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1" ;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success" ;

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout" ;

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error" ;

    /**
     * 自动去除表前缀
     */
    public static final String AUTO_REOMVE_PRE = "true" ;

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum" ;

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize" ;

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn" ;

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc" ;
}
