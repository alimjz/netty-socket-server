package com.socket.nettysocket.decoder;

import com.socket.nettysocket.model.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestDecoder extends ReplayingDecoder<RequestData> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            RequestData requestData = new RequestData();
            requestData.setIntInput(byteBuf.readInt());
            int strValue = byteBuf.readInt();
            requestData.setStringValue(byteBuf.readCharSequence(strValue, StandardCharsets.UTF_8).toString());
            list.add(requestData);
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }
}
