package com.guoguozhi.lock.readwrite;

public class GoodInfo {
    private String name; //  名称
    private int storeNumber; //  库存
    private double totalMoney; //  总销售额 = 销售数量 * 单价
    private double price = 20; //  单价

    public GoodInfo(String name, int storeNumber, double totalMoney) {
        super();
        this.name = name;
        this.storeNumber = storeNumber;
        this.totalMoney = totalMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //  依据销售数量改变库存和总销售额
    public void changeStoreNumberAndTotalMoney(int sellNumber) {
        this.storeNumber = this.storeNumber - sellNumber;
        this.totalMoney = this.totalMoney + sellNumber * this.price;
    }

}
