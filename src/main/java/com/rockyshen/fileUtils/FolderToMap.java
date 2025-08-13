package com.rockyshen.fileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rockyshen
 * @date 2025/8/13 22:00
 * 基于当前根文件下的文件夹和文件，生成Map结构
 */
public class FolderToMap {

    public static void main(String[] args) {
        File rootFolder = new File("/Users/shen/Desktop/test");
        Map<String, Object> folderToMap = folderToMap(rootFolder);
        System.out.println(folderToMap);
    }

    private static Map<String, Object> folderToMap(File folder){
        Map<String, Object> map = new HashMap<>();

        File[] files = folder.listFiles();

        if(files != null){
            for (File file : files){
                if(file.isDirectory()){
                    map.put(file.getName(), folderToMap(file));
                }else {
                    map.put(file.getName(), file.getName());
                }
            }
        }

        return map;
    }
}
