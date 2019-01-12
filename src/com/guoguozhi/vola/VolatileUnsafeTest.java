package com.guoguozhi.vola;

import com.guoguozhi.tools.SleepTools;

public class VolatileUnsafeTest {

    private static class VolatileVar implements Runnable{

        private volatile int a = 0;

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            this.a = this.a ++;
            System.out.println(threadName + " --start-- " + this.a);
            // 通过添加阻塞方法来模拟多线程对同一变量的访问
            SleepTools.sleepForMilliseconds(200);
            this.a = this.a + 1;
            System.out.println(threadName + " --ended-- " + this.a);
        }
    }

    // volatile无法提供操作的原子性，却是可以提供操作的可见性
    public static void main(String args[]) {
        VolatileVar volatileVar =  new VolatileVar();
        Thread thread = new Thread(volatileVar);
        Thread thread2 = new Thread(volatileVar);
        Thread thread3 = new Thread(volatileVar);
        Thread thread4= new Thread(volatileVar);
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
