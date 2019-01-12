package com.guoguozhi.safeend;

import java.text.SimpleDateFormat;
import java.util.Date;

// ? 如何将线程设计为能安全的结束线程
public class SafeEndThread {
    private static class UseThread extends Thread{

        public UseThread(String name){
            super(name);
        }

        @Override
        public void run() {
            // 打印当前线程的名称
            String threadName = Thread.currentThread().getName();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            // 在run方法中，在合适的位置加上isInterrupted()是否中断了的判断
            while(!isInterrupted()){ // 未中断时
                System.out.println(sdf.format(new Date()) + " " + threadName + " interrupt flag is " + isInterrupted());
            }
            System.out.println(sdf.format(new Date()) + " " + threadName + " interrupt flag is " + isInterrupted());
        }
    }

    // Thread.sleep(1000) 表示将当前线程睡眠1s，这个方法在哪里执行，就睡眠那个线程

    public static void main(String args[]) throws InterruptedException {
        // 创建线程
        UseThread useThread = new UseThread("UseThread");
        // 启动线程
        useThread.start();
        // 让主线程休眠200毫秒，休眠期间，代码处于阻塞状态，后面的代码要等休眠结束后才能执行，相当于OC中的dispatch_after方法
        Thread.sleep(200);
        // 中断useThread
        useThread.interrupt();
    }
}
