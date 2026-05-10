//package com.example.netty.http;
//
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.http.HttpServerCodec;
//
//public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
//    @Override
//    protected void initChannel(SocketChannel ch) throws Exception {
//        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast("codec", new HttpServerCodec());
//        pipeline.addLast("handlers", new MyHandler());
//    }
//}
