package com.guoguozhi.tools;

import java.util.concurrent.TimeUnit;

// 休眠工具类
// 工具类的方法一般是公共的、静态的、且不被覆盖的(不希望被覆盖)
//                                     public      static          final
public class SleepTools {
    // 睡眠多少秒
    public static final void sleepForSeconds(long seconds) {
        /*
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {

        }
        */
        // 类 时间单元TimeUnit -> 块 seconds milliseconds
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    // 睡眠多少毫秒
    public static final void sleepForMilliseconds(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}
