package com.guoguozhi.thread;

import com.guoguozhi.tools.SleepTools;
import org.omg.PortableServer.THREAD_POLICY_ID;

public class UseJoin {

    private static class JumpQueue extends Thread{

        private Thread previousThread; // 插队线程

        public JumpQueue(Thread previousThread) {
            this.previousThread = previousThread;
        }

        @Override
        public void run() {
            if (!isInterrupted()) {
                System.out.println(this.previousThread.getName() + " will be joined before " + Thread.currentThread().getName());
                try {
                    this.previousThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " is terminated.");
            }
        }
    }

    /**
     * 创建10个线程(1~10)
     * 线程1  线程2  线程3 ... ...  线程9 线程10
     * 线程1(main) 线程2(线程1) ... ... 线程10(线程9)
     * 执行顺序: main -> 线程1 -> 线程2 -> ... ... -> 线程10
     */
    public static void main(String args[]) {
        Thread previousThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            JumpQueue jumpQueue = new JumpQueue(previousThread);
            jumpQueue.setName(String.valueOf(i + 1));
            jumpQueue.start();
            System.out.println(previousThread.getName() + " jump the queue before " + jumpQueue.getName());
            previousThread = jumpQueue;
        }
        System.out.println(Thread.currentThread().getName() + " will sleep for a few seconds.");
        SleepTools.sleepForSeconds(2);
        System.out.println(Thread.currentThread().getName() + " is terminated.");
    }
}
