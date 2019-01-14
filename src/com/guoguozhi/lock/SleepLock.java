package com.guoguozhi.lock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 研究sleep方法会不会释放线程获取的锁
 */
public class SleepLock {

    // 锁
    private Object lock = new Object();

    public static void main(String args[])  {
        SleepLock sleepLock = new SleepLock();
        SleepThread sleepThread = sleepLock.new SleepThread();
        sleepThread.setName("sleepThread");
        NotSleepThread notSleepThread = sleepLock.new NotSleepThread();
        notSleepThread.setName("notSleepThread");
        sleepThread.start();
        try {
            Thread.sleep(1000); // 让main线程休眠1s，让sleepThread优先获取CPU资源被调度
            System.out.println(Thread.currentThread().getName() + " sleep end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notSleepThread.start();
    }

    private class SleepThread extends Thread {
        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " will take the lock. " + sdf.format(new Date()));
            synchronized (lock) { // 获得锁
                System.out.println(threadName + " has taken the lock." + sdf.format(new Date()));

                try {
                    Thread.sleep(5000); // 假如sleep释放了锁，那么①处可能还没执行，②处就获得了锁，而实际上需等待③处执行完毕，②处才可获取锁
                    System.out.println(threadName + " sleep end." + sdf.format(new Date())); // ①
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(threadName + " will release the lock." + sdf.format(new Date())); // ③
            }
            System.out.println(threadName + " has released the lock." + sdf.format(new Date()));
            /*
            //  此时锁已释放，那怎么知道sleep方法是否释放锁呢？
            try {
                Thread.sleep(5000);
                System.out.println(threadName + " sleep end." + sdf.format(new Date()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           */
        }
    }

    private class NotSleepThread extends Thread {
        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " will take the lock."+ sdf.format(new Date()));
            synchronized (lock) { // ②
                System.out.println(threadName + " has taken the lock."+ sdf.format(new Date()));

                System.out.println(threadName + " will release the lock."+ sdf.format(new Date()));
            }
            System.out.println(threadName + " has released the lock."+ sdf.format(new Date()));
        }
    }
}
