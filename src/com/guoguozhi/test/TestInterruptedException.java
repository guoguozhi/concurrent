package com.guoguozhi.test;

import com.guoguozhi.tools.SleepTools;

public class TestInterruptedException {

    private static class SleepThread extends Thread{
        @Override
        public void run() {
            if (!isInterrupted()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(getName() + " has been interrupted.");
                }
            }
        }
    }

    private static class InterruptThread extends Thread{
        private Thread interruptedThread;

        public InterruptThread(Thread interruptedThread){
            this.interruptedThread = interruptedThread;
        }
        @Override
        public void run() {
            this.interruptedThread.interrupt();
        }
    }

    public static void main(String args[]) {
        SleepThread sleepThread = new SleepThread();
        sleepThread.setName("sleepThread");
        sleepThread.start();

        SleepTools.sleepForSeconds(1);

        //InterruptThread interruptThread = new InterruptThread(sleepThread);
        //interruptThread.start();

        sleepThread.interrupt(); // main线程终止sleepThread有问题

        SleepTools.sleepForSeconds(20);

    }
}
