package com.example.netty.chart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerChart {
    private int port;

    public ServerChart(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup boosGourp = new NioEventLoopGroup(1);
        EventLoopGroup workGourp = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boosGourp, workGourp)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new ServerChartHandler());
                    }
                });
        ChannelFuture future = serverBootstrap.bind(port).sync();
        future.channel().closeFuture();
    }

    public static void main(String[] args) throws InterruptedException {
        new ServerChart(8000).run();
    }

}
