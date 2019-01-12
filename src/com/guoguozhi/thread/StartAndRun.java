package com.guoguozhi.thread;


// 特别注意区分run和start方法，深入理解

public class StartAndRun {

    private static class UseThread extends Thread{

        public UseThread() {
            super();
        }

        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() { // 对应着UseThread的线程
            System.out.println("I am UseThread extends Thread." + Thread.currentThread().getName());
        }
    }

    public static void main(String args[]) {
        // 创建线程，线程处于新建状态
        UseThread useThread = new UseThread();
        useThread.setName("UseThread");
        // 启动线程一定要用start方法，而不是直接调用run方法，因为直接调用run方法和对象的普通方法调用没什么区别
        // 这样的直接调用并不会将线程从新建状态切换至就绪状态(或运行状态)，也并不会有一个操作系统线程与useThread对应
        // 而通过start方法来调用，会有一个操作系统线程与之对应，当获取到cpu调度时，会自动执行run或call方法
        //useThread.start(); // 线程进入就绪状态，等待被调度
        useThread.run();
    }
}
