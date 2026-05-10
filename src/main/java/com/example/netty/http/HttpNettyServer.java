package com.example.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;

public class HttpNettyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.channel(NioServerSocketChannel.class)
                    .group(boosGroup, workGroup)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("mycodec", new HttpServerCodec());
                            pipeline.addLast("myhandler", new SimpleChannelInboundHandler<HttpObject>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
                                    Channel channel = ctx.channel();
                                    if (msg instanceof DefaultHttpRequest) {
                                        System.out.println("来自客户端：【"+channel.remoteAddress()+"】的请求");
                                        ByteBuf content = Unpooled.wrappedBuffer("我是来自服务器端的数据：".getBytes("UTF-8"));
                                        DefaultHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,
                                                HttpResponseStatus.OK, content);
                                        response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain;Charset=UTF-8");
                                        response.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                                        ctx.writeAndFlush(response);
                                    }
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.bind(7000).sync();
            future.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
