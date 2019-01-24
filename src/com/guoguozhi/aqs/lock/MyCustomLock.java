package com.guoguozhi.aqs.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//  模仿ReentrantLock -> 独占式的
public class MyCustomLock implements Lock {
    private final Sync sync  = new Sync();

    //  独占式方法相关方法(模板方法+流程方法)
    //  假设state = 1表示获取到了锁，state = 0 表示未获取到所
    private static class Sync extends AbstractQueuedSynchronizer { //  同步器

        @Override
        protected boolean isHeldExclusively() {
            return this.getState() == 1;
        }

        //  流程方法
        //  独占式获取锁
        @Override
        protected boolean tryAcquire(int arg) { //  尝试获取锁(只有state=0时，才能获取锁，获取锁以后需将状态设置为1)  许可
            if(this.compareAndSetState(0, 1)) { // 若设置成功，说明原来state = 0，现在被设置成了1，表示获取到了锁
                //  设置独占的线程是谁
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false; //  表示设置失败，说明state的值为1，有线程正用着锁
        }

        //  独占式释放锁
        @Override
        protected boolean tryRelease(int arg) { //  尝试释放锁 许可
            if (this.getState() == 0) { //  表示未获取到锁，何谈释放锁
                throw new UnsupportedOperationException();
            }
            this.setState(0);
            this.setExclusiveOwnerThread(null);
            return true;
        }

        public Condition newCondition() {
            //  调用AQS的ConditionObject的构造方法创建condition对象
            return new ConditionObject();
        }
    }

    @Override
    public void lock() { //  获取锁，不能被中断
        this.sync.acquire(1); //  调用同步器的获取许可方法
    }

    @Override
    public void lockInterruptibly() throws InterruptedException { //  获取锁，能被中断
        this.sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return this.sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return this.sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        this.sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return this.sync.newCondition();
    }
}
