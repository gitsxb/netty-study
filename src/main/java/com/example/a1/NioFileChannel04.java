package com.example.a1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NioFileChannel04 {
    public static void main(String[] args) throws Exception{
        FileInputStream inputStream = new FileInputStream("1.txt");
        FileChannel fileChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel1 = outputStream.getChannel();

        fileChannel.transferTo(0, fileChannel.size(), fileChannel1);
        fileChannel1.transferFrom(fileChannel, 0, fileChannel.size());

        inputStream.close();
        outputStream.close();
    }
}
