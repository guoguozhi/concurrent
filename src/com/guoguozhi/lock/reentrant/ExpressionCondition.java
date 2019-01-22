package com.guoguozhi.lock.reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  通过signal-await实现通知等待
 */
public class ExpressionCondition {
    // 起点
    private String start = "tianjin";
    // 终点
    private String end = "shanghai";
    // 总里程数
    private float totalKilometres = 1000;
    // 当前里程数
    private float currentKilometres;
    // 当前位置
    private String currentSite;
    // 可重入锁
    private ReentrantLock reentrantLock = new ReentrantLock();
    // 与公里数相关的条件
    private Condition currentKilometresCondition = reentrantLock.newCondition();
    // 与当前位置相关的条件
    private Condition currentSiteCondition = reentrantLock.newCondition();

    // 通知方

    // 修改里程数
    public void changeCurrentKilometres(){
        this.reentrantLock.lock();
        try {
            System.out.println("开始改变当前里程数." + Thread.currentThread().getName());
            // 改变里程数
            this.currentKilometres = 101;
            // 发出通知
            this.currentKilometresCondition.signal();
            System.out.println("改变当前里程数的线程: "+ Thread.currentThread().getName());
            // 进行自己的业务处理
            System.out.println("结束改变当前里程数并发出通知." + Thread.currentThread().getName());
        } finally {
            this.reentrantLock.unlock();
        }
    }

    // 修改当前位置
    public void changeCurrentSite(){
        this.reentrantLock.lock();
        try {
            System.out.println("开始改变当前位置." + Thread.currentThread().getName());
            // 改变位置
            this.currentSite = "beijing";
            // 发出通知
           this.currentSiteCondition.signal();
            System.out.println("改变当前里程数的线程: "+ Thread.currentThread().getName());
            // 进行自己的业务处理
            System.out.println("结束改变当前位置并发出通知." + Thread.currentThread().getName());
        } finally {
            this.reentrantLock.unlock();
        }
    }

    // 等待方

    // 等待里程数的变化
    public void waitCurrentKilometresChange(){
        this.reentrantLock.lock();
        try {
            System.out.println("开始检测当前里程数是否满足条件." + Thread.currentThread().getName());
            // 循环检测是否满足条件，不满足条件继续等待
            while(this.currentKilometres <= 100){
                // 继续等待
                try {
                    this.currentKilometresCondition.await();
                    System.out.println("checking 里程数变化的线程: "+ Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("结束检测当前里程数是否满足条件." + Thread.currentThread().getName());
            // 进行自己的业务处理
            System.out.println("里程数满足条件并进行自己的业务处理." + Thread.currentThread().getName());
        } finally {
            this.reentrantLock.unlock();
        }
    }

    public void waitCurrentSiteChange(){
        this.reentrantLock.lock();
        try {
            System.out.println("开始检测当前位置是否满足条件." + Thread.currentThread().getName());
            while(!"beijing".equals(this.currentSite)){
                try {
                    this.currentSiteCondition.await();
                    System.out.println("checking 当前位置的线程: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("结束检测当前位置是否满足条件." + Thread.currentThread().getName());
            // 进行自己的业务处理
            System.out.println("当前位置满足条件并进行自己的业务处理." + Thread.currentThread().getName());
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
