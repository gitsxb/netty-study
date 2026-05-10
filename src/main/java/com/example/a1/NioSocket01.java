package com.example.a1;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NioSocket01 {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(7000));

        SocketChannel socketChannel = serverSocketChannel.accept();

        ByteBuffer buffer = ByteBuffer.allocate(5);

        int limit = 8;
        while (true) {
            long read = socketChannel.read(buffer);
            buffer.flip();
            String msg = new String(buffer.array());
            System.out.println(msg);
            socketChannel.write(buffer);
        }

    }
}
