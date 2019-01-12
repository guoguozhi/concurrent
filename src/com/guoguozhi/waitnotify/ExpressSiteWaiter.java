package com.guoguozhi.waitnotify;

public class ExpressSiteWaiter extends Thread {
    private Express express;

    public ExpressSiteWaiter(Express express) {
        this.express = express;
    }

    public ExpressSiteWaiter(String name, Express express) {
        super(name);
        this.express = express;
    }

    // 等待位置的变化
    public  void waitCurrentSiteChange(){
        synchronized (this.express) {
            String threadName =  Thread.currentThread().getName();
            System.out.println("开始检测当前位置是否满足条件." + threadName);
            while(!"beijing".equals(this.express.getCurrentSite())){
                // 继续等待
                try {
                    this.express.wait();
                    System.out.println("checking 当前位置的线程: " + threadName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("结束检测当前位置是否满足条件." + threadName);
            // 进行自己的业务处理
            System.out.println("当前位置满足条件并进行自己的业务处理." + threadName);
        }
    }

    @Override
    public void run() {
        if (!isInterrupted()) {
            this.waitCurrentSiteChange();
        }
    }
}
