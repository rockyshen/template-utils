package com.rockyshen.utils;

/**
 * 和JSON相关的工具
 * @author rockyshen
 * @date 2026/4/27 10:28
 */
public class JsonUtil {

    /**
     * 解决：大模型JSON字符串转义边界的经典陷阱
     * 当`content` 源头就已经是"被转义过的字面量"时，客户端的`JSON.stringify`会再转义一轮，Jackson 只会反转义一轮
     * 于是服务端拿到的字符串比预期多了一层`\`——钉钉按字面渲染，markdown 就全废了。
     * @param content
     * @return
     */
    static String normalizeJsonEscapedMarkdown(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        if (!looksJsonEscaped(content)) {
            return content;
        }

        int n = content.length();
        StringBuilder out = new StringBuilder(n);
        int i = 0;
        while (i < n) {
            char c = content.charAt(i);
            if (c != '\\' || i == n - 1) {
                out.append(c);
                i++;
                continue;
            }
            char next = content.charAt(i + 1);
            switch (next) {
                case 'n':
                    out.append('\n');
                    i += 2;
                    break;
                case 'r':
                    out.append('\r');
                    i += 2;
                    break;
                case 't':
                    out.append('\t');
                    i += 2;
                    break;
                case '"':
                    out.append('"');
                    i += 2;
                    break;
                case '\\':
                    out.append('\\');
                    i += 2;
                    break;
                case 'u':
                    if (i + 6 <= n && isHex4(content, i + 2)) {
                        int code = Integer.parseInt(content.substring(i + 2, i + 6), 16);
                        out.append((char) code);
                        i += 6;
                    } else {
                        out.append('\\');
                        i += 1;
                    }
                    break;
                default:
                    out.append('\\');
                    i += 1;
                    break;
            }
        }
        return out.toString();
    }

    /**
     * 启发式检测：字符串中是否包含任一 JSON 字面转义序列。
     */
    private static boolean looksJsonEscaped(String s) {
        int n = s.length();
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) != '\\') {
                continue;
            }
            char next = s.charAt(i + 1);
            if (next == 'n' || next == 'r' || next == 't' || next == '"' || next == '\\') {
                return true;
            }
            if (next == 'u' && i + 6 <= n && isHex4(s, i + 2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isHex4(String s, int start) {
        if (start + 4 > s.length()) {
            return false;
        }
        for (int k = 0; k < 4; k++) {
            char ch = s.charAt(start + k);
            boolean ok = (ch >= '0' && ch <= '9')
                    || (ch >= 'a' && ch <= 'f')
                    || (ch >= 'A' && ch <= 'F');
            if (!ok) {
                return false;
            }
        }
        return true;
    }
}
