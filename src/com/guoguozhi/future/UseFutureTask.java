package com.guoguozhi.future;

import com.guoguozhi.tools.SleepTools;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class UseFutureTask {

    public static class UseCallable implements Callable<Integer> {
        private int sum = 0;
        @Override
        public Integer call() throws Exception {
            SleepTools.sleepForMilliseconds(2000);
            if (!Thread.currentThread().isInterrupted()) {
                System.out.println("useCallable开始计算.");
                for (int i = 0; i < 10000; i++) {
                    sum = sum + i;
                }
            } else {
                System.out.println("useCallable线程被中断了.");
            }
            System.out.println("useCallable线程结束计算，结果 = " + sum);
            return sum;
        }
    }

    public static void main(String[] args)  {
        UseCallable useCallable  = new UseCallable();
        FutureTask futureTask = new FutureTask(useCallable);
        (new Thread(futureTask)).start();
        SleepTools.sleepForMilliseconds(1);
        futureTask.cancel(true);
        /*
        Random random = new Random();
        if (random.nextBoolean()) { // true -> 中断线程
            futureTask.cancel(true);
        } else {
            try {
                System.out.println("sum = " + futureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        */
    }
}
