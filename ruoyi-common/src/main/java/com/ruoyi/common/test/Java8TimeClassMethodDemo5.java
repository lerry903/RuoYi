package com.ruoyi.common.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 为localDateTime添加时区信息
 */
public class Java8TimeClassMethodDemo5 {

    public static void main(String[] args) {
        //1.封装自定义时区信息
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 11, 22, 51, 30);
        //2.添加时区信息到对象中，使用atZone()方法
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println("Asia/Shanghai current time :"+zonedDateTime);
        //3.通过时区查看当前日期，通过withZoneSameInstant()方法即可更改
        ZonedDateTime zonedDateTime1 = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        System.out.println("Tokyo's time :"+ zonedDateTime1);
    }
}
