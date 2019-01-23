package com.guoguozhi.lock.readwrite;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseReentrantReadWriteLock {
    private static final ReentrantReadWriteLock lock =  new ReentrantReadWriteLock();
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    static class UseReadRunnable implements Runnable {
        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                readLock.lock();
                try {
                    long start = System.currentTimeMillis();
                    System.out.println(Thread.currentThread().getName() + "读线程");
                    try {
                        Thread.sleep(3000);
                        System.out.println(Thread.currentThread().getName() + "读耗时:" + (System.currentTimeMillis() - start) + "毫秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    readLock.unlock();
                }
            }
        }
    }

    static class UseReadRunnable2 implements Runnable {
        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                lock2.lock();
                try {
                    long start = System.currentTimeMillis();
                    System.out.println(Thread.currentThread().getName() + "读线程-可重入锁");
                    try {
                        Thread.sleep(3000);
                        System.out.println(Thread.currentThread().getName() + "读耗时-可重入锁:" + (System.currentTimeMillis() - start) + "毫秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock2.unlock();
                }
            }
        }
    }

    static class UseWriteRunnable implements Runnable {
        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                writeLock.lock();
                try {
                    long start = System.currentTimeMillis();
                    System.out.println(Thread.currentThread().getName() + "写线程");
                    try {
                        Thread.sleep(3000);
                        System.out.println(Thread.currentThread().getName() + "写耗时:" + (System.currentTimeMillis() - start) + "毫秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    writeLock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        /*
        // 读写锁的读读非互斥
        for (int i = 0; i < 5; i++) {
            UseWriteRunnable useWriteRunnable = new UseWriteRunnable();
            Thread writeThread = new Thread(useWriteRunnable, "writeThread" + (i + 1));
            for (int j = 0; j < 5; j++) {
                UseReadRunnable useReadRunnable = new UseReadRunnable();
                Thread readThread = new Thread(useReadRunnable, "readThread" + (j + 1));
                readThread.start();
            }
            writeThread.start();
        }
        */

        /*
        //  读写锁的读写互斥
        for (int i = 0; i < 10; i++) {
            UseWriteRunnable useWriteRunnable = new UseWriteRunnable();
            Thread writeThread = new Thread(useWriteRunnable, "writeThread" + (i + 1));
            UseReadRunnable useReadRunnable = new UseReadRunnable();
            Thread readThread = new Thread(useReadRunnable, "readThread" + ( + 1));
            writeThread.start();
            readThread.start();
        }
        */

        /*
        //  读写锁写写互斥
        for (int i = 0; i < 10; i++) {
            UseWriteRunnable useWriteRunnable = new UseWriteRunnable();
            Thread writeThread = new Thread(useWriteRunnable, "writeThread" + (i + 1));
            writeThread.start();
        }
        */

        for (int i = 0; i < 10; i++) {
            UseReadRunnable2 useReadRunnable2 = new UseReadRunnable2();
            Thread readThread = new Thread(useReadRunnable2, "readThread" + ( + 1));
            readThread.start();
        }

    }
}
