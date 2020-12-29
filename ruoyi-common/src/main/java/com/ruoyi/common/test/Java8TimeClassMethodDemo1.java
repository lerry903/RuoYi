package com.ruoyi.common.test;

import java.time.*;

/**
 * Date-Time API中所有类均生成不可变实例，他们是线程安全的，并且这些类不是提供公共的构造方法，也就是不能通过new的方式直接创建，
 * 需要采用工厂方法加以实例化
 */
public class Java8TimeClassMethodDemo1 {
    public static void main(String[] args) {
        //使用new方法创建Instance的实例对象
        Instant instant = Instant.now();//英国格林尼治标准时间
        LocalDate localDate = LocalDate.now();
        LocalTime localTimeNow = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        //将实例对象打印到控制台查看内容
        System.out.println("instant:"+instant);
        System.out.println("localDate:"+localDate);
        System.out.println("localTimeNow:"+localTimeNow);
        System.out.println("localDateTime:"+localDateTime);
        System.out.println("zonedDateTime:"+zonedDateTime);
    }
}
