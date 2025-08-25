package com.rockyshen.fileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    /**
     * 输入一个文件夹，递归查询下面所有的文件，返回一个List<File>
     * @param directory
     * @return
     */
    public static List<File> listAllFiles(File directory){
        List<File> fileList = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) { //确保目录不是空
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归地列出子文件夹中的文件
                    listAllFiles(file);
                } else {
                    fileList.add(file); // 收集文件
                }
            }
        }
        return fileList;
    }

    /**
     * 提供一个http下载链接，获取下载到的文件，并存放到提供的路径中
     * @param downloadUrl
     * @param folder
     */
    private static void fetchAndSave(String downloadUrl, File folder){
        String fileName = "自定义文件名" + ".md";
        // 根据父目录文件+子路径构建
        File file = new File(folder, fileName);
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
