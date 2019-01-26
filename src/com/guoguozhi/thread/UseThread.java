package com.guoguozhi.thread;

import com.guoguozhi.tools.SleepTools;

//  线程如何正确响应中断请求
public class UseThread {

    private static class Worker extends Thread {
        @Override
        public void run() {
            while (true && !isInterrupted()) {
                try {
                    System.out.println(Thread.currentThread().getName() + "############" + " 中断标志位:" + Thread.currentThread().isInterrupted());
                    Thread.sleep(1000);  //  线程正睡眠呢，被中断了，抛出中断异常，需捕获，因此线程不会进入死亡状态，会继续执行后面的代码直至线程正常运行解释线程进入死亡状态
                    System.out.println(Thread.currentThread().getName() + "$$$$$$$$$$$$" + "中断标志位:" + Thread.currentThread().isInterrupted());
                } catch (InterruptedException e) { // 被中断
                    e.printStackTrace();
                    //  线程在sleep被中断了，抛出了中断异常，中断标志位被重置为false了
                    //  因sleep睡眠被中断了，并对异常进行了处理，程序继续执行，那么程序能继续执行，说明中断标志位不能为true，所以就设置成了false，被clear了
                    System.out.println("中断标志位-1:" + Thread.currentThread().isInterrupted());
                    //throw new RuntimeException(); //  抛出异常 -> 线程进入死亡状态
                    Thread.currentThread().interrupt(); // 又将中断标志位设置true
                    System.out.println("中断标志位-2" + Thread.currentThread().isInterrupted());
                }
            }
        }

    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start();
        SleepTools.sleepForMilliseconds(100);
//        for (;;) { //  自旋中中断线程
//            worker.interrupt();
//        }

        worker.interrupt(); //  并不是真正中断线程，只是将线程中断标志位(interrupt status)设置为true
    }
}
