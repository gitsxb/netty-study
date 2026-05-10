package com.example.netty.chart;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerChartHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("["+channel.remoteAddress()+"]上线了.....");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("["+channel.remoteAddress()+"]离线了.....");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("["+channel.remoteAddress()+"]已经成功连接服务器！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("["+channel.remoteAddress()+"]已经断开连接服务器！\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Date date = new Date();
        Channel channel = ctx.channel();
        System.out.println("服务器转发了["+channel.remoteAddress()+"]的消息:"+msg+"\n");
        for (Channel ch : channelGroup) {
            if (channel == ch) {
                ch.writeAndFlush("[自己]"+sdf.format(date)+":" + msg+"\n");
            } else {
                ch.writeAndFlush("["+channel.remoteAddress()+"]"+sdf.format(date)+":"+msg+"\n");
            }
        }
    }
}
