package com.guoguozhi.lock;

public class NotifyLock {

    // 锁
    private static final Object lock = new Object();

    public static void main(String args[]) {
        NotifyThread notifyThread = new NotifyThread();
        notifyThread.setName("notifyThread");
        NotNotifyThread notNotifyThread = new NotNotifyThread();
        notNotifyThread.setName("notNotifyThread");
        notifyThread.start();
        try {
            Thread.sleep(800);
            System.out.println(Thread.currentThread().getName() + " sleep end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notNotifyThread.start();
    }

    private static class NotifyThread extends Thread{
        @Override
        public void run() {
            if (!isInterrupted()) {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " will take the lock " + lock.toString());
                synchronized (lock) {
                    System.out.println(threadName + " has taken the lock " + lock.toString());
                    lock.notify();
                    System.out.println(threadName + " will release the lock " + lock.toString());
                }
                System.out.println(threadName + " has released the lock " + lock.toString());
            }
        }
    }

    private static class NotNotifyThread extends Thread{
        @Override
        public void run() {
            if (!isInterrupted()) {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " will take the lock " + lock.toString());
                synchronized (lock) {
                    System.out.println(threadName + " has taken the lock " + lock.toString());

                    System.out.println(threadName+ " will release the lock " + lock.toString());
                }
                System.out.println(threadName + " has released the lock " + lock.toString());
            }
        }
    }
}
