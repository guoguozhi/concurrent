package com.guoguozhi.tool.semaphore;

import com.guoguozhi.pool.SqlConnectionImpl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

// 数据库连接池semaphore版
public class DBPoolSemaphore {

    //  连接池的大小
    private static final int POOL_SIZE = 10;
    //  连接池
    private static LinkedList<Connection> pool = new LinkedList<>();
    // 信号量
    // 表示可用连接数的信号量
    private static Semaphore useful;
    // 表示已用连接数的信号量
    private static Semaphore useless;

    // 初始化信号量
    public DBPoolSemaphore() {
        useful = new Semaphore(POOL_SIZE);
        useless = new Semaphore(0);
    }

    static {
        //  初始化连接池
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.addLast(SqlConnectionImpl.newConnection());
        }
    }

    // 获取、持有连接
    public Connection takeConnection() throws InterruptedException {
        Connection connection = null;
        if (!pool.isEmpty()) { // 连接池非空
            synchronized (pool) {
                connection = pool.removeFirst();
                useful.acquire();  //  useful获取一个许可 -1
                useless.release(); // useless 释放一个许可 +1
            }
        }
        return connection;
    }

    // 释放连接
    public void releaseConnection(Connection connection) throws InterruptedException {
        if (connection != null) {
            System.out.println("当前有" + useful.getQueueLength()  + "个线程正在等待获取数据库连接"  + " 可用的数据库连接数:" +  useful.availablePermits());
            synchronized (pool) {
                pool.addLast(connection);
                useful.release(); // useful释放许可 +1
                useless.acquire(); //  useless获取许可 -1
            }
        }
    }
}
