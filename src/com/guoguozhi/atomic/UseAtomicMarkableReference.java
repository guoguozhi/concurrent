package com.guoguozhi.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class UseAtomicMarkableReference {
    private static AtomicMarkableReference<String> atomicMarkableReference = new AtomicMarkableReference<String>("Jack", false);

    public static void main(String[] args) {
        //  getReference() 获取引用值(包装值)  isMarked 获取当前的mark值
        System.out.println(atomicMarkableReference.getReference() + " " + atomicMarkableReference.isMarked());
        boolean success = atomicMarkableReference.compareAndSet("Milk",
                "Jack",
                atomicMarkableReference.isMarked(),
                true);
        System.out.println("success:" + success);
        System.out.println(atomicMarkableReference.getReference() + " " + atomicMarkableReference.isMarked());

    }
}
