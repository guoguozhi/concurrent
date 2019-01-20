package com.guoguozhi.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class UseAtomicIntegerArray {

    public static void main(String[] args) {
        int[] array = new int[]{10, 20, 30, 40};
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(array);
        System.out.println(atomicIntegerArray.get(0));
        atomicIntegerArray.set(0, 100);
        System.out.println(atomicIntegerArray.get(0));
        System.out.println(array[0]);
    }
}
