package com.ruoyi.common.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class Java8TimeClassMethodDemo6 {

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.of(2020, Month.NOVEMBER, 17, 23,9,30);
//        System.out.println(localDateTime);

        LocalDate localDate = LocalDate.now();
        LocalDate localDate1 = localDate.plusDays(5);
        LocalDate localDate2 = localDate.plusDays(-5);
        //minusDays()方法运用的其实就是plusDays(负数)
        LocalDate localDate3 = localDate.minusDays(5);

        System.out.println(localDate2+" : "+localDate+" : "+localDate1+" : "+localDate3);
        System.out.println(localDate2 == localDate3);
        System.out.println(localDate2.isEqual(localDate3));
    }
}
