package com.souche.datadev.test.biz;

import com.souche.datadev.pack.Header;
import com.souche.datadev.pack.KMHeader;
import com.souche.datadev.request.ReportBathRequest;
import com.souche.datadev.test.AbstractDecoder;
import com.souche.datadev.utils.CodecUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by chauncy on 2018/6/6.
 */
public class TestBatchLocationParse extends AbstractDecoder {


    private String batchLocation = "7e0704012f0145314385390024000501003a000001000000080001cd7d01c207278128000000000002180606094227010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080101cd7d01c207278128000000000002180606094232010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080101cd7d01c207278128000000000000180606094258010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080001cd7d01c207278128000000000006180606094812010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080001cd7d01c207278128000000000006180606094828010400000000eb16000c00b28986040419179068187500060089ffffffff377e";
    private String expBatchLocation = "0704012f0145314385390024000501003a000001000000080001cd7d01c207278128000000000002180606094227010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080101cd7d01c207278128000000000002180606094232010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080101cd7d01c207278128000000000000180606094258010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080001cd7d01c207278128000000000006180606094812010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080001cd7d01c207278128000000000006180606094828010400000000eb16000c00b28986040419179068187500060089ffffffff37";

    private String expTransform = "0704012f0145314385390024000501003a000001000000080001cd7dc207278128000000000002180606094227010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080101cd7dc207278128000000000002180606094232010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080101cd7dc207278128000000000000180606094258010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080001cd7dc207278128000000000006180606094812010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080001cd7dc207278128000000000006180606094828010400000000eb16000c00b28986040419179068187500060089ffffffff37";
    private String expCutCrc = "0704012f0145314385390024000501003a000001000000080001cd7dc207278128000000000002180606094227010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080101cd7dc207278128000000000002180606094232010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080101cd7dc207278128000000000000180606094258010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080001cd7dc207278128000000000006180606094812010400000000eb16000c00b28986040419179068187500060089ffffffff003a000001000000080001cd7dc207278128000000000006180606094828010400000000eb16000c00b28986040419179068187500060089ffffffff";

//    @Test
//    public void testCutFlag() {
//        ByteBuf src = getByteBuf(batchLocation);
//        ByteBuf cutFlag = CodecUtils.getObject2(src);
//        String strCutFlag = ByteBufUtil.hexDump(cutFlag);
//        Assert.assertEquals(expBatchLocation, strCutFlag);
//    }
//
//    @Test
//    public void testTransform() {
//        ByteBuf src = getByteBuf(batchLocation);
//        ByteBuf cutFlag = CodecUtils.getObject2(src);
//        ByteBuf t = CodecUtils.reverseTransform(cutFlag);
//        String strT = ByteBufUtil.hexDump(t);
//        Assert.assertEquals(expTransform, strT);
//    }
//
//    @Test
//    public void testCutCrc() {
//        ByteBuf src = getByteBuf(batchLocation);
//        ByteBuf cutFlag = CodecUtils.getObject2(src);
//        ByteBuf t = CodecUtils.reverseTransform(cutFlag);
//        ByteBuf cutCrc = CodecUtils.cutCrc(t);
//        String strCutCrc = ByteBufUtil.hexDump(cutCrc);
//        Assert.assertEquals(expCutCrc, strCutCrc);
//
//    }

    @Test
    public void testGetObj() {
        ByteBuf src = getByteBuf(batchLocation);
        ByteBuf data = CodecUtils.getObject(src);
        Assert.assertEquals(expCutCrc, ByteBufUtil.hexDump(data));

    }


    @Test
    public void testParseBatchLocation() {


        ByteBuf src = getByteBuf(batchLocation);
        ByteBuf data = CodecUtils.getObject(src);
        Header header = new KMHeader(data);
        ReportBathRequest reportBathRequest = new ReportBathRequest(header,data);
        Assert.assertEquals(5,reportBathRequest.getDatas().size());
        Assert.assertEquals(5,reportBathRequest.getCount());
    }





}