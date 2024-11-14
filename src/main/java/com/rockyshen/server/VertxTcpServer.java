package com.rockyshen.server;

/**
 * @author rockyshen
 * @date 2024/11/14 11:43
 */


import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;

/**
 * @author rockyshen
 * @date 2024/11/14 11:22
 * 改为基于TCP协议的服务器，实现HttpServer接口
 */
public class VertxTcpServer implements Server {

    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();
        // 这个就是TCP服务器啦！牛逼
        NetServer server = vertx.createNetServer();

        // 处理请求
        server.connectHandler(netSocket -> {
            netSocket.handler(buffer -> {
                // 请求到本TCP服务器的requestData数据
                byte[] requestData = buffer.getBytes();
                // 处理handler，跳到外面的独立方法中
                byte[] responseData = handleRequest(requestData);
                // 把响应数据发回给client
                netSocket.write(Buffer.buffer(responseData));
            });
        });

        server.listen(port, netServerAsyncResult -> {
            // 监听端口，是否成功
            if(netServerAsyncResult.succeeded()){
                System.out.println("TCP server started on port " + port);
            }else {
                System.out.println("Failed to start TCP Server: " + netServerAsyncResult.cause());
            }
        });
    }

    private byte[] handleRequest(byte[] requestData){
//        System.out.println(requestData.toString());
        return "Hello,Client".getBytes();
    }

    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}
