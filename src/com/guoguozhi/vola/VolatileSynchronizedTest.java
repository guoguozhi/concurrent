package com.guoguozhi.vola;

public class VolatileSynchronizedTest {

    private volatile int age = 100000;

    public int getAge(){
        return this.age;
    }

    // 为了说明volatile修饰的变量在多个线程操作时也是不安全的，说明了原理是啥？虽然volatile有不安全的因素，那么它的提出是为了干嘛呢？
    // 现在volatile多用于jdk中，一般就是适用于多个线程读，一个线程写，保证了变量对外的可见性，却不能保证写操作的原子性，而synchronized
    // 关键字即可保证原子性又可保证可见性~
    public void setAge(int age){
        this.age = age + 20;
    }

    public void test(){
        this.age ++;
    }

    public void test2(){
        this.age --;
    }

    private static class UseThread extends Thread{
        private VolatileSynchronizedTest volatileSynchronizedTest;

        public UseThread(String name, VolatileSynchronizedTest volatileSynchronizedTest) {
            super(name);
            this.volatileSynchronizedTest = volatileSynchronizedTest;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) { // 递增次数
                this.volatileSynchronizedTest.test(); // 对age递增
            }
            System.out.println(this.getName() + " age: " + this.volatileSynchronizedTest.getAge());
        }
    }

    private static class UseThread2 extends Thread{
        private VolatileSynchronizedTest volatileSynchronizedTest;

        public UseThread2(String name, VolatileSynchronizedTest volatileSynchronizedTest) {
            super(name);
            this.volatileSynchronizedTest = volatileSynchronizedTest;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                this.volatileSynchronizedTest.test2();
            }
            System.out.println(this.getName() + " age: " + this.volatileSynchronizedTest.getAge());
        }
    }

    public static void main(String args[]) {
        VolatileSynchronizedTest volatileSynchronizedTest = new VolatileSynchronizedTest();
        UseThread useThread = new UseThread("testVolatileThread" , volatileSynchronizedTest);
         // 一个线程通过start方法启动以后，jvm就会将线程对象与操作系统中的线程对应起来
        useThread.start();

//        UseThread2 useThread2 = new UseThread2("testVolatileThread2" , volatileSynchronizedTest);
//        useThread2.start();
        for (int i = 0; i < 100000; i++) {
            volatileSynchronizedTest.test2();
        }
        System.out.println(Thread.currentThread().getName() + " age: " + volatileSynchronizedTest.getAge());
    }
}
