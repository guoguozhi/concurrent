package com.guoguozhi.tool.semaphore;

import com.guoguozhi.pool.SqlConnectionImpl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

// 数据库连接池semaphore版
public class DBPoolSemaphore2 {

    //  连接池的大小
    private static final int POOL_SIZE = 10;
    //  连接池
    private static LinkedList<Connection> pool = new LinkedList<>();
    // 信号量
    // 表示可用连接数的信号量
    private static Semaphore semaphore;

    // 初始化信号量
    public DBPoolSemaphore2() {
        semaphore = new Semaphore(POOL_SIZE);
    }

    static {
        //  初始化连接池
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.addLast(SqlConnectionImpl.newConnection());
        }
    }

    // 持有连接
    public Connection takeConnection() throws InterruptedException {
        Connection connection = null;
        semaphore.acquire();
        synchronized (pool) {
            if (!pool.isEmpty()) {
                // 获取一个数据库连接，连接池中就少了一个，信号量的许可就减少一个
                connection = pool.removeFirst();
                System.out.println("当前有" + semaphore.getQueueLength() + "个线程正在等待获取数据库连接" + " 可用的数据库连接数:" + semaphore.availablePermits());
            }
        }
        return connection;
    }

    // 释放连接
    public void releaseConnection(Connection connection) throws InterruptedException {
        if (connection != null) {
            //System.out.println("当前有" + semaphore.getQueueLength()  + "个线程正在等待获取数据库连接"  + " 可用的数据库连接数:" +  semaphore.availablePermits());
            synchronized (pool) {
                pool.addLast(connection);
            }
        }
        semaphore.release();
    }
}
