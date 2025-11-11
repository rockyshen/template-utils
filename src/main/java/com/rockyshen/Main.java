package com.rockyshen;

import com.rockyshen.result.Result;
import com.rockyshen.result.StatusCodeEnum;
import com.rockyshen.utils.FileUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

//        Map<String, Object> fileMap = FileUtil.collectToMap(new File("/Users/shen/Desktop/测试"));
//        System.out.println(fileMap);

//        List<File> files = FileUtil.collectToList(new File("/Users/shen/Desktop/测试"));
//        System.out.println(files);

        FileUtil.flatCopy(new File("/Volumes/data-file/车联网知识库"), new File("/Volumes/wendangdata/maxkb/车联网知识库"));
    }
}