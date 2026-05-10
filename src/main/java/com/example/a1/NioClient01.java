package com.example.a1;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient01 {
    public static void main(String[] args) throws Exception{
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        boolean connect = channel.connect(new InetSocketAddress("127.0.0.1", 6666));
        Scanner scanner = new Scanner(System.in);
        if (connect) {
            while (true) {
                String next = scanner.next();
                ByteBuffer buffer = ByteBuffer.wrap(next.getBytes("UTF-8"));
                channel.write(buffer);
            }
        }

    }
}
