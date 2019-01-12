package com.guoguozhi.safeend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class SafeEndCallable {

    private static class UseCallable implements Callable<String> {
        @Override
        public String call() {
            // 获取当前线程
            Thread currentThread = Thread.currentThread();
            // 日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            while(!currentThread.isInterrupted()) { // 需在之前的条件上加上!isInterrupted()判断
                // 业务代码
                System.out.println(sdf.format(new Date()) + " " + currentThread.getName() + " interrupt flag is " + currentThread.isInterrupted());
            }
            System.out.println(sdf.format(new Date()) + " " + currentThread.getName() + " interrupt flag is " + currentThread.isInterrupted());
            return "Callable Result";
        }
    }

    public static void main(String args[]) throws InterruptedException {
        // 创建useCallable对象
        UseCallable useCallable = new UseCallable();
        // 将useCallable对象包装成FutureTask对象
        FutureTask<String> futureTask = new FutureTask<String>(useCallable);
        // 将futureTask包装成Thread对象
        Thread  thread = new Thread(futureTask, "useCallable");
        // 启动线程
        thread.start();
        // 休眠200ms
        Thread.sleep(200);
        // 中断线程
        thread.interrupt();
    }
}
