package com.guoguozhi.waitnotify;

import com.guoguozhi.tools.SleepTools;

import java.util.ArrayList;
import java.util.List;

public class TestExpressNotiferWaiter {

    public static void main(String args[]) {
        // 快递对象
        Express express = new Express("tianjin","shanghai", 1000);

        List<ExpressKMWaiter> list = new ArrayList<ExpressKMWaiter>();
        // 创建3个waiter
        for (int i = 0; i < 3; i++) {
            ExpressKMWaiter expressKMWaiter = new ExpressKMWaiter(express);
            expressKMWaiter.setName("check-km-thread-" + (i+1));
            list.add(expressKMWaiter);
        }

        list.get(2).start();
        list.get(0).start();
        list.get(1).start();

        // 休眠3s，将检测线程都启动
        SleepTools.sleepForSeconds(3);

        // 创建notifier
        ExpressKMNotifier expressKMNotifier = new ExpressKMNotifier(express);
        expressKMNotifier.setName("change-km-thread");
        expressKMNotifier.start();
    }
}

// 对象的notify方法是随机唤醒的~