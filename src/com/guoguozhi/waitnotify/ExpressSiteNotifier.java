package com.guoguozhi.waitnotify;

public class ExpressSiteNotifier extends Thread {
    private Express express;

    public ExpressSiteNotifier(Express express){
        this.express = express;
    }

    public ExpressSiteNotifier(String  name, Express express){
        super(name);
        this.express = express;
    }

    // 修改当前位置
    public  void changeCurrentSite(){
        synchronized(this.express) {
            String threadName = Thread.currentThread().getName();
            System.out.println("开始改变当前位置." + threadName);
            // 改变位置
            this.express.setCurrentSite("beijing");
            // 发出通知
            this.express.notifyAll();
            System.out.println("改变当前里程数的线程: "+ threadName);
            // 进行自己的业务处理
            System.out.println("结束改变当前位置并发出通知." + threadName);
        }
    }

    @Override
    public void run() {
        if (!isInterrupted()) {
            this.changeCurrentSite();
        }
    }
}
