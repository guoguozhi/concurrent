package com.guoguozhi.lock.readwrite;

import com.guoguozhi.tools.SleepTools;

public class GoodServiceSynchronizedImpl implements GoodService {
    private GoodInfo goodInfo;

    public GoodServiceSynchronizedImpl(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public synchronized GoodInfo getGoodInfo() {
        SleepTools.sleepForMilliseconds(5);
        return this.goodInfo;
    }

    @Override
    public synchronized void sell(int sellNumber) {
        SleepTools.sleepForMilliseconds(5);
        this.goodInfo.changeStoreNumberAndTotalMoney(sellNumber);
    }
}
