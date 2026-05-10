package com.example;

import java.io.*;

public class CopyDerectory {

    public static void main(String[] args) throws IOException {
        String srcPath = "F:\\BaiduNetdiskDownload\\黄丹青象棋";
        String destPath = "G:\\";
        copyFile(new File(srcPath), new File(destPath));
    }


    public static void copyFile(File srcFile, File destFile) throws IOException {
        File[] files = srcFile.listFiles();
        for (File file : files) {
            String name = file.getName();
            File f = new File(destFile, name);
            if (file.isDirectory()) {
                if (!f.exists()) {
                    f.mkdirs();
                }
                copyFile(file, f);
            } else {
                long start = System.currentTimeMillis();
                OutputStream os = null;
                InputStream in = null;
                try {
                    os = new FileOutputStream(f);
                    in = new FileInputStream(file);
                    byte[] bys = new byte[1024*1024*100];
                    while((in.read(bys) !=-1)) {
                        os.write(bys);
                    }
                }finally {
                    os.close();
                    in.close();
                    long end = System.currentTimeMillis();
                    System.out.println("处理文件："+f.getAbsolutePath()+"；耗时："+ (end-start));
//                    处理文件：001-屏风马课程简介1.mp4；耗时：74423
//                    处理文件：002-中炮直横车对屏风马两头蛇的前世今生.mp4；耗时：23552
//                    处理文件：003-黑防守阵型的技巧和特点.mp4；耗时：29176
                }
            }
        }
    }
}
