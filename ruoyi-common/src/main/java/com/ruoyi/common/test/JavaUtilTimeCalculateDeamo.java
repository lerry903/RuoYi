package com.ruoyi.common.test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class JavaUtilTimeCalculateDeamo {
    public static void main(String[] args) {
        Date date = new Date();
        long d = date.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.set(1995,11,16);
        Date calendarTime = calendar.getTime();
        long c = calendarTime.getTime();

        long intervalDay = (d - c) / 1000 / 60 / 60 / 24;
        System.out.println("intervalDay: "+intervalDay);

        //使用java8新版本的API來完成題目中的要求
        long days = ChronoUnit.DAYS.between(LocalDate.of(1995, 12, 16), LocalDate.now());
        System.out.println("days:"+days);
    }
}
