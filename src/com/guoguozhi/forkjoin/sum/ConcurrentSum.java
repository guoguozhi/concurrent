package com.guoguozhi.forkjoin.sum;

import com.guoguozhi.tools.SleepTools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * fork/join的方式sum
 */
public class ConcurrentSum {

    // 并发求和任务
    private static class ConcurrentSumTask extends RecursiveTask<Integer> {// 适合有返回值

        // 阈值
        private int shreshold;

        // 求和数组
        private int[] array;

        // 开始索引
        private int fromIndex;

        // 结束索引
        private int toIndex;

        public ConcurrentSumTask(int[] array, int fromIndex, int toIndex) {
            this.array = array;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.shreshold = this.array.length / 10;
        }

        @Override
        protected Integer compute() { // 任务拆分、计算
            if ((this.toIndex - this.fromIndex) < this.shreshold) { // 不拆分任务，小于阈值
                // 直接计算返回
                int sum = 0;
                for (int i = this.fromIndex; i <= this.toIndex; i++) {
                    SleepTools.sleepForMilliseconds(1);
                    sum = sum + this.array[i];
                }
                return sum;
            } else { // 拆分成子任务，大于等于阈值
                // 假设拆分成两个子任务(也可自定义其他规则拆分成多个)
                int middleIndex = (this.fromIndex + this.toIndex) / 2;
                ConcurrentSumTask concurrentSumLeftSubTask = new ConcurrentSumTask(this.array, this.fromIndex, middleIndex);
                ConcurrentSumTask concurrentSumRightSubTask = new ConcurrentSumTask(this.array, middleIndex + 1, this.toIndex);
                // 递归的关键
                invokeAll(concurrentSumLeftSubTask, concurrentSumRightSubTask);
                // 返回结果
                return concurrentSumLeftSubTask.join() + concurrentSumRightSubTask.join();
            }
        }
    }

    public static void main(String args[]) {

        // 创建forkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 创建数组
        int[] array = MakeArray.makeArray();
        ConcurrentSumTask concurrentSumTask = new ConcurrentSumTask(array, 0, array.length - 1);

        long start = System.currentTimeMillis();

        // 执行大任务
        forkJoinPool.invoke(concurrentSumTask);

        // 获取返回值
        int sum = concurrentSumTask.join();

        long end = System.currentTimeMillis();

        System.out.println("sum = " + sum + " 耗时:" + (end - start) + "ms");
    }
}
