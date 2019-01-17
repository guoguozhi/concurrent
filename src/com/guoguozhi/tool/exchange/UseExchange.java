package com.guoguozhi.tool.exchange;

import com.guoguozhi.tools.SleepTools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

public class UseExchange {

    public static void main(String[] args) {

        // 交换器
        final Exchanger<Set<String>> exchanger = new Exchanger<Set<String>>();



        // 线程A
        new Thread(new Runnable() {

            //  待交换的数据
            private Set<String> setA = new HashSet<>();

            @Override
            public void run() {
                if (! Thread.currentThread().isInterrupted()) {
                    Thread.currentThread().setName("threadA");
                    System.out.println("A 即将准备待交换的数据.");
                    this.setA.add("张三");
                    this.setA.add("李四");
                    this.setA.add("王五");
                    System.out.println("A 准备完成待交换的数据.");
                    try {
                        // 谁先执行到此处谁就先候着另一个线程的到来，然后才能交换数据，等交换完数据以后，后面的代码才会执行~
                        exchanger.exchange(this.setA);
                        System.out.println("A 完成数据交换.");
                    } catch (InterruptedException e) {
                        System.out.println("when change A's data, A is interrupted.");
                        e.printStackTrace();
                    }

                    System.out.println("setA:" + setA.toString());
                }
            }
        }).start();

        // 线程B
        new Thread(new Runnable() {

            //  待交换的数据
            private Set<String> setB = new HashSet<>();

            @Override
            public void run() {
                if (!Thread.currentThread().isInterrupted()) {
                    Thread.currentThread().setName("threadA");
                    System.out.println("B 即将准备待交换的数据.");
                    this.setB.add("zhangsan");
                    this.setB.add("lisi");
                    this.setB.add("wangwu");
                    System.out.println("B 准备完成待交换的数据.");
                    try {
                        SleepTools.sleepForSeconds(10);
                        exchanger.exchange(this.setB);
                        System.out.println("B 完成数据交换.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("setB:" + setB.toString());
                }
            }
        }).start();

    }

}
