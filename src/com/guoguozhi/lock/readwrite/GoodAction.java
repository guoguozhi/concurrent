package com.guoguozhi.lock.readwrite;

import java.util.Random;

public class GoodAction {
    private static final int READ_WRITE_RATE = 10; //  读写线程数比例
    private static final int MIN_THREAD_COUNT = 3; //  最少线程数

    private static class ReadRunnable implements Runnable {
        private GoodService goodService;

        public ReadRunnable(GoodService goodService) {
            this.goodService = goodService;
        }

        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                long start = System.currentTimeMillis();
                //  模拟业务
                for (int i = 0; i < 100; i++) { // 读100次
                    this.goodService.getGoodInfo();
                }
                System.out.println(Thread.currentThread().getName() + "读取耗时:" + (System.currentTimeMillis() - start) + "毫秒");
            }
        }
    }

    private static class WriteRunnable implements Runnable {
        private GoodService goodService;

        public WriteRunnable(GoodService goodService) {
            this.goodService = goodService;
        }

        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                long start = System.currentTimeMillis();
                Random random = new Random();
                for (int i = 0; i < 10; i++) { // 写10次
                    this.goodService.sell(random.nextInt(10));
                }
                System.out.println(Thread.currentThread().getName() + "写入耗时:" + (System.currentTimeMillis() - start) + "毫秒");
            }
        }
    }

    public static void main(String[] args) {
        GoodInfo goodInfo = new GoodInfo("book", 10000, 10000);
        // synchronized关键字实现
        //GoodService goodService = new GoodServiceSynchronizedImpl(goodInfo);
        // 可见读写锁的效率比synchronized效率高
        //GoodService goodService = new GoodServiceRWImpl(goodInfo);
        GoodService goodService = new GoodServiceReentrantImpl(goodInfo);
        //  每创建一个写线程就创建READ_WRITE_RATE个读线程
        for (int i = 0; i < MIN_THREAD_COUNT; i++) {
            WriteRunnable writeRunnable = new WriteRunnable(goodService);
            for (int j = 0; j < READ_WRITE_RATE; j++) {
                ReadRunnable readRunnable = new ReadRunnable(goodService);
                Thread readThread = new Thread(readRunnable, "readThread-" + (j + 1));
                readThread.start();
            }
            Thread writeThread = new Thread(writeRunnable, "writeThread-" + (i + 1));
            writeThread.start();
        }
    }

}

//  当线程有操作对象属性时(如写)，请考虑是否加锁，因为当创建多个线程去操作时，不加锁就有问题，还得考虑是否在读上加锁？
//  如果不加锁，那么读取的数据可能就是过期的数据，一般都得加锁