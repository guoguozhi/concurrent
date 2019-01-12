package com.guoguozhi.safeend;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SafeEndRunnable {

    private static class UseRunnable implements Runnable{

        @Override
        public void run() {
            // 获取当前线程
            Thread currentThread = Thread.currentThread();
            // 日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            while (!currentThread.isInterrupted()) {// 判断是否中断
                System.out.println(sdf.format(new Date()) + " " + currentThread.getName() + " interrupt flag is " + currentThread.isInterrupted());
            }
            System.out.println(sdf.format(new Date()) + " " + currentThread.getName() + " interrupt flag is " + currentThread.isInterrupted());
        }
    }

    public static void main(String args[]) throws InterruptedException {
        // 创建useRunnable
        UseRunnable useRunnable = new UseRunnable();
        // 将useRunnable包装成Thread，并指定线程名称
        Thread myThread = new Thread(useRunnable, "useRunnable");
        // 启动线程
        myThread.start();
        // 让主线程休眠200毫秒，就阻塞了后面的代码执行
        Thread.sleep(200);
        // 中断myThread
        myThread.interrupt();
    }
}
