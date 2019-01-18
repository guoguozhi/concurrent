package com.guoguozhi.tool.barrier;

import com.guoguozhi.tools.SleepTools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class UseCyclicBarrier2 {

    private static final CyclicBarrier barrier = new CyclicBarrier(3);

    private static class ThreadA extends Thread {
        @Override
        public void run() {
            if (!isInterrupted()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("when A is  sleeping, A  is interrupted. ");
                    e.printStackTrace();
                }
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    System.out.println("when A is  waiting, A is interrupted. barrier is broken? " + barrier.isBroken());
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    System.out.println("A barrier is broken.");
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ThreadB extends Thread {
        @Override
        public void run() {
            if (!isInterrupted()) {
                SleepTools.sleepForSeconds(4);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    System.out.println("B is interrupted.");
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    System.out.println("B barrier is broken.");
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ThreadC extends Thread {
        @Override
        public void run() {
            if (!isInterrupted()) {
                SleepTools.sleepForSeconds(8);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    System.out.println("C is interrupted.");
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    System.out.println("C barrier is broken.");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        threadA.start();

        SleepTools.sleepForMilliseconds(2000);

        threadA.interrupt();

        ThreadB threadB = new ThreadB();
        threadB.start();

        ThreadC threadC = new ThreadC();
        threadC.start();
    }
}
