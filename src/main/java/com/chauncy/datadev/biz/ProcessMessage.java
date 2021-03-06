package com.chauncy.datadev.biz;

import com.alibaba.fastjson.JSON;
import com.chauncy.datadev.mq.KafkaProperties;
import com.chauncy.datadev.mq.Sender;
import com.chauncy.datadev.pack.Header;
import com.chauncy.datadev.request.RegisterRequest;
import com.chauncy.datadev.request.ReportBathRequest;
import com.chauncy.datadev.request.ReportRequest;
import com.chauncy.datadev.response.CommonResponse;
import com.chauncy.datadev.response.RegisterResponse;
import com.chauncy.datadev.response.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chauncy on 2018/6/4.
 */
public class ProcessMessage {


    private final Logger logger = LoggerFactory.getLogger(ProcessMessage.class);

    private ChannelHandlerContext ctx;
    private Header header;
    private ByteBuf buf;

    public ProcessMessage(ChannelHandlerContext ctx, Header header, ByteBuf buf) {
        this.ctx = ctx;
        this.header = header;
        this.buf = buf;
    }

    public void doRegister() {

        RegisterRequest registerRequest = new RegisterRequest(header, buf);
        //处理响应的业务逻辑
        //判断是否已经注册过


        logger.info("register provinceId={},cityId={},vendorId={},type={},sn={}",
                registerRequest.getProvinceId(), registerRequest.getCityId(), registerRequest.getVendorId(), registerRequest.getTerminalType(), header.getNo());

        Response registerResponse = new RegisterResponse(header.getPhone(), header.getNo(), Response.STATUS_SUCCESS, new String("token"));


        ctx.writeAndFlush(registerResponse.response());

    }

    public void doTerminalAuth() {
        int length = header.getLength();
        byte[] token = new byte[length];
        buf.readBytes(token);
        logger.info("terminal auth length={},token={},phone={}", length, new String(token), header.getPhone());
        responseCommon();
    }

    public void doHeart() {
        responseCommon();
        logger.info("do heart done phone={}", header.getPhone());
    }

    public void doLocationReport() {
        ReportRequest reportRequest = new ReportRequest(header, buf);

        //todo send mq
        responseCommon();

        Sender.INSTANCE.send(KafkaProperties.TOPIC, reportRequest.getPhone(), JSON.toJSONString(reportRequest));
//        logger.info(JSON.toJSONString(reportRequest));

    }

    private void responseCommon() {
        Response response = new CommonResponse(header.getPhone(), header.getNo(), header.getId(), Response.STATUS_SUCCESS);
        ctx.write(response.response());
    }

    public void doLocationReportBath() {
        ReportBathRequest reportBathRequest = new ReportBathRequest(header, buf);

       // logger.info(JSON.toJSONString(reportBathRequest));
        responseCommon();
        for (ReportRequest reportRequest : reportBathRequest.getDatas()) {
            Sender.INSTANCE.send(KafkaProperties.TOPIC, reportRequest.getPhone(), JSON.toJSONString(reportRequest));
        }

    }
}
