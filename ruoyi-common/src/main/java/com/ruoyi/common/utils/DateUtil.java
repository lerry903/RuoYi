package com.ruoyi.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Administrator
 */
@Slf4j
public class DateUtil extends cn.hutool.core.date.DateUtil {

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath(){
        return format(new Date(), "yyyy/MM/dd");
    }
}
