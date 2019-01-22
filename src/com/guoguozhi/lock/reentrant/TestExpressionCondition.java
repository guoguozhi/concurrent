package com.guoguozhi.lock.reentrant;

import com.guoguozhi.tools.SleepTools;

public class TestExpressionCondition {

        private static ExpressionCondition expressionCondition = new ExpressionCondition();

        // 检测里程数的线程
        private static class CheckCurrentKilometresThread extends Thread{
            @Override
            public void run() {
                expressionCondition.waitCurrentKilometresChange();
            }
        }

        // 检测位置的线程
        private static class CheckCurrentSiteThread extends Thread{
            @Override
            public void run() {
                expressionCondition.waitCurrentSiteChange();
            }
        }

        // 改变里程数的线程
        private static class ChangeCurrentKilometresThread extends Thread {
            @Override
            public void run() {
                expressionCondition.changeCurrentKilometres();
            }
        }

        // 改变位置的线程
        private static class ChangeCurrentSiteThread extends Thread{
            @Override
            public void run() {
                expressionCondition.changeCurrentSite();
            }
        }

        public static void main(String args[])  {
            // 提前创建3个线程检测位置
            for (int i = 0; i < 3; i++) {
                CheckCurrentSiteThread checkCurrentSiteThread = new CheckCurrentSiteThread();
                checkCurrentSiteThread.setName("check-site-thread-" + (i+1));
                checkCurrentSiteThread.start();
            }

            // 提前创建3个线程检测里程数
            for (int i = 0; i < 3; i++) {
                CheckCurrentKilometresThread checkCurrentKilometresThread = new CheckCurrentKilometresThread();
                checkCurrentKilometresThread.setName("check-kilometres-thread-" + (i+1));
                checkCurrentKilometresThread.start();
            }

            // 休眠3s，将检测线程都启动
            SleepTools.sleepForSeconds(3);

            // 创建改变位置线程
            ChangeCurrentSiteThread changeCurrentSiteThread = new ChangeCurrentSiteThread();
            changeCurrentSiteThread.setName("change-site-thread");
            changeCurrentSiteThread.start();

            // 创建改变里程数线程
            ChangeCurrentKilometresThread changeCurrentKilometresThread = new ChangeCurrentKilometresThread();
            changeCurrentKilometresThread.setName("change-kilometres-thread");
            changeCurrentKilometresThread.start();
        }

}
