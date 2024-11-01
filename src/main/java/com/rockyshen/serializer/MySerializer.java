package com.rockyshen.serializer;

import java.io.*;

/**
 * @author rockyshen
 * @date 2024/11/1 09:27
 * 基于ByteArrayInputStream 和 ByteArrayOutputStream实现的序列化与反序列化器
 * 将对象数据暂存到内存中的！需要的时候再从内存中读出来，转成对象使用
 */
public class MySerializer implements Serializer{
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(object);    // 将对象写入内存中
        oos.close();
        return byteArrayOutputStream.toByteArray();      // 顺便把这个写入内存的byte型数组返回出去
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
        try {
            return (T)ois.readObject();           // 反序列化的对象，返回出去，类型强转
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            ois.close();
        }
    }
}
