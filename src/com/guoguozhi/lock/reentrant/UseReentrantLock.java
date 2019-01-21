package com.guoguozhi.lock.reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UseReentrantLock {
    //  可重入锁
    private static ReentrantLock reentrantLock = new ReentrantLock();
    //  条件
    private static Condition condition = reentrantLock.newCondition();

    static class UseRunnable implements Runnable {
        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                this.test();
            }
        }

        //  同synchronized关键字的效果
        private void test() {
            reentrantLock.lock();
            //  1 has got the lock.0 1 是否在排队 false
            //  可以从侧面反映了当前线程获取了锁，不然进入不了这段代码
            //System.out.println(Thread.currentThread().getName() + " has got the lock." + reentrantLock.getQueueLength() + " " + reentrantLock.getHoldCount() + " 是否在排队 " + reentrantLock.hasQueuedThread(Thread.currentThread()));
                try {
                condition.await();
                for (int i = 0; i < 5; i++) {
                    System.out.println("the current thread name is " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new UseRunnable(), (i+1) + "");
            thread.start();
        }
    }
}

/*
lock.lock();
try {
    //  业务代码
} finally {
    lock.unlock();
}
 */