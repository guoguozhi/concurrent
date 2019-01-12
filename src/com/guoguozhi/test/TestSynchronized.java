package com.guoguozhi.test;

import com.guoguozhi.waitnotify.Express;

public class TestSynchronized {

    private static class UseThread extends Thread{
        private Express express;
        public UseThread(Express express){
            this.express = express;
        }
        @Override
        public void run() {
            express.testSynchronized();
        }
    }

    public static void main(String args[]) {
        Express express = new Express();
        for (int i = 0; i < 3; i++) {
            (new UseThread(express)).start();
        }
    }
}
