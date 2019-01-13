package com.guoguozhi.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据库连接池
 * 1. 在数据库连接池中维护了多个连接
 */
public class DBPool {
    // 连接池
    LinkedList<Connection> pool = new LinkedList<Connection>();

    public DBPool(int initialSize){
        if (initialSize <= 0) {
            throw new IllegalArgumentException("DBPool's initialSize must be positive number");
        } else {
            for (int i = 0; i < initialSize; i++) {
                SqlConnectionImpl  sqlConnection = new SqlConnectionImpl();
                this.pool.addLast(sqlConnection);
            }
        }
    }

    // 获取连接(mills毫秒内获取不到连接就返回)
    public Connection fetchConnection(long mills) {
        synchronized (this.pool) {
            if (mills <= 0) { // 无超时时间，获取不到连接就死磕
                // 数据库连接池为空则线程等待并释放锁
                while (this.pool.isEmpty()) {
                    try {
                        this.pool.wait();// 等池子里面有连接，一旦有连接，通知线程继续执行
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 取连接
                Connection connection = null;
                if (!this.pool.isEmpty()) {
                    connection = this.pool.removeFirst();
                }
                return connection;
            } else {// seconds为超时时间，要么获取到连接，要么返回一个null
                // 连接池为空且剩余时间>0就等待
                // 超时时间=当前时间+等待时间
                long overtime =  System.currentTimeMillis() + mills;
                // 剩余时间
                long remain = mills;
                while (this.pool.isEmpty() && remain > 0) {
                    try {
                       this.pool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    remain = overtime - System.currentTimeMillis();
                }
                // 取连接
                Connection connection = null;
                if (!this.pool.isEmpty()) {
                    connection = this.pool.removeFirst();
                }
                return connection;
            }
        }
    }

    // 释放连接
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (this.pool) {
                // 归还连接
                this.pool.addLast(connection);
                // 发出通知
                this.pool.notifyAll();
            }
        }
    }
}
