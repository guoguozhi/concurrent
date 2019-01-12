package com.guoguozhi.test;

import com.guoguozhi.waitnotify.Express_bak;

public class TestSynchronized {

    private static class UseThread extends Thread{
        private Express_bak express;
        public UseThread(Express_bak express){
            this.express = express;
        }
        @Override
        public void run() {
            express.testSynchronized();
        }
    }

    public static void main(String args[]) {
        Express_bak express = new Express_bak();
        for (int i = 0; i < 3; i++) {
            (new UseThread(express)).start();
        }
    }
}
