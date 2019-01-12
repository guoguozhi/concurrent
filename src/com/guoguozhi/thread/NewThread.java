package com.guoguozhi.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// 创建线程的几种方式

public class NewThread {

    // 通过Thread类
    private static class UseThread extends Thread{
        @Override
        public void run() {
            System.out.println("I am extends Thread.");
        }
    }

    // 通过Runnable接口
    private static class UseRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println("I am implements Runnable.");
        }
    }

    // 通过Callable接口
    private static class UseCallable implements Callable<String>{
        @Override
        public String call() {
            System.out.println("I am implements Callable<String>.");
            return "Callable Result";
        }
    }

    // Runnable接口和Callable接口都是对Thread类实现线程的补充，因Java中类是单继承多实现的

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 方式一
        // 创建Thread对象，调用start方法
        UseThread useThread = new UseThread();
        useThread.start();

        // 不推荐使用的方法，如果万不得以，还是可以继续使用的，请尽量不要使用
        //useThread.stop();
        //useThread.resume();
        //useThread.suspend();

        // 方式二
        // 将Runnable对象包装(wrap)成Thread对象
        UseRunnable useRunnable = new UseRunnable();
        Thread runnableWrappedThread = new Thread(useRunnable);
        runnableWrappedThread.start();

        // 方式三
        // 创建实现了Callable接口的对象
        UseCallable useCallable = new UseCallable();
        // 将Callable包装成FutureTask，然后将FutureTask包装成Thread来使用，FutureTask是一个实现了Runnable接口的类

        // 将useCallable包装成FutureTask
        FutureTask<String> futureTask = new FutureTask<String>(useCallable);
        // 将futureTask包装成thread
        Thread callableWrappedThread = new Thread(futureTask);
        callableWrappedThread.start();
        System.out.println(futureTask.get());
    }
}
