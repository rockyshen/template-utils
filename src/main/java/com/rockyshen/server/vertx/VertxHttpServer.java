package com.rockyshen.server.vertx;

import io.vertx.core.Vertx;

/**
 * @author rockyshen
 * @date 2024/11/14 11:43
 */
public class VertxHttpServer implements Server{
    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 使用我们手写的处理请求、响应的逻辑  -> HttpServerHandler
        server.requestHandler(new HttpServerHandler());

        server.listen(port,result -> {
            if(result.succeeded()){
                System.out.println("Vertx Http Server正在监听端口："+ port);
            }else{
                System.out.println("启动Vertx Http Server失败"+ result.cause());
            }
        });
    }
}
