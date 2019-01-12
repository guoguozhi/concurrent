package com.guoguozhi.waitnotify;

public class ExpressKMWaiter extends Thread {
    private Express express;

    public ExpressKMWaiter( Express express) {
        this.express = express;
    }

    public ExpressKMWaiter(String name, Express express) {
        super(name);
        this.express = express;
    }

    // 等待里程数的变化
    public void waitCurrentKilometresChange(){
        synchronized (this.express) {
            String threadName = Thread.currentThread().getName();
            System.out.println("开始检测当前里程数是否满足条件." + threadName);
            // 循环检测是否满足条件，不满足条件继续等待
            while(this.express.getCurrentKilometres() <= 100){
                // 继续等待
                try {
                    this.express.wait();
                    System.out.println("checking 里程数变化的线程: "+ threadName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("结束检测当前里程数是否满足条件." + threadName);
            // 进行自己的业务处理
            System.out.println("里程数满足条件并进行自己的业务处理." + threadName);
        }
    }

    @Override
    public void run() {
        if (!isInterrupted()) {
            this.waitCurrentKilometresChange();
        }
    }
}
