package com.example.a1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer01 {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(6666));

        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking(false);


        new Thread(() -> {
            try {
                System.out.println("进入线程。。。。");
                Thread.sleep(5000);
                selector.wakeup();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("主线程select。。。。");
        selector.select();
        System.out.println("select 被唤醒");



        int i = selector.selectNow();
        serverSocketChannel.register(selector, 0);
        int i1 = selector.selectNow();


        //serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (selector.select(10000) == 0) {
                System.out.println("服务器未接收到任何连接：10秒");
                continue;
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接成功：" + socketChannel);
                }
                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    socketChannel.read(buffer);
                    System.out.println("客户端发来消息：" + new String(buffer.array(), "UTF-8"));
                    buffer.clear();
                }
                keyIterator.remove();
            }
        }
    }
}
