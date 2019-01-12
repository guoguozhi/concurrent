package com.guoguozhi.thread;

// 守护线程

public class DaemonThread {

    private static class  UseThread extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) { // 未被中断
                    // 业务代码
                    System.out.println(Thread.currentThread().getName() + " I am UseThread extends Thread.");
                }
            } finally {
                // 当不打扰守护线程时(不要interrupt守护线程)，守护线程的finally语句块不会执行，否则finally语句块也会执行
                System.out.println("I am UseThread finally");
            }
        }
    }

    // main方法中的对象创建处于main线程，如果有线程对象的创建且该线程设置成了守护线程，那么它守护的是
    // main线程，随着main线程的销毁而销毁，当main方法执行结束时，main线程销毁
    public static void main(String args[]) throws InterruptedException {
        // 创建线程
        UseThread useThread = new UseThread();
        // 设置成main线程的守护线程，随main线程销毁而销毁
         useThread.setDaemon(true); // 如果将useThread设置成main线程的守护线程注释掉，那么它是和操作系统的线程映射
        // 不会随main线程的销毁而销毁，而是会继续执行，除非将useThread线程中断(interrupt)
        // 设置线程名称
        useThread.setName("useThread");
        // 启动线程
        useThread.start();
        // 将主线程休眠2s
        Thread.sleep(2000);
        // 中断useThread
        //useThread.interrupt();
    }
}
