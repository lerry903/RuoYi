package com.ruoyi.common.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Java8TimeClassMethodDemo3 {

    public static void main(String[] args) {
        //初始化2018年8月8日的LocalDate对象
        LocalDate localDate = LocalDate.of(2018, 8, 8);
        System.out.println("localDate:" + localDate);

        LocalTime localTime = LocalTime.of(20, 00);
        System.out.println("localTime:" + localTime);

        LocalDateTime localDateTime = LocalDateTime.of(2020, 11, 16, 23, 04, 01);
        System.out.println("localDateTime:"+localDateTime);


    }
}
