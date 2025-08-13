package com.rockyshen.fileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rockyshen
 * @date 2025/8/13 22:23
 * 基于Map结构，在当前根目录下生成的文件夹和文件
 * 1、遍历Map<String, Object>
 * 2、如果value是Map，表示这是一个文件夹，递归创建其中内容
 * 3、如果value是字符串，就创建这个文件
 */
public class MapToFolder {
    public static void main(String[] args) {
        // 模拟你提供的 Map 结构
        Map<String, Object> folderMap = new HashMap<>();
        folderMap.put("file1.txt", "file1.txt");
        folderMap.put("file2.txt", "file2.txt");

        Map<String, Object> subFolderB = new HashMap<>();
        subFolderB.put("b2.txt", "b2.txt");
        subFolderB.put("b1.txt", "b1.txt");
        folderMap.put("subFolderB", subFolderB);

        Map<String, Object> subFolderA = new HashMap<>();
        subFolderA.put("a1.txt", "a1.txt");
        subFolderA.put("a2.txt", "a2.txt");
        folderMap.put("subFolderA", subFolderA);

        File targetDir = new File("/Users/shen/Desktop/test2");
        createFromMap(folderMap,targetDir);
    }

    private static void createFromMap(Map<String, Object> map, File targetDir){
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
                createFromMap( (Map<String, Object>)value, newFile );
            } else if (value instanceof String) {   // 如果value是一个String，证明是一个文件名
                try (FileWriter writer = new FileWriter(newFile)){
                    writer.write((String) value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
