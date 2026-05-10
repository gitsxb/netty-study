package com.example.a1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFIleChannel03 {
    public static void main(String[] args) throws Exception{
        FileInputStream inputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(5);

        while (true) {
            byteBuffer.clear();
            int read = fileChannel01.read(byteBuffer);
            System.out.println(read);
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
        inputStream.close();
        outputStream.close();

    }
}
