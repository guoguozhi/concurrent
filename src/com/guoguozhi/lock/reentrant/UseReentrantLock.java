package com.guoguozhi.lock.reentrant;

import java.util.concurrent.locks.ReentrantLock;

public class UseReentrantLock {
    private static ReentrantLock reentrantLock = new ReentrantLock();

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
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("the current thread name is " + Thread.currentThread().getName());
                }
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