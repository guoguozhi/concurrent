package com.guoguozhi.tool.latch;

import com.guoguozhi.tools.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 * 测试CountDownLatch 倒计时门闩
 */
public class UseCountDownLatch {

    // 初始化latch(门闩)
    private static final CountDownLatch latch =  new CountDownLatch(6);

    // 初始化线程
    private static class InitThread extends Thread{
        @Override
        public void run() {
            if (!isInterrupted()) {
                Thread.currentThread().setName("InitThread");
                System.out.println(Thread.currentThread().getName() + " is ready to do some initial work. latch=" + latch.getCount());
                latch.countDown(); // 只会将计数器-1
                System.out.println(Thread.currentThread().getName() + " latch 的计数器值: " + latch.getCount());
                for (int i = 0; i < 2; i++) {
                    System.out.println(Thread.currentThread().getName() + " continue does it's work. latch=" + latch.getCount());
                }
                System.out.println(Thread.currentThread().getName() + " has finished it's initial work. latch=" + latch.getCount());
                // 只有放在最后面才能保证创建的6个线程都执行结束以后，main线程后面的代码才会被调度，有时需要特别注意代码的位置
                //latch.countDown();
            }
        }
    }

    // 业务线程
    private static class BusinessThread extends Thread{
        @Override
        public void run() {
            if (!isInterrupted()) {
                Thread.currentThread().setName("businessThread");
                System.out.println(Thread.currentThread().getName() + " is ready to do it's business. latch=" + latch.getCount());
                try {
                    latch.await(); //  需要等待计数器的值变为0，后面的代码才会执行，如果一个线程将当前线程给interrupt了，那它也是可以继续执行的
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " has been interrupted. latch=" + latch.getCount());
                    e.printStackTrace();
                }
                for (int i = 0; i < 3; i++) {
                    System.out.println(Thread.currentThread().getName() + " continue does it's business work. latch=" + latch.getCount());
                }
                System.out.println(Thread.currentThread().getName() + " has finished it's business. latch=" + latch.getCount());
            }
        }
    }

    public static void main(String args[]) {
        /*
        for (int i = 0; i < 6; i++) {
            InitThread initThread = new InitThread();
            initThread.setName("initThread-"+(i+1));
            initThread.start();
        }
        try {
            latch.await(); // 阻塞方法
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " does it's work.");
        */

        /*
        // 先启动initThread
        InitThread initThread = new InitThread();
        initThread.start();

        BusinessThread businessThread = new BusinessThread();
        businessThread.start();

        System.out.println(Thread.currentThread().getName() + " will sleep for 10 seconds");
        SleepTools.sleepForSeconds(10);
        System.out.println(Thread.currentThread().getName() + " has finished sleep for 10 seconds");

        //  会唤醒businessThread之前阻塞的方法，而代码并不会从头开始执行，仅仅是唤醒
        businessThread.interrupt();
        */

        //  单独的初始化线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("singleInitThread");
                System.out.println(Thread.currentThread().getName() + " is ready to do it's step one. latch=" + latch.getCount());
                SleepTools.sleepForMilliseconds(1);
                System.out.println(Thread.currentThread().getName() + " has finished it's step one. latch=" + latch.getCount());
                latch.countDown(); // 计数器扣减1
                System.out.println(Thread.currentThread().getName() + " is ready to do it's step two. latch=" + latch.getCount());
                SleepTools.sleepForMilliseconds(1);
                System.out.println(Thread.currentThread().getName() + " has finished it's step two. latch=" + latch);
                latch.countDown(); // 计数器扣减1
            }
        }).start();

        (new BusinessThread()).start();

        for (int i = 0; i < 4; i++) {
            (new InitThread()).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " continue does it's work. latch=" + latch.getCount());
    }
}
