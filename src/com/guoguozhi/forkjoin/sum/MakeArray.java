package com.guoguozhi.forkjoin.sum;


import java.util.Random;

public class MakeArray {

    // 数组长度
    private static final int ARRAY_LENGTH = 40000;

    public static int[] makeArray(){ //耗时操作
        int[] result = new int[ARRAY_LENGTH];
        Random random = new Random();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            result[i] = random.nextInt(ARRAY_LENGTH * 3);
        }
        return result;
    }

}
