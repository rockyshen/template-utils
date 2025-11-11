package com.rockyshen.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * 读取Excel中的每一行，作为参数输入，进一步处理
 * 例如：读取每一行的文件路径，进行文件拷贝等处理
 * @author rockyshen
 * @date 2025/11/11 10:04
 */

@Slf4j
public class ExcelUtil {
    public static void main(String[] args) {
        // 读取excel中的每一行记录
        String inputExcel = "/Users/junjie.shen/Desktop/test.xlsx";
        String ROOT_DIR   = "/Users/junjie.shen/Desktop/upload_maxkb_doc";
        try (FileInputStream fis = new FileInputStream(inputExcel);
            Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) { // 跳过表头
                Row row = sheet.getRow(i);
                if (row == null) {continue;}
                Cell filePathCell = row.getCell(0);
                Cell knowNameCell = row.getCell(1);
                if (filePathCell == null || knowNameCell == null) {continue;}

                String filePath = filePathCell.getStringCellValue();
                String knowName  = knowNameCell.getStringCellValue().trim();

                // 基于上面从Excel中提取到的信息，进行下一步自定义方法进行处理
                File src = new File(filePath);
                if (!src.exists()) {
                    log.info("源文件不存在");
                    continue;
                }
                File destDir = new File(ROOT_DIR, knowName);
                if (!destDir.exists()) {destDir.mkdirs();}
                File destFile = new File(destDir, src.getName());
                try {
                    Files.copy(src.toPath(), destFile.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);
                    log.info("拷贝完成：{} -> {}", src.getAbsolutePath(), destFile.getAbsolutePath());
                } catch (Exception e) {
                    log.error("拷贝失败：{}，原因：{}", filePath, e.getMessage());
                }
            }

            // 保存修改后的Excel文件
            try (FileOutputStream fos = new FileOutputStream(inputExcel)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
