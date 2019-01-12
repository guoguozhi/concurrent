package com.guoguozhi.waitnotify;

import com.guoguozhi.tools.SleepTools;

/**
 * 等待通知的标准范式
 * 等待方(waiter)
 * 1.获取锁对象(即wait方法需方在synchronized修饰的方法中)
 * 2.轮询判断条件是否满足(轮询的目的: 即便wait方法被中断了，也可继续条件的判断)
 * 3.当条件不满足时，则等待，当条件满足时，执行自己的业务方法
 * 通知方(notifier)
 * 1.获取对象锁(即notify/notifyAll方法需放在synchronized修饰的方法中)
 * 2.修改条件
 * 3.发出通知
 *
 * 注意: waiter中和notifier中的wait和notify/notifyAll方法的调用者是锁对象
 */

// 快递
public class Express_bak {// 第三方
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

    // 通知方

    // 修改里程数
    public synchronized void changeCurrentKilometres(){
        System.out.println("开始改变当前里程数." + Thread.currentThread().getName());
        // 改变里程数
        this.currentKilometres = 101;
        // 发出通知
        this.notifyAll();
        System.out.println("改变当前里程数的线程: "+ Thread.currentThread().getName());
        // 进行自己的业务处理
        System.out.println("结束改变当前里程数并发出通知." + Thread.currentThread().getName());
    }

    // 修改当前位置
    public synchronized void changeCurrentSite(){
        System.out.println("开始改变当前位置." + Thread.currentThread().getName());
        // 改变位置
        this.currentSite = "beijing";
        // 发出通知
        this.notifyAll();
        System.out.println("改变当前里程数的线程: "+ Thread.currentThread().getName());
        // 进行自己的业务处理
        System.out.println("结束改变当前位置并发出通知." + Thread.currentThread().getName());
    }

    // 等待方

    // 等待里程数的变化
    public synchronized void waitCurrentKilometresChange(){
        System.out.println("开始检测当前里程数是否满足条件." + Thread.currentThread().getName());
        // 循环检测是否满足条件，不满足条件继续等待
        while(this.currentKilometres <= 100){
            // 继续等待
            try {
                this.wait();
                System.out.println("checking 里程数变化的线程: "+ Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("结束检测当前里程数是否满足条件." + Thread.currentThread().getName());
        // 进行自己的业务处理
        System.out.println("里程数满足条件并进行自己的业务处理." + Thread.currentThread().getName());
    }

    // 疑问点:
    // ① 为什么wait/notify/notifyAll方法需要使用synchronized关键字修饰
    // ② 使用了synchronized关键字修饰的方法只能一个线程进入，为什么TestExpress中启动三个线程都进入了wait相关的方法
    // 等待当前位置的变化，因wait方法释放了获取的对象锁，并未等方法执行结束，其他两个线程又开始了对象锁的争夺
    public synchronized void waitCurrentSiteChange(){
        System.out.println("开始检测当前位置是否满足条件." + Thread.currentThread().getName());
        // 循环检测是否满足条件，不满足条件继续等待
        // ? 为什么一定要轮询检测条件是否满足，因线程在等待过程中若被interrupt掉了，那么会抛出InterruptedException异常，我们及时处理了
        // 异常，而后线程将不会继续等待(监听)，因此需要轮询判断，即便是interrupt掉了也可继续良好监听变化(此为设计良好的监听)
        while(!"beijing".equals(this.currentSite)){
            // 继续等待
            try {
                this.wait(); // wait方法执行时，需先获取锁，在执行了wait方法以后，线程处于阻塞状态，然后并释放了对象锁
                System.out.println("checking 当前位置的线程: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("结束检测当前位置是否满足条件." + Thread.currentThread().getName());
        // 进行自己的业务处理
        System.out.println("当前位置满足条件并进行自己的业务处理." + Thread.currentThread().getName());
    }

    /**
     *  synchronized修饰的方法，若多个线程同时访问，那么只能一个一个的访问，等第一个访问结束以后，第二个线程才能访问该方法
     *  方法结束以后，会释放对象锁，剩余线程继续争夺对象锁，依次重复上述过程，测试时最好加上sleep，否则给人一种多个线程同时
     *  进入的感觉
     */
    public synchronized void testSynchronized(){
        System.out.println("testSynchronized ... ..." + Thread.currentThread().getName());
        SleepTools.sleepForSeconds(3);
    }
}