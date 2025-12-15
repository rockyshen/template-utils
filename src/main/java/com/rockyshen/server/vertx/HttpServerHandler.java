package com.rockyshen.server.vertx;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author rockyshen
 * @date 2024/11/1 13:22
 * 接收web服务器的请求，并响应结果
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {


    @Override
    public void handle(HttpServerRequest request) {
        // 补充处理请求，返回响应的具体逻辑
    }
}
