package com.guoguozhi.forkjoin.file;

import java.io.File;

public class RecursiveDirectory {

    public static void main(String args[]) {
         long start = System.currentTimeMillis();
         recurseDirectory("/Users/guoguozhi/zhangbin/learningmaterials");
         long end = System.currentTimeMillis();
        System.out.println("耗时: "+ (end - start) + "ms");
    }

    private static void recurseDirectory(String path) {
        if (!"".equals(path) && path != null) {
            File file = new File(path);
            if (file != null) {
                if (file.isDirectory()) {// 目录
                    File[] files = file.listFiles();
                    for (File item : files) {
                        if (item.isDirectory()) { // 子目录
                            recurseDirectory(item.getAbsolutePath());
                        } else {
                            System.out.println("file name is " + item.getAbsolutePath());
                        }
                    }
                } else { // 文件
                    System.out.println("file name is " + file.getAbsolutePath());
                }
            } else {
                throw new IllegalArgumentException("path is incorrect.");
            }
        } else {
            throw new IllegalArgumentException("path can not be empty or null.");
        }
    }
}
