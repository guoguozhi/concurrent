package com.guoguozhi.practice;

import java.util.concurrent.atomic.AtomicInteger;

public class BinzAtomicInteger {
    private AtomicInteger atomicInteger = null;

    public BinzAtomicInteger() {
        super();
        this.atomicInteger = new AtomicInteger(0);
    }

    public BinzAtomicInteger(int initialValue) {
        super();
        this.atomicInteger = new AtomicInteger(initialValue);
    }

    // 仅用get + compareAndSet
    //  模仿AtomicInteger的实现
    public int incrementAndGet() {
        int value = 0;
        for (;;) {
            int initialValue = this.atomicInteger.get();
            boolean success = this.atomicInteger.compareAndSet(initialValue, initialValue+1);
            if (success) {
                value = this.atomicInteger.get();
                break;
            }
        }
        return value;
    }

    public int getAndIncrement() {
        int value = 0;
        for (;;) {
            int initialValue = this.atomicInteger.get();
            boolean success = this.atomicInteger.compareAndSet(initialValue, initialValue+1);
            if (success) {
                value = initialValue;
                break;
            }
        }
        return value;
    }

    static class UseRunnable implements  Runnable{
        BinzAtomicInteger binzAtomicInteger = null;
        public UseRunnable(BinzAtomicInteger binzAtomicInteger) {
            super();
            this.binzAtomicInteger = binzAtomicInteger;
        }
        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + "-开始递增");
                System.out.println(Thread.currentThread().getName() + "-value: "+ this.binzAtomicInteger.getAndIncrement());
                System.out.println(Thread.currentThread().getName() + "-结束递增");
            }
        }
    }

    public static void main(String[] args) {
        BinzAtomicInteger binzAtomicInteger = new BinzAtomicInteger(10);
        for (int i = 0; i < 10; i++) {
            Thread myThread = new Thread(new UseRunnable(binzAtomicInteger), (i+1) + "");
            myThread.start();
//            try {
//                myThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}

// ? 如何保证多个创建的线程顺序执行~

