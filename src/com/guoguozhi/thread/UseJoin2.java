package com.guoguozhi.thread;

public class UseJoin2 {

    static class UseRunnable implements Runnable {
        private Thread previousThread;

        public UseRunnable(Thread previousThread) {
            super();
            if (previousThread != null) {
                this.previousThread = previousThread;
                //  将previousThread插队到当前线程前面
                try {
                    this.previousThread.join();
                } catch (InterruptedException e) { // 插队失败
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " is running.");
            }
        }
    }

    public static void main(String[] args) {
        // A -> B -> C -> D ... F -> main
        String[] threadNames = new String[]{"A", "B", "C", "D", "E", "F"};
        Thread  main = Thread.currentThread(); // 主线程
        Thread previousThread = null;
        Thread lastThread = null;
        for (int i = 0; i < threadNames.length; i++) {
            Thread currentThread = new Thread(new UseRunnable(previousThread), threadNames[i]);
            previousThread = currentThread;
            if (i == threadNames.length - 1) {
                lastThread = currentThread;
            }
            currentThread.start();
        }
        try {
            lastThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main is running.");
    }
}
