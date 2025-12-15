package com.rockyshen.utils.luban;

import java.util.Iterator;
import java.util.List;

/**
 * CompareUtil对比对象之后的结果，封装到DiffItem中
 * DiffItem：{对象.属性, 对象1的值, 对象2的值}
 * @author rockyshen
 * @date 2025/12/15 10:57
 */
public class DiffItem {
    private String key;
    private String fieldValue1;
    private String fieldValue2;

    public DiffItem(String key, String fieldValue1) {
        this(key, fieldValue1, (String)null);
    }

    public DiffItem(String key, String fieldValue1, String fieldValue2) {
        this.key = key;
        this.fieldValue1 = fieldValue1;
        this.fieldValue2 = fieldValue2;
    }

    public static String toLogFormat(List<DiffItem> diffs) {
        if (EmptyUtil.isEmpty(diffs)) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            Iterator var2 = diffs.iterator();

            while(var2.hasNext()) {
                DiffItem diff = (DiffItem)var2.next();
                sb.append(diff.getKey()).append(":\r\n    A:");
                sb.append(diff.getFieldValue1()).append("\r\n    B:");
                sb.append(diff.getFieldValue2()).append("\r\n");
            }

            return sb.toString();
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFieldValue1() {
        return this.fieldValue1;
    }

    public void setFieldValue1(String fieldValue1) {
        this.fieldValue1 = fieldValue1;
    }

    public String getFieldValue2() {
        return this.fieldValue2;
    }

    public void setFieldValue2(String fieldValue2) {
        this.fieldValue2 = fieldValue2;
    }
}
