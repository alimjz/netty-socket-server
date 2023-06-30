package com.socket.nettysocket.encoder;

import com.socket.nettysocket.model.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class ResponseEncoder extends MessageToByteEncoder<ResponseData> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ResponseData responseData, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(responseData.getResponse());
        byteBuf.writeInt(responseData.getText().length());
        byteBuf.writeCharSequence(responseData.getText(), StandardCharsets.UTF_8);
    }
}
