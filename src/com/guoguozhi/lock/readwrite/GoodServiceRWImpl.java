package com.guoguozhi.lock.readwrite;

import com.guoguozhi.tools.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GoodServiceRWImpl implements  GoodService{
    private GoodInfo goodInfo;
    //  可重入的读写锁
    private final ReentrantReadWriteLock reentrantReadWriteLock =  new ReentrantReadWriteLock();
    //  读锁
    private final Lock readLock = reentrantReadWriteLock.readLock();
    //  写锁
    private final Lock writeLock = reentrantReadWriteLock.writeLock();

    public GoodServiceRWImpl(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public GoodInfo getGoodInfo() {
        readLock.lock();
        try {
            SleepTools.sleepForMilliseconds(5);
            return this.goodInfo;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void sell(int sellNumber) {
        readLock.lock();
        try {
            SleepTools.sleepForMilliseconds(5);
            this.goodInfo.changeStoreNumberAndTotalMoney(sellNumber);
        } finally {
            readLock.unlock();
        }
    }
}
