package com.guoguozhi.tool.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//  研究屏障线程barrierAction的执行时机~
public class UseBarrierAction {

    private static class MyThread implements Runnable {
        private CyclicBarrier barrier; // 屏障

        public MyThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                try {
                    this.barrier.await();
                    System.out.println(Thread.currentThread().getName() + " continue does it's work.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(10, new Thread(new Runnable() { // 屏障线程
            @Override
            public void run() {
                if (!Thread.currentThread().isInterrupted()) {
                    System.out.println("barrierAction is doing it's work.");
                }
            }
        })); // 屏障打开时先执行屏障线程

        for (int i = 0; i < 10; i++) {
            (new Thread(new MyThread(barrier))).start();
        }

    }

}
