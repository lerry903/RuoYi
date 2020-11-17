package com.ruoyi.common.test;

import lombok.Synchronized;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建10个线程，将字符串“2018-12-12 12:12:12”转换为Date对象后打印到控制台
 */
public class SimpleDateFormatDemo implements AutoCloseable{
    final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Date date = SIMPLE_DATE_FORMAT.parse("2018-12-12 12:12:12");
                        System.out.println(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void close() throws Exception {

    }
}
