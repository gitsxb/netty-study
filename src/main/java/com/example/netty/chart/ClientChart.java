package com.example.netty.chart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ClientChart {

    private String host;
    private int port;

    public ClientChart(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new ClientChartHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
        Channel channel = channelFuture.channel();
        System.out.println("------------"+channel.remoteAddress()+"-----------------");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            channel.writeAndFlush(line+"\r\n");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ClientChart("localhost", 8000).run();
    }
}
