package com.rockyshen.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * 基于httpClient下载文件相关
 * @author rockyshen
 * @date 2025/9/27 17:27
 */
public class DownloadUtil {

    /**
     * 提供一个http下载链接，获取下载到的文件，并存放到提供的路径中
     * @param downloadUrl
     * @param dir
     */
    private static void downloadFileToDir(String downloadUrl, File dir){
        String fileName = "自定义文件名" + ".md";
        // 根据父目录文件+子路径构建
        File file = new File(dir, fileName);
        try (BufferedInputStream in = new BufferedInputStream(new URL(downloadUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
