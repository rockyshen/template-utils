package com.rockyshen.server;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;

/**
 * @author rockyshen
 * @date 2024/11/14 11:33
 * 测试接收VertxTcpServer的客户端
 */
public class VertxTcpClient {
    public void start(){
        Vertx vertx = Vertx.vertx();
        vertx.createNetClient().connect(8888,"localhost",netSocketAsyncResult -> {
            // 是否成功建立连接
            if(netSocketAsyncResult.succeeded()){
                System.out.println("Connected to TCP server");
                NetSocket socket = netSocketAsyncResult.result();
                socket.write("Hello,server!");
                socket.handler(buffer -> {
                    System.out.println("Received response from TCP server: "+ buffer.toString());
                });
            }else{
                System.out.println("Failed to connect to TCP server");
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpClient().start();
    }
}