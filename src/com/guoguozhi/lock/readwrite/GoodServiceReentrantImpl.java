package com.guoguozhi.lock.readwrite;

import com.guoguozhi.tools.SleepTools;

import java.util.concurrent.locks.ReentrantLock;

public class GoodServiceReentrantImpl implements GoodService{
    private GoodInfo goodInfo;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public GoodServiceReentrantImpl(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public GoodInfo getGoodInfo() {
        this.reentrantLock.lock();
        try {
            SleepTools.sleepForMilliseconds(5);
            return this.goodInfo;
        } finally {
            this.reentrantLock.unlock();
        }
    }

    @Override
    public void sell(int sellNumber) {
        this.reentrantLock.lock();
        try {
            SleepTools.sleepForMilliseconds(5);
            this.goodInfo.changeStoreNumberAndTotalMoney(sellNumber);
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
