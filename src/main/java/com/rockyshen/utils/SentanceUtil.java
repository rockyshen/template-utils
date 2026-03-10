package com.rockyshen.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入一篇文章，按照我指定的一句话边界，按句输出！
 * SENTENCE_BOUNDARIES 定义（。，、？！；：…— 及换行、英文 .!?;: 等）。
 *
 * @author rockyshen
 * @date 2026/3/10 11:05
 */
public class SentanceUtil {
    private static final String SENTENCE_BOUNDARIES = "。，、？！；：…—\n\r.!?;:";

    /**
     * 将一篇文章按句边界拆成多句，按句返回。
     * 复用 findLastSentenceBoundBefore / findNextSentenceBoundAfter 确定每句的起止位置。
     */
    public static List<String> parseSentence(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        int len = text.length();
        List<String> sentences = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            if (SENTENCE_BOUNDARIES.indexOf(text.charAt(i)) >= 0) {
                int sentenceStart = findLastSentenceBoundBefore(text, len, i);
                int sentenceEnd = findNextSentenceBoundAfter(text, len, i);
                String sentence = text.substring(sentenceStart, sentenceEnd).trim();
                if (!sentence.isEmpty()) {
                    sentences.add(sentence);
                }
            }
        }
        // 末尾无边界符的一段也算一句
        int tailStart = findLastSentenceBoundBefore(text, len, len);
        if (tailStart < len) {
            int tailEnd = findNextSentenceBoundAfter(text, len, tailStart);
            String tail = text.substring(tailStart, tailEnd).trim();
            if (!tail.isEmpty()) {
                sentences.add(tail);
            }
        }
        return sentences;
    }

    /** 从 start 向前查找最近一句的起始位置（上一个边界符之后），不含 start */
    private static int findLastSentenceBoundBefore(String text, int len, int start) {
        for (int i = start - 1; i >= 0; i--) {
            if (SENTENCE_BOUNDARIES.indexOf(text.charAt(i)) >= 0) {
                return i + 1;
            }
        }
        return 0;
    }

    /** 从 end 向后查找最近一句的结束位置（下一个边界符含在内），不含 end 前字符 */
    private static int findNextSentenceBoundAfter(String text, int len, int end) {
        for (int i = end; i < len; i++) {
            if (SENTENCE_BOUNDARIES.indexOf(text.charAt(i)) >= 0) {
                return i + 1;
            }
        }
        return len;
    }

    // 测试
    public static void main(String[] args) {
        List<String> strings = SentanceUtil.parseSentence("哈哈哈，你是谁。我是快乐星球！");
        strings.forEach(System.out::println);
    }
}
