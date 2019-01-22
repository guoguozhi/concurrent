package com.guoguozhi.lock.readwrite;

public interface GoodService {
    public GoodInfo getGoodInfo();  //  获取商品信息

    public void sell(int sellNumber); //  售出
}
