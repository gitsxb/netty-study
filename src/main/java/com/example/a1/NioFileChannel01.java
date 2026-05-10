package com.example.a1;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel01 {
    public static void main(String[] args) throws Exception{
        String s = "hello,尚硅谷";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ByteBuffer put = byteBuffer.put(s.getBytes());

        FileOutputStream outputStream = new FileOutputStream("d.txt");
        FileChannel fileChannel = outputStream.getChannel();
        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        outputStream.close();

    }
}
