package com.ruoyi.common.test;

import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;

/**
 * Date-Time API中的所有类均生成不可变实例，它们是线程安全的，并且这些类不提供公共的构造函数，也就是说没办法通过new的方式直接创建，需要采用工厂
 * 方法加以实例化
 */
public class Java8TimeClassMethodDemo2 {
    public static void main(String[] args) {
        //使用now方法创建Year类型的实例对象
        Year year = Year.now();
        //使用now方法创建YearMonth类型的实例对象
        YearMonth yearMonth = YearMonth.now();
        //使用now方法创建MonthDay类型的实例对象
        MonthDay monthDay = MonthDay.now();

        System.out.println("year:"+year);
        System.out.println("YearMonth:"+yearMonth);
        System.out.println("monthDay:"+monthDay);
    }
}
