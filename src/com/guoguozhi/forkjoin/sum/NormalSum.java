package com.guoguozhi.forkjoin.sum;

import com.guoguozhi.tools.SleepTools;

public class NormalSum {

    public static void main(String args[]) {
        int[] array = MakeArray.makeArray();
        long begin =  System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            // sleep的方式模拟其他业务操作
            SleepTools.sleepForMilliseconds(1);
            sum = array[i] + sum;
        }
        long end = System.currentTimeMillis();
        System.out.printf("sum = " + sum + "耗时:" + (end - begin) + "毫秒");
    }

}
