package com.guoguozhi.safeend;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SafeEndWhenHasInterruptedException {

    private static class UseThread extends Thread{

        /*
        // 试图自己维护中断标志位
        // 线程是否取消
        private boolean cancel = false;

        public boolean isCancel() {
            return cancel;
        }
       */

        public UseThread(String name){
            super(name);
        }

        @Override
        public void run() {
            // 获取当前线程的名称
            String threadName = Thread.currentThread().getName();
            // 创建日期格式化器
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            /*
            while (!isCancel()) {// 未被取消
                // 业务代码

            }
           */

            while(!isInterrupted()){
                System.out.println(sdf.format(new Date()) + " " + threadName + " interrupt flag is " + isInterrupted()); // ①
                try {
                    // 休眠20ms
                    // 当useThread线程启动后，打印① ，然后休眠20ms，判断是否中断，未中断，继续打印①，休眠20ms，... ...
                    // 当主线程休眠了200ms后(③)，就会中断useThread线程，当useThread线程正处于休眠状态时被中断了，那么会
                    // 抛出中断异常，即中断不成功，那么中断标志位就是false，这个useThread继续执行，不能正常中断，当useThread
                    // 未处于休眠状态，假如正在执行①处的代码，那么就能正常中断...

                    Thread.sleep(20); // ②

                } catch (InterruptedException e) {
                    System.out.println(sdf.format(new Date()) + " " + threadName + " interrupt flag is " + isInterrupted());
                    // 不管是因为什么原因导致了InterruptedException异常，那么需要手动中断
                    // 中断线程(中断自己)，useThread继承自Thread有此方法
                    // 如果是在Runnable 或 Callable接口中中断，那么需要获取当前线程，然后来中断(Thread.currentThread().interrupt())
                    interrupt();
                    e.printStackTrace();
                }
            }
            System.out.println(sdf.format(new Date()) + " " + threadName + " interrupt flag is " + isInterrupted());
        }
    }

    public static void main(String args[]) throws InterruptedException {
        // 创建线程
        UseThread useThread = new UseThread("UseThread");
        // 启动线程
        useThread.start();
        // 休眠200毫秒
        Thread.sleep(200); // ③
        // 中断线程
        useThread.interrupt();
    }
}


/*
    不光sleep方法会有InterruptedException异常抛出，其实后面学习的其他方法也会有异常抛出~
*/



