package com.guoguozhi.synchronize;

import com.guoguozhi.tools.SleepTools;

public class SynchronizedInstanceAndClazz {

    private static class SynchronizedClazz extends Thread{
        @Override
        public void run() {
            System.out.println("Test SynchronizedClazz run start ... ...");
            SynchronizedInstanceAndClazz.synchronizedClazz();
            System.out.println("Test SynchronizedClazz run ended ... ...");
        }
    }

    private static class SynchronizedInstance implements Runnable{
        // 保留SynchronizedInstanceAndClazz类型的对象的引用就是为了调用同步方法
        private SynchronizedInstanceAndClazz  synchronizedInstanceAndClazz;

        public SynchronizedInstance(SynchronizedInstanceAndClazz synchronizedInstanceAndClazz){
            this.synchronizedInstanceAndClazz = synchronizedInstanceAndClazz;
        }

        @Override
        public void run() {
            System.out.println("Test SynchronizedInstance run start ... ..." + this.synchronizedInstanceAndClazz);
            this.synchronizedInstanceAndClazz.synchronizedInstance();
            System.out.println("Test SynchronizedInstance run ended ... ..." + this.synchronizedInstanceAndClazz);
        }
    }

    private static class SynchronizedInstance2 implements Runnable{
        private SynchronizedInstanceAndClazz  synchronizedInstanceAndClazz;

        public SynchronizedInstance2(SynchronizedInstanceAndClazz synchronizedInstanceAndClazz){
            this.synchronizedInstanceAndClazz = synchronizedInstanceAndClazz;
        }

        @Override
        public void run() {
            System.out.println("Test SynchronizedInstance2 run start ... ..." + this.synchronizedInstanceAndClazz);
            this.synchronizedInstanceAndClazz.synchronizedInstance2();
            System.out.println("Test SynchronizedInstance2 run ended ... ..." + this.synchronizedInstanceAndClazz);
        }
    }

    // 同步方法，锁对象(synchronized关键字是修饰方法的，因此需放到方法的void关键字前面)
    private synchronized void synchronizedInstance(){ // 锁是当前方法的调用对象
        // 方法开始
        System.out.println("synchronizedInstance start ... ..." + this.toString());
        SleepTools.sleepForSeconds(2);
        // 方法运行中
        System.out.println("synchronizedInstance is going ... ..." + this.toString());
        SleepTools.sleepForSeconds(2);
        // 方法结束
        System.out.println("synchronizedInstance ended ... ..." + this.toString());
    }

    // 锁对象
    private synchronized void synchronizedInstance2(){
        System.out.println("synchronizedInstance2 start ... ..." + this.toString());
        SleepTools.sleepForSeconds(2);
        System.out.println("synchronizedInstance2 is going ... ..." + this.toString());
        SleepTools.sleepForSeconds(2);
        System.out.println("synchronizedInstance2 ended ... ..." + this.toString());
    }

    // 锁类
    private static synchronized  void synchronizedClazz(){ // 锁是对象，只不过是当前类的class对象，在虚拟机中只有一个，因此能保证方法的同步执行~，没有线程安全问题~
        System.out.println("synchronizedClazz start ... ..." );
        SleepTools.sleepForSeconds(2);
        System.out.println("synchronizedClazz is going ... ...");
        SleepTools.sleepForSeconds(2);
        System.out.println("synchronizedClazz ended ... ..." );
    }

    public static void main(String args[]) {
        /*
        // 创建synchronizedInstanceAndClazz对象
        SynchronizedInstanceAndClazz synchronizedInstanceAndClazz = new SynchronizedInstanceAndClazz();
        // 创建线程synchronizedInstance
        // ?为什么将synchronizedInstanceAndClazz传递给synchronizedInstance，是因为synchronizedInstanceAndClazz有同步方法，传递给synchronizedInstance
        // 以后可以访问它的方法，也就是可以访问同步方法，可观察多线程同步的问题，若将这个对象传递给多个线程，那么在多个线程中正好可访问该对象的同步方法
        SynchronizedInstance synchronizedInstance = new SynchronizedInstance(synchronizedInstanceAndClazz);
        Thread synchronizedInstanceThread = new Thread(synchronizedInstance);
        // 启动线程
        synchronizedInstanceThread.start();

        SynchronizedInstanceAndClazz synchronizedInstanceAndClazz2 = new SynchronizedInstanceAndClazz();
        // 创建线程synchronizedInstance2
        Thread synchronizedInstance2Thread = new Thread(new SynchronizedInstance2(synchronizedInstanceAndClazz));
        // 传递了另一个对象，那么就是不同的锁，因此不能锁住
        //Thread synchronizedInstance2Thread = new Thread(new SynchronizedInstance2(synchronizedInstanceAndClazz2));
        // 启动线程
        synchronizedInstance2Thread.start();
        */
        SynchronizedClazz synchronizedClazz = new SynchronizedClazz();
        synchronizedClazz.start();

        SynchronizedClazz synchronizedClazz2 = new SynchronizedClazz();
        synchronizedClazz2.start();
    }
}
