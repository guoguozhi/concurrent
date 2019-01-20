package com.guoguozhi.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

public class UseAtomicStampedReference {
    // 初始化
    private static AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<String>("Jack" ,0);

    public static void main(String[] args) {
        String oldReference = atomicStampedReference.getReference();
        int oldStamp = atomicStampedReference.getStamp();
        // 打印初始化值
        System.out.println("oldReference:" + oldReference + " oldStamp:" + oldStamp);
        //  第一次设置
        atomicStampedReference.compareAndSet(oldReference, "Rose", oldStamp, 1);
        System.out.println("after first compare and set , then the reference is " + atomicStampedReference.getReference() + " stamp is " + atomicStampedReference.getStamp());

        /*
        // Rose 1

        //  第二次设置
        boolean success = atomicStampedReference.compareAndSet(oldReference, "Jack", oldStamp, 2);
        System.out.println("success : " + success); // false
        System.out.println("after first compare and set , then the reference is " + atomicStampedReference.getReference() + " stamp is " + atomicStampedReference.getStamp()); // Rose 1
        */

        // 必须值和版本号都一致才会设置成功！
        //  一般设置的版本号都是递增的，也可以不按这个规则来~
        boolean success = atomicStampedReference.compareAndSet(atomicStampedReference.getReference(), "Milk", atomicStampedReference.getStamp(), 2);
        System.out.println("success : " + success); // false
        System.out.println("after first compare and set , then the reference is " + atomicStampedReference.getReference() + " stamp is " + atomicStampedReference.getStamp());

    }
}

//  理解CAS的原理: 在一个自旋中进行CAS操作，如果expect和内存地址指向的值相等就赋予新的值，否则就不进行任何操作