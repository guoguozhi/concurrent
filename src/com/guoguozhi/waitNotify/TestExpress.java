package com.guoguozhi.waitNotify;

import com.guoguozhi.tools.SleepTools;

public class TestExpress {

    private static Express express = new Express();

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
        for (int i = 0; i < 3; i++) {
            CheckCurrentSiteThread checkCurrentSiteThread = new CheckCurrentSiteThread();
            checkCurrentSiteThread.setName("check-site-thread-" + (i+1));
            checkCurrentSiteThread.start();
        }

        // 休眠3s，将检测线程都启动
        SleepTools.sleepForSeconds(3);

        // 创建改变线程
        //ChangeCurrentKilometresThread changeCurrentKilometresThread = new ChangeCurrentKilometresThread();
        //changeCurrentKilometresThread.start();

        ChangeCurrentSiteThread changeCurrentSiteThread = new ChangeCurrentSiteThread();
        //changeCurrentSiteThread.start();

    }
}
