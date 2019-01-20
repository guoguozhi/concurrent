package com.guoguozhi.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicStampedReference;

public class UseAtomicStampedReference {
    // 初始化
    private static AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<String>("Jack" ,0);
    //private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        final String oldReference = atomicStampedReference.getReference();
        final int oldStamp = atomicStampedReference.getStamp();
        // 打印初始化值
        System.out.println("oldReference:" + oldReference + " oldStamp:" + oldStamp);

        /*
        // 在main先中进行设置
        //  第一次设置
        atomicStampedReference.compareAndSet(oldReference, "Rose", oldStamp, 1);
        System.out.println("after first compare and set , then the reference is " + atomicStampedReference.getReference() + " stamp is " + atomicStampedReference.getStamp());
        */

        /*
        // Rose 1

        //  第二次设置
        boolean success = atomicStampedReference.compareAndSet(oldReference, "Jack", oldStamp, 2);
        System.out.println("success : " + success); // false
        System.out.println("after first compare and set , then the reference is " + atomicStampedReference.getReference() + " stamp is " + atomicStampedReference.getStamp()); // Rose 1
        */

        /*
        // 必须值和版本号都一致才会设置成功！
        //  一般设置的版本号都是递增的，也可以不按这个规则来~
        boolean success = atomicStampedReference.compareAndSet(atomicStampedReference.getReference(), "Milk", atomicStampedReference.getStamp(), 2);
        System.out.println("success : " + success); // false
        System.out.println("after first compare and set , then the reference is " + atomicStampedReference.getReference() + " stamp is " + atomicStampedReference.getStamp());
        */

        //  线程1
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!Thread.currentThread().isInterrupted()) {
                    atomicStampedReference.compareAndSet(oldReference, "Rose", oldStamp, 1);
                    System.out.println("A after first compare and set , then the reference is " + atomicStampedReference.getReference() +
                            " stamp is " + atomicStampedReference.getStamp());
                    //latch.countDown(); //  扣减1
                }
            }
        });

        //  线程2
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!Thread.currentThread().isInterrupted()) {
                    // oldReference: Jack oldStamp: 0  和 atomicStampedReference.getReference() 、atomicStampedReference.getStamp()不一致，所以不更新
                    boolean success  = atomicStampedReference.compareAndSet(oldReference, "Milk", oldStamp, 2);
                    System.out.println("success : " + success);
                    System.out.println("B after first compare and set , then the reference is " + atomicStampedReference.getReference() +
                            " stamp is " + atomicStampedReference.getStamp());
                }
            }
        });

        //  先启动线程A
        threadA.start();

        /*
        try {
            latch.await(); // 阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       */
        try {
            threadA.join(); // 主线程会阻塞，直到线程A执行结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //  等线程A执行结束以后再启动线程B
        threadB.start();
    }
}

//  理解CAS的原理: 在一个自旋中进行CAS操作，如果expect和内存地址指向的值相等就赋予新的值，否则就不进行任何操作