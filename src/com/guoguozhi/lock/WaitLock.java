package com.guoguozhi.lock;

/**
 * 测试wait和锁的关系: wait方法会释放锁
 */
public class WaitLock {

    // 锁
    private static final Object lock = new Object();

    public static void main(String args[]) {
        WaitThread waitThread = new WaitThread();
        waitThread.setName("waitThread");
        NotWaitThread notWaitThread = new NotWaitThread();
        notWaitThread.setName("notWaitThread");
        waitThread.start();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " sleep end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notWaitThread.start();
    }

    private static class WaitThread extends Thread{
        @Override
        public void run() {
            if (!isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " will take the lock " + lock.toString());
                synchronized (lock) { // 获取锁
                    System.out.println(Thread.currentThread().getName() + " has taken the lock " + lock.toString());
                    try {
                        lock.wait(); // ① wait方法调用前先获取锁对象 ② 获取的是什么锁就用什么对象来调用wait? 不理解 后面研究
                        System.out.println(Thread.currentThread().getName() + " wait end");
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " wait is interrupted");
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " will release the lock " + lock.toString());
                }
                System.out.println(Thread.currentThread().getName() + " has released the lock " + lock.toString());
            }
        }
    }

    private static class NotWaitThread extends Thread{
        @Override
        public void run() {
            if (!isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " will take the lock " + lock.toString());
                synchronized (lock) { // 获取锁
                    System.out.println(Thread.currentThread().getName() + " has taken the lock " + lock.toString());
                    System.out.println(Thread.currentThread().getName() + " will release the lock " + lock.toString());
                }
                System.out.println(Thread.currentThread().getName() + " has released the lock " + lock.toString());
            }
        }
    }
}
