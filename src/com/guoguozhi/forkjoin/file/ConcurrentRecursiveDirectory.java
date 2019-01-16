package com.guoguozhi.forkjoin.file;

import com.guoguozhi.tools.SleepTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ConcurrentRecursiveDirectory {

    public static void main(String args[]) {
        long start = System.currentTimeMillis();
        // 创建forkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 创建大任务
        ConcurrentRecursiveDirectoryAction concurrentRecursiveDirectoryAction = new ConcurrentRecursiveDirectoryAction("/Users/guoguozhi/zhangbin/learningmaterials");
        // 执行大任务
        // invoke方法是阻塞方法
        System.out.println("forkJoinPool will invoke task.");
        //forkJoinPool.invoke(concurrentRecursiveDirectoryAction); // 同步方法
        forkJoinPool.execute(concurrentRecursiveDirectoryAction);// 异步方法
        //System.out.println("forkJoinPool end invoke task.");
        System.out.println("task has execute asynchronously.");
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start) + "ms"); // 异步方法打印耗时无意义

        System.out.println(Thread.currentThread().getName() + " will do something else.");

        int count = 0;
        for (int i = 0; i < 2000; i++) {
            count = count + i;
            System.out.println(Thread.currentThread().getName() + " is doing something else.");
        }


        System.out.println(Thread.currentThread().getName() + " has done something else.'");

        // 未看到打印信息时，是因为异步方法执行很快就返回了，在打印前主线程已经执行结束了，所以休眠等待查看结果
        SleepTools.sleepForSeconds(3);
    }

    //  并发递归文件夹
    private static class ConcurrentRecursiveDirectoryAction extends RecursiveAction{
        // 路径
        private String path;

        public ConcurrentRecursiveDirectoryAction(String path) {
            this.path = path;
        }

        @Override
        protected void compute() {
            if (!"".equals(path) || path != null) {
                File file = new File(this.path);
                if (file != null) {
                    if (file.isDirectory()) {
                        File[] files = file.listFiles();
                        // 搜集子任务
                        List<ConcurrentRecursiveDirectoryAction> subTasks = new ArrayList<ConcurrentRecursiveDirectoryAction>();
                        for(File subFile : files) {
                            if (subFile.isDirectory()) {
                                // 创建子任务，有多少个子目录就创建多少个子任务
                                ConcurrentRecursiveDirectoryAction concurrentRecursiveDirectoryTask = new ConcurrentRecursiveDirectoryAction(subFile.getAbsolutePath());
                                subTasks.add(concurrentRecursiveDirectoryTask);
                                // 无返回不需要用join
                            } else {
                                if (subFile.getAbsolutePath().endsWith(".xml")) {
                                    System.out.println(subFile.getAbsolutePath());
                                }
                            }
                        }
                        // 执行所有子任务
                        invokeAll(subTasks);
                    } else {
                        if (file.getAbsolutePath().endsWith(".xml")) {
                            System.out.println(file.getAbsolutePath());
                        }
                    }
                } else {
                    throw new IllegalArgumentException("please check your directory path");
                }
            } else {
                throw new IllegalArgumentException("directory's path can not be empty or null ");
            }
        }
    }
}
