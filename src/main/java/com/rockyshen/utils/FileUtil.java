package com.rockyshen.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 针对文件和文件夹的常用方法
 * @author rockyshen
 * @date 2025/9/27 16:53
 */
public class FileUtil {
    // 基于根文件夹，收集根文件夹下所有的文件，收集成Map
    // fileName : file对象
    public static Map<String, Object> collectToMap(File rootDir){
        Map<String, Object> map = new HashMap<>();
        File[] files = rootDir.listFiles();
        if(files != null){
            for (File file : files){
                if(file.isDirectory()){
                    map.put(file.getName(), collectToMap(file));
                }else {
                    map.put(file.getName(), file.getName());
                }
            }
        }
        return map;
    }

    // 基于上一个方法提供的Map，反推成一棵文件树形结构
    // 同时提供一个文件过滤器，可以指定收集特定类型的文件
    public static void reverseFromMap(Map<String, Object> map, File targetDir){
        if(!targetDir.exists()){
            targetDir.mkdirs();
        }

        // 遍历每一个key:value
        for (Map.Entry<String, Object> entry : map.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            File newFile = new File(targetDir, key);
            // value是Map，证明是嵌套，是一个文件夹，需要递归
            if(value instanceof Map<?,?>){
                reverseFromMap( (Map<String, Object>)value, newFile );
            } else if (value instanceof String) {   // 如果value是一个String，证明是一个文件名
                try (FileWriter writer = new FileWriter(newFile)){
                    writer.write((String) value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // 基于根文件夹，无视层级结构，收集成一个List
    public static List<File> collectToList(File rootDir){
        List<File> fileList = new ArrayList<>();
        File[] files = rootDir.listFiles();
        // 创建一个FileNameFilter来过滤文件
        FilenameFilter pdfFilter = (dir, name) -> name.toLowerCase().endsWith(".pdf");
        if(files != null){
            for (File file : files){
                if(file.isDirectory()){
                    fileList.addAll(collectToList(file));
                    // 这里运用文件过滤器，只有符合文件过滤条件的，才会最终被加入列表
                }else if(pdfFilter.accept(file.getParentFile(), file.getName())) {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    // 扁平化复制文件：基于一个根路径，提取里面的文件，不保留层级结构，将所有文件复制到目标路径下
    public static void flatCopy(File sourceDir, File targetDir){
        // 判空是好习惯
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        // 遍历源目录
        try (Stream<Path> files = Files.walk(sourceDir.toPath())) {
            files
                // 过滤掉目录，只保留文件
                .filter(path -> !Files.isDirectory(path))
                .forEach(file -> {
                    try {
                        // 复制文件到目标目录，使用源文件的名称
                        Files.copy(file,
                            targetDir.toPath().resolve(file.getFileName()),
                            StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace(); // 打印错误信息
                    }
                });
        } catch (IOException e) {
            e.printStackTrace(); // 捕捉并处理遍历目录可能出现的IO异常
        }
    }
}
