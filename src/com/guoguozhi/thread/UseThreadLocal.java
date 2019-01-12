package com.guoguozhi.thread;

// ThreadLocal的本质就是一个map，也就是底层实现是通过map来实现的，当多个线程操作同一个threadLocal变量时
// ThreadLocal<Integer> -> Map<Thread,Integer>

public class UseThreadLocal {

    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    // 创建线程数组并开始
    public void createThreadArrayAndStart(){
        for (int i = 0 ; i < 4; i++) { // 创建4个线程并启动 边创建边启动~
            TestThreadLocalThread testThreadLocalThread = new TestThreadLocalThread(i + 1);
            testThreadLocalThread.setName("thread-" + (i+1));
            testThreadLocalThread.start();
        }
    }

    // 测试threadLocal的线程
    private static class TestThreadLocalThread extends Thread{
        private int id;

        public TestThreadLocalThread(int id){
            super();
            this.id = id;
        }
        @Override
        public void run() { // 在线程方法中访问threadLocal
            System.out.println(Thread.currentThread().getName() + " start");
            Integer value = threadLocal.get(); // 获取threadLocal中的值
            value = this.id + value;
            threadLocal.set(value); // 设置threadLocal中的值
            System.out.println(Thread.currentThread().getName()  + "  " + value);
            System.out.println(Thread.currentThread().getName() + " ended");
            threadLocal.remove();//清除临时值，加快内存空间的释放回收利用
        }
    }

    public static void main(String args[]) {
        UseThreadLocal useThreadLocal = new UseThreadLocal();
        useThreadLocal.createThreadArrayAndStart();
    }
}

/**

 ThreadLocal

 ① ThreadLocal是一个可以实现线程间数据隔离的对象，每个线程在访问ThreadLocal的时候，都会将ThreadLocal在线程的工作空间进行一次
       拷贝，然后线程再次操作ThreadLocal的时候就是操作自己的那份ThreadLocal
 ② ThreadLocal是一个泛型，可以设置初始值，通过initialValue方法来实现，其实还可以调用setInitalValue的方式来设置初始值
       ?ThreadLocal是如何使用的
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
            @Override
            protected Integer initialValue(){
                return 10;// 待返回的初始值
            }
        };
 ③ ThreadLocal的get()方法是获取内部封装的值，而set()方法是设置内部封装的值

 ④ ThreadLocal是有一个方法 remove()是干嘛的？它是清除临时值的，也就是清除在线程中的临时值

 ⑤ ThreadLocal的用途: ThreadLocal可以用于线程池、Session中，后面会了解和实现、使用ThreadLocal

 */





