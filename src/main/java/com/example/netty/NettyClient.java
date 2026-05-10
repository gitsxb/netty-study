package com.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(loopGroup)
                .remoteAddress("localhost", 6665)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                Channel channel = ctx.channel();
                                System.out.println("clinet channelActive channelHashCode=" + channel.hashCode());
                                ctx.writeAndFlush(Unpooled.wrappedBuffer("你好！我是客户端发来的消息".getBytes("UTF-8")));
                                ctx.writeAndFlush(Unpooled.wrappedBuffer("你好！我是客户端发来的消息0".getBytes("UTF-8")));
                                ctx.writeAndFlush(Unpooled.wrappedBuffer("你好！我是客户端发来的消息1".getBytes("UTF-8")));
                                ctx.writeAndFlush(Unpooled.wrappedBuffer("你好！我是客户端发来的消息2".getBytes("UTF-8")));
                            }
                        });
                    }
                })
                ;
        ChannelFuture future = bootstrap.connect().sync();
        future.channel().closeFuture();
    }
}
