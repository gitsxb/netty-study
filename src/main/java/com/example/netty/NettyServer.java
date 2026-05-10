package com.example.netty;

import cn.hutool.core.util.ReflectUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.springframework.util.ReflectionUtils;
import sun.misc.Unsafe;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public class NettyServer {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = ReflectUtil.newInstance(Unsafe.class);
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(4);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(8);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boosGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println("pipeline："+ctx.pipeline().hashCode()+"; handler:"+this.hashCode());
                                ByteBuf bf = (ByteBuf) msg;
                                System.out.println(bf.toString(StandardCharsets.UTF_8));
                            }

                            @Override
                            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                Channel channel = ctx.channel();
                                System.out.println("channelReadComplete  channelHash="+ channel.hashCode());
                                System.out.println(NettyServer.sdf.format(new Date()) +"--喵喵喵2: "+Thread.currentThread().getId());
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("................");
                            }
                        });
                    }
                });
        ChannelFuture future = bootstrap.bind(6665).sync();
        future.channel().closeFuture();
        Future<Integer> future1 = boosGroup.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 30;
            }
        });
    }
}
