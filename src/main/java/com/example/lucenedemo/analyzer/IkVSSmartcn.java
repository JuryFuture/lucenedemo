package com.example.lucenedemo.analyzer;

import com.example.lucenedemo.ik.IKAnalyzer8x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class IkVSSmartcn {
    public static final String str1 = "公路局正在治理解放大道路面积水问题。";
    public static final String str2 = "IKAnalyzer 是一个开源的，基于 java 语言开发的轻量级的中文分词工具包";

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = null;

        System.out.println("句子一：" + str1);
        System.out.println("SmartChineseAnalyzer 分词结果：");
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer, str1);

        System.out.println("IKAnalyzer 分词结果：");
        analyzer = new IKAnalyzer8x(true);
        printAnalyzer(analyzer, str1);

        System.out.println("-------------------------------");

        System.out.println("句子二：" + str2);
        System.out.println("SmartChineseAnalyzer 分词结果：");
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer, str2);

        System.out.println("IKAnalyzer 分词结果：");
        analyzer = new IKAnalyzer8x(true);
        printAnalyzer(analyzer, str2);

        analyzer.close();
    }

    public static void printAnalyzer(Analyzer analyzer, String str) throws IOException {
        StringReader reader = new StringReader(str);
        TokenStream tokenStream = analyzer.tokenStream(str, reader);
        tokenStream.reset();

        CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.print(termAttribute.toString() + "|");
        }

        System.out.println();
    }
}
