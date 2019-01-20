package com.guoguozhi.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomicInteger {
    private static AtomicInteger atomicInteger = new AtomicInteger(10);

    public static void main(String[] args) {
        System.out.println("原始值:" + atomicInteger.get()); // 10
        System.out.println("getAndIncrement:" + atomicInteger.getAndIncrement()); // 先返回再递增 10 -> 11
        System.out.println("incrementAndGet:" + atomicInteger.incrementAndGet()); // 先递增再返回 12 -> 12
        System.out.println("getAndDecrement:" + atomicInteger.getAndDecrement()); // 先返回再递减 12 -> 11
        System.out.println("decrementAndGet:" + atomicInteger.decrementAndGet()); // 先递减再返回 10 -> 10
    }
}
