package com.guoguozhi.thread;

public class TestJoin {

    private static class MyThread extends Thread{

        private Thread otherThread;

        public MyThread(){
            super();
        }

        public MyThread(Thread otherThread) {
            this.otherThread = otherThread;
        }

        @Override
        public void run() {
            if (!isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " start ... ");
                if (this.otherThread != null) {
                    try {
                        this.otherThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " end ... ");
            }
        }
    }

    public static void main(String args[]) {
        // 线程C
        MyThread threadC= new MyThread();
        threadC.setName("threadC");

        // 线程B
        MyThread threadB= new MyThread(threadC);
        threadB.setName("threadB");

        // 线程A
        MyThread threadA= new MyThread(threadB);
        threadA.setName("threadA");

        // 启动线程
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
