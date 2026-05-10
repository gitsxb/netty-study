package com.example.a1;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel02 {
    public static void main(String[] args) throws Exception{
        FileInputStream inputStream = new FileInputStream("d.txt");
        FileChannel fileChannel = inputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        fileChannel.read(byteBuffer);

        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array()));
        inputStream.close();
    }
}
