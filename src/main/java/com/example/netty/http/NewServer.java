package com.example.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class NewServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("codec", new HttpServerCodec());
                            pipeline.addLast("handlers", new SimpleChannelInboundHandler<Object>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ChannelPipeline pipeline1 = ctx.pipeline();
                                    System.out.println("pipeline:" + ctx.pipeline().hashCode()+"; handler:"+this.hashCode());
                                    System.out.println("类信息："+ctx.channel().getClass());
                                    System.out.println("客户端地址："+ctx.channel().remoteAddress());
                                    if (msg instanceof HttpRequest) {
                                        ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器端", CharsetUtil.UTF_8);
                                        //HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                                        //response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                                        //response.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                                        ctx.writeAndFlush(content);
                                    }
                                }
                            });
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
