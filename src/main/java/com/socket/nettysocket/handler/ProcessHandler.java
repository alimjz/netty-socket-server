package com.socket.nettysocket.handler;

import com.socket.nettysocket.model.RequestData;
import com.socket.nettysocket.model.ResponseData;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            RequestData requestData = (RequestData) msg;
            ResponseData responseData = new ResponseData();
            responseData.setResponse(requestData.getIntInput() * 2);
            responseData.setText("Ali Pot");
            ChannelFuture channelFuture = ctx.writeAndFlush(responseData);
            channelFuture.addListener(ChannelFutureListener.CLOSE);
            log.info("Response Data is : {} ", responseData);
        } catch (Exception e) {
            log.error("an exception happened : ",e);
        }
    }
}
