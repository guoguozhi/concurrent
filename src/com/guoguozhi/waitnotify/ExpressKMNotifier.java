package com.guoguozhi.waitnotify;

public class ExpressKMNotifier extends Thread {
    private Express express;

    public ExpressKMNotifier(Express express){
        this.express = express;
    }

    public ExpressKMNotifier(String  name, Express express){
        super(name);
        this.express = express;
    }

    // 修改里程数
    public  void changeCurrentKilometres(){
        synchronized (this.express) {
            String threadName = Thread.currentThread().getName();
            System.out.println("开始改变当前里程数." + threadName);
            // 改变里程数
            this.express.setCurrentKilometres(101);
            // 发出通知
            this.express.notify();
            System.out.println("改变当前里程数的线程: "+ threadName);
            // 进行自己的业务处理
            System.out.println("结束改变当前里程数并发出通知." + threadName);
        }
    }

    @Override
    public void run() {
        if (!isInterrupted()) {
            this.changeCurrentKilometres();
        }
    }
}
