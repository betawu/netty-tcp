package com.souche.datadev.response;

import io.netty.buffer.ByteBuf;

/**
 * Created by chauncy on 2018/6/4.
 */
public interface Response {


    byte SUCCESS = 0;

    ByteBuf response();
}
