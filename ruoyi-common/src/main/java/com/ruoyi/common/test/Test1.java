package com.ruoyi.common.test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

public class Test1 extends ThreadPoolExecutor{
    public Test1(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                 BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,
                 RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public static void main(String[] args) {
        //Map没有实现迭代器接口
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1,"one");
        map.put(2,"two");

        List<String> strings = new ArrayList<String>();
        strings.add("first");
        strings.add("second");
        strings.add("third");

        for (Iterator<String> iter = strings.iterator(); iter.hasNext();) {
            String s = iter.next();
            System.out.println(s);
        }
        for (int i = 0; i < 10; i++) {
            int tlr = ThreadLocalRandom.current().nextInt(100) % 100;
            System.out.println("tlr"+i+": "+tlr);
            System.out.println(Math.abs(new Random().nextInt(3)) % 3);
        }

        BigDecimal bigDecimal = new BigDecimal("1.00");
        bigDecimal = bigDecimal.subtract(new BigDecimal("0.1"));

    }
}
