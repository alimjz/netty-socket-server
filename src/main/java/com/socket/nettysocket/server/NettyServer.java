package com.socket.nettysocket.server;

import com.socket.nettysocket.decoder.RequestDecoder;
import com.socket.nettysocket.encoder.ResponseEncoder;
import com.socket.nettysocket.handler.ProcessHandler;
import com.socket.nettysocket.model.RequestData;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@Slf4j
public class NettyServer {
    private int port =9090;

    @PostConstruct
    private void init(){
        NioEventLoopGroup parent = new NioEventLoopGroup();
        NioEventLoopGroup child = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parent, child)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RequestDecoder(),
                                    new ResponseEncoder(), new ProcessHandler());
                            log.info("Channel Initiated.");
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }
}
