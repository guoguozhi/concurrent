package com.guoguozhi.waitnotify;

import com.guoguozhi.tools.SleepTools;

public class TestExpressNotiferWaiter {

    public static void main(String args[]) {
        // 快递对象
        Express express = new Express("tianjin","shanghai", 1000);

        // 创建3个waiter
        for (int i = 0; i < 3; i++) {
            ExpressKMWaiter expressKMWaiter = new ExpressKMWaiter(express);
            expressKMWaiter.setName("check-km-thread-" + (i+1));
            expressKMWaiter.start();
        }

        // 休眠3s，将检测线程都启动
        SleepTools.sleepForSeconds(3);

        // 创建notifier
        ExpressKMNotifier expressKMNotifier = new ExpressKMNotifier(express);
        expressKMNotifier.setName("change-km-thread");
        expressKMNotifier.start();
    }
}
