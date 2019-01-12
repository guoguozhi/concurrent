package com.guoguozhi.waitnotify;

import com.guoguozhi.tools.SleepTools;

import java.util.ArrayList;
import java.util.List;

public class TestExpress {

    private static Express_bak express = new Express_bak();

    // 检测里程数的线程，当不满足条件继续等待
    private static class CheckCurrentKilometresThread extends Thread{
        @Override
        public void run() {
            express.waitCurrentKilometresChange();
        }
    }

    // 检测位置的线程，当不满足条件继续等待
    private static class CheckCurrentSiteThread extends Thread{
        @Override
        public void run() {
            express.waitCurrentSiteChange();
        }
    }

    // 改变里程数的线程
    private static class ChangeCurrentKilometresThread extends Thread {
        @Override
        public void run() {
            express.changeCurrentKilometres();
        }
    }

    // 改变位置的线程
    private static class ChangeCurrentSiteThread extends Thread{
        @Override
        public void run() {
            express.changeCurrentSite();
        }
    }

    private static class InterruptThreadThread extends Thread {
        private Thread otherThread;
        public InterruptThreadThread(Thread otherThread) {
            this.otherThread = otherThread;
        }
        @Override
        public void run() {
            if (!isInterrupted()) {
                this.otherThread.interrupt();
                System.out.println(this.otherThread.getName() + " is interrupted.");
            }
        }
    }

    public static void main(String args[])  {

        // 提前检测
        // 创建3个线程检测里程数
        /*
        for (int i = 0; i < 3; i++) {
            CheckCurrentKilometresThread currentKilometresThread = new CheckCurrentKilometresThread();
            currentKilometresThread.setName("check-kilometres-thread-" + (i+1));
            currentKilometresThread.start();
        }
       */

        // 创建3个线程检测位置
        List<Thread> checkCurrentSiteThreads = new ArrayList<Thread>();
        for (int i = 0; i < 3; i++) {
            CheckCurrentSiteThread checkCurrentSiteThread = new CheckCurrentSiteThread();
            checkCurrentSiteThread.setName("check-site-thread-" + (i+1));
            checkCurrentSiteThreads.add(checkCurrentSiteThread);
        }

        checkCurrentSiteThreads.get(0).start();

        checkCurrentSiteThreads.get(1).start();

        /*
        // 休眠500ms
        SleepTools.sleepForMilliseconds(500);
        // 中断第2个线程
        InterruptThreadThread interruptThreadThread = new InterruptThreadThread(checkCurrentSiteThreads.get(1));
        interruptThreadThread.start();
       */

        checkCurrentSiteThreads.get(2).start();

        // 休眠3s，将检测线程都启动
        SleepTools.sleepForSeconds(3);

        // 创建改变线程
        //ChangeCurrentKilometresThread changeCurrentKilometresThread = new ChangeCurrentKilometresThread();
        //changeCurrentKilometresThread.setName("change-kilometres-thread");
        //changeCurrentKilometresThread.start();

        ChangeCurrentSiteThread changeCurrentSiteThread = new ChangeCurrentSiteThread();
        changeCurrentSiteThread.setName("change-site-thread");
        changeCurrentSiteThread.start();
    }
}
