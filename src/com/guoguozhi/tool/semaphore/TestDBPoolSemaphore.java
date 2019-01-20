package com.guoguozhi.tool.semaphore;

import com.guoguozhi.tools.SleepTools;

import java.sql.Connection;
import java.util.Random;

public class TestDBPoolSemaphore {

    private static DBPoolSemaphore dbPool = new DBPoolSemaphore();

    private static class WorkerThread extends Thread {
        @Override
        public void run() {
            if (!isInterrupted()) {
                try {
                    Random random = new Random();
                    long start  = System.currentTimeMillis();
                    // 获取连接
                    Connection connection = dbPool.takeConnection();
                    System.out.println(Thread.currentThread().getName() + "获取数据库连接耗时:" + (System.currentTimeMillis() - start) + "毫秒");
                    // 模拟业务操作
                    SleepTools.sleepForMilliseconds(100 + random.nextInt(100));
                    System.out.println(Thread.currentThread().getName() + "完成业务操作并归还连接.");
                    // 释放连接
                    dbPool.releaseConnection(connection);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            (new WorkerThread()).start();
        }
    }
}
