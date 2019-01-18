package com.guoguozhi.tool.barrier;

import com.guoguozhi.tools.SleepTools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
                //barrier.reset();
                //System.out.println("A barrier's broken is " + barrier.isBroken());
                try {
                    barrier.await();
                    //barrier.await(1, TimeUnit.SECONDS); //  表示从线程获取CPU执行权并执行后，最多等1s，否则超时抛出TimeoutException，并将破损标志位broken置位true
                } catch (InterruptedException e) {
                    //  线程A被interrupt，barrier的破损标志位为true
                    System.out.println("when A is  waiting, A is interrupted. barrier is broken? " + barrier.isBroken());
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    System.out.println("A barrier is broken.");
                    e.printStackTrace();
                } /*catch (TimeoutException e) {
                    System.out.println("A timeout barrier is  broken?" + barrier.isBroken());
                    e.printStackTrace();
                }*/
                System.out.println("A barrier's broken is " + barrier.isBroken());
            }
        }
    }

    private static class ThreadB extends Thread {
        @Override
        public void run() {
            if (!isInterrupted()) {
                SleepTools.sleepForSeconds(4);
                try {
                    System.out.println("B barrier's broken is " + barrier.isBroken() + ", number of waiting thread is " + barrier.getNumberWaiting());
                    barrier.await();
                    System.out.println("B continue does it's work.");
                } catch (InterruptedException e) {
                    System.out.println("B is interrupted.");
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    System.out.println("B barrier is broken.");
                    e.printStackTrace();
                }
                System.out.println("B barrier's broken is " + barrier.isBroken());
            }
        }
    }

    private static class ThreadC extends Thread {
        @Override
        public void run() {
            if (!isInterrupted()) {
                SleepTools.sleepForSeconds(8);
                try {
                    System.out.println("C barrier's broken is " + barrier.isBroken() + ", number of waiting thread is " + barrier.getNumberWaiting());
                    barrier.await();
                    System.out.println("C continue does it's work.");
                } catch (InterruptedException e) {
                    System.out.println("C is interrupted.");
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    System.out.println("C barrier is broken.");
                    e.printStackTrace();
                }
                System.out.println("C barrier's broken is " + barrier.isBroken());
            }
        }
    }

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        threadA.start();

        //SleepTools.sleepForMilliseconds(100);
        //threadA.interrupt();
        ThreadB threadB = new ThreadB();
        threadB.start();

        ThreadC threadC = new ThreadC();
        threadC.start();

        SleepTools.sleepForSeconds(20);
        System.out.println("barrier's broken is " + barrier.isBroken());
    }
}

//  不管什么原因导致了barrier is broken，那么最初定义的需要相互等待的线程，在执行await方法时，会抛出BarrierBrokenException异常~

