package com.guoguozhi.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class UseAtomicReference {
    private static AtomicReference<UserInfo> atomicReference = new AtomicReference<UserInfo>();

    public static void main(String[] args) {
        UserInfo jack = new UserInfo("Jack",23);
        atomicReference.set(jack);
        System.out.println("in atomicReference name is " + atomicReference.get().getName() + " age is " + atomicReference.get().getAge());
        UserInfo rose = new UserInfo("Rose",20);
        atomicReference.compareAndSet(jack, rose); //  第一个参数: 期待值(expect) 第二个参数: 更新值(update)
        System.out.println("in atomicReference name is " + atomicReference.get().getName() + " age is " + atomicReference.get().getAge());

        System.out.println("jack's name is " + jack.getName() + " age is " + jack.getAge());
    }

    private static class UserInfo {
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            super();
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}

// AtomicReference的比较设置(compareAndSet)不会对原值进行修改