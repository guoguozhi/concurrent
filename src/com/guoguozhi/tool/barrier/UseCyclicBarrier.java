package com.guoguozhi.tool.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class UseCyclicBarrier {

    // 循环屏障
    private static CyclicBarrier barrier = new CyclicBarrier(3);

    // 工作线程
    private static class WorkingThread extends Thread {
        @Override
        public void run() {
            if (!isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " is ready to do it's work.");
                for (int i = 0; i < 2; i++) {
                    System.out.println(Thread.currentThread().getName() + " is doing it's step one work.");
                }

                System.out.println(Thread.currentThread().getName() + "  完成了第一步");

                try {
                    barrier.await();  // 阻塞方法，线程会卡到此处直至等待其他线程都到齐了，后面的代码才会执行
                    System.out.println(Thread.currentThread().getName() + " 等待结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < 2; i++) {
                    System.out.println(Thread.currentThread().getName() + " is doing it's step two work.");
                }

                System.out.println(Thread.currentThread().getName() + " has finished it's work.");
            }
        }
    }

    public static void main(String args[]) {
        for (int i = 0; i < 3; i++) {
            WorkingThread workingThread = new WorkingThread();
            workingThread.setName("workingThread-" + (i+1));
            workingThread.start();
        }
        System.out.println(Thread.currentThread().getName() + " do it's work");
    }
}

