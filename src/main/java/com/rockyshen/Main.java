package com.rockyshen;

import com.rockyshen.fileUtils.FileUtils;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File dir = new File("/Users/junjie.shen/Desktop/IT");
        List<File> fileList = FileUtils.listAllFiles(dir);
        System.out.println("===");
    }
}