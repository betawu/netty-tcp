package com.souche.datadev.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by chauncy on 2018/5/30.
 */
public class KMClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private String tcpData = "7E 02 00 00 22 01 44 00 44 00 55 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 18 05 30 17 31 36 25 04 00 00 00 00 69 7E";


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//        for (int i = 0 ;i <10;i++){
            ctx.writeAndFlush(get());
//        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        for (int i = 0 ;i <10;i++){
            ctx.writeAndFlush(get());
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    private ByteBuf get() {
        ByteBuf byteBuf = Unpooled.buffer();
        for (String s : tcpData.split(" ")) {
            byteBuf.writeByte(hexStringToByteArray(s)[0]);
        }
        return byteBuf;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


//    public static void main(String[] args) {
//
//       String tcpData1 = "7E 02 00 00 22 01 44 00 44 00 55 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 18 05 30 17 31 36 25 04 00 00 00 00 69 7E";
//
//
//
//
//        byte[] a = hexStringToByteArray("02");
//
//    }


}