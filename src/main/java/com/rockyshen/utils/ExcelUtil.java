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
    public static void processExcel(String inputExcel) {
        // 读取excel中的每一行记录
//        String inputExcel = "/Users/junjie.shen/Desktop/test.xlsx";

        try (FileInputStream fis = new FileInputStream(inputExcel);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) { // 跳过表头
                // 👇循环读取Excel的每一行
                Row row = sheet.getRow(i);
                if (row == null) {continue;}
                Cell filePathCell = row.getCell(0);
                Cell knowNameCell = row.getCell(1);
                if (filePathCell == null || knowNameCell == null) {continue;}

                // 👇读取每一行中单元格的信息，提取出来
                String filePath = filePathCell.getStringCellValue();

                // 拿到信息后，传入自定义方法进行处理
                // method(filePath)...

                // 结果回填到excel的第二列
                Cell resultCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                resultCell.setCellValue("自定义方法拿到的结果");
            }
            // 保存修改后的Excel文件
            try (FileOutputStream fos = new FileOutputStream(inputExcel)) {
                workbook.write(fos);
            } finally {
                workbook.close();
                log.info("所有文件已处理完毕！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
