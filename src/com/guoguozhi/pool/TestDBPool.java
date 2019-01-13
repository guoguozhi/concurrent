package com.guoguozhi.pool;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class TestDBPool {

    // 数据库连接池(初始化10个连接)
    private static DBPool pool = new DBPool(10);

    // 工作线程
    private static class Worker implements Runnable {

        // 操作次数
        private int count;

        // 成功获取连接的次数
        private AtomicInteger get;

        // 失败获取连接的次数
        private AtomicInteger notGet;

        public Worker(int count, AtomicInteger get, AtomicInteger notGet) {
            this.count = count;
            this.get = get;
            this.notGet = notGet;
        }

        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                while (count > 0) {
                    // 获取数据库连接(超时等待时间1s)
                    SqlConnectionImpl connection = (SqlConnectionImpl) pool.fetchConnection(1000);
                    if (connection != null) { // 拿到连接
                        System.out.println(Thread.currentThread().getName() + "已获取到连接.");
                        // 模拟操作数据库
                        try {
                            connection.createStatement();
                            connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.out.println("提交失败.");
                            try {
                                connection.rollback();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                                System.out.println("回滚失败.");
                            }
                        } finally {
                            // 释放连接
                            pool.releaseConnection(connection);
                        }
                        // 计数一次
                        this.get.incrementAndGet();
                    } else { // 未获取连接
                        System.out.println(Thread.currentThread().getName() + "未获取到连接，获取连接超时.");
                        // 计数一次
                        this.notGet.incrementAndGet();
                    }
                    count--;
                }
            }
            System.out.println(Thread.currentThread().getName() + " get:" + this.get.get() + " notGet:" + this.notGet.get());
        }
    }

    public static void main(String args[]) {
        // 线程总数
        int numberOfWorkerThreads = 50;
        // 操作次数
        int numberOfCounts = 20;
        // 成功计数器
        AtomicInteger get = new AtomicInteger();
        // 失败计数器
        AtomicInteger notGet = new AtomicInteger();
        // 创建线程
        for (int i = 0; i < numberOfWorkerThreads; i++) {
            Worker worker = new Worker(numberOfCounts, get, notGet);
            Thread workerThread = new Thread(worker, "worker-" + (i + 1));
            workerThread.start();
        }
    }
}


