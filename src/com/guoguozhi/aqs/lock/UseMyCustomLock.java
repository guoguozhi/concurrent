package com.guoguozhi.aqs.lock;

import com.guoguozhi.tools.SleepTools;

import java.util.concurrent.locks.ReentrantLock;

public class UseMyCustomLock {

    public void test() {
        //final ReentrantLock lock = new ReentrantLock();
        final MyCustomLock lock = new MyCustomLock();

        class Worker extends Thread {
            @Override
            public void run() {
                if (!isInterrupted()) {
                    while (true) {
                        /*
                        reentrantLock.lock();
                        try {
                            System.out.println(Thread.currentThread().getName() + "开始");
                            System.out.println(Thread.currentThread().getName() + "... ...");
                            SleepTools.sleepForSeconds(1);
                            System.out.println(Thread.currentThread().getName());
                            SleepTools.sleepForSeconds(1);
                        } finally {
                            System.out.println(Thread.currentThread().getName() + "... ...");
                            System.out.println(Thread.currentThread().getName() + "结束");
                            reentrantLock.unlock();
                        }
                        SleepTools.sleepForSeconds(2);
                        */
                        lock.lock();
                        try {
                            System.out.println(Thread.currentThread() + "开始");
                            System.out.println(Thread.currentThread() + "... ...");
                            SleepTools.sleepForSeconds(1);
                            System.out.println(Thread.currentThread().getName());
                            SleepTools.sleepForSeconds(1);
                        } finally {
                            System.out.println(Thread.currentThread() + "... ...");
                            System.out.println(Thread.currentThread() + "结束");
                            lock.unlock();
                        }
                        SleepTools.sleepForSeconds(2);
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }

        // 主线程每隔1秒换行
        for (int i = 0; i < 10; i++) {
            SleepTools.sleepForSeconds(1);
            System.out.println();
        }

        //  打印是同步打印的
    }

    public static void main(String[] args) {
        UseMyCustomLock useMyCustomLock = new UseMyCustomLock();
        useMyCustomLock.test();
    }
}
