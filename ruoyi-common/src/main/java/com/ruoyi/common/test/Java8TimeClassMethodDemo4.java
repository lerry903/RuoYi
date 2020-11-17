package com.ruoyi.common.test;

import java.time.ZoneId;
import java.util.Set;

public class Java8TimeClassMethodDemo4 {
    public static void main(String[] args) {
        // 获取所有的时区信息
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        for (String availableZoneId : availableZoneIds) {
            System.out.println(availableZoneId);
        }

        // 查看当前系统默认的时区信息 ->中国
        ZoneId systemDefault = ZoneId.systemDefault();
        System.out.println(systemDefault);


    }
}
