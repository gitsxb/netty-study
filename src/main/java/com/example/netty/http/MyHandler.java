//package com.example.netty.http;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.*;
//import io.netty.util.CharsetUtil;
//
//public class MyHandler extends SimpleChannelInboundHandler<HttpObject> {
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
//        System.out.println("类信息："+ctx.channel().getClass());
//        System.out.println("客户端地址："+ctx.channel().remoteAddress());
//        if (msg instanceof HttpRequest) {
//            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器端", CharsetUtil.UTF_8);
//            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
//            response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
//            response.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
//            ctx.writeAndFlush(response);
//        }
//    }
//}
