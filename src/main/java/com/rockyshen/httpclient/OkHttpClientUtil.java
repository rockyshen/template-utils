package com.rockyshen.httpclient;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * okhttpClient工厂
 * @author rockyshen
 * @date 2025/9/16 10:57
 */
public class OkHttpClientUtil {

    private static OkHttpClient okHttpClient;

    private OkHttpClientUtil() {
        // 私有构造函数，防止实例化
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpClientUtil.class) {
                if (okHttpClient == null) {
                    // 创建一个 HTTP 日志拦截器
//                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    // 配置 OkHttpClient
                    okHttpClient = new OkHttpClient.Builder()
//                            .addInterceptor(loggingInterceptor)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .build();
                }
            }
        }
        return okHttpClient;
    }
}
