package com.ruoyi.common.utils;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Administrator
 */
@Slf4j
public class DateUtil extends cn.hutool.core.date.DateUtil {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        Date result = null;
        if (ObjectUtil.isNotNull(str)) {
            try {
                result = DateUtils.parseDate(str.toString(), parsePatterns);
            } catch (ParseException e) {
                log.error("日期类型字符串转日期失败", e);
            }
        }
        return result;
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath(){
        return format(new Date(), "yyyy/MM/dd");
    }
}
