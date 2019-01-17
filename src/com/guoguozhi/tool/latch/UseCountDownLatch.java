package com.guoguozhi.tool.latch;

import java.util.concurrent.CountDownLatch;

/**
 * 测试CountDownLatch
 */
public class UseCountDownLatch {

    // 初始化latch(门闩)
    private static final CountDownLatch latch =  new CountDownLatch(6);

    // 初始化线程
    private static class InitThread extends Thread{
        @Override
        public void run() {
            //Thread.currentThread().setName("InitThread");
            System.out.println(Thread.currentThread().getName() + " is ready to do some initial work.");
            //latch.countDown();
            System.out.println(Thread.currentThread().getName() + " latch 的计数器值: " + latch.getCount());
            for (int i = 0; i < 2; i++) {
                System.out.println(Thread.currentThread().getName() + " continue does it's work.");
            }
            System.out.println(Thread.currentThread().getName() + " has finished it's initial work.");
            // 只有放在最后面才能保证创建的6个线程都执行结束以后，main线程后面的代码才会被调度，有时需要特别注意代码的位置
            latch.countDown();
        }
    }

    public static void main(String args[]) {
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
    }
}
