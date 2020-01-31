package com.example.lucenedemo.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

public class VariousAnalyzers {
    public static final String str = "中华人民共和国简称中国，是一个有13亿人口的国家";

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = null;
        // 标准分词
        analyzer = new StandardAnalyzer();
        System.out.println("标准分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        System.out.println();

        // 空格分词
        analyzer = new WhitespaceAnalyzer();
        System.out.println("空格分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        System.out.println();

        // 简单分词
        analyzer = new SimpleAnalyzer();
        System.out.println("简单分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        System.out.println();

        // 二分分词
        analyzer = new CJKAnalyzer();
        System.out.println("二分分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        System.out.println();

        // 关键词分词
        analyzer = new KeywordAnalyzer();
        System.out.println("关键词分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        System.out.println();

        // 停用词分词
        analyzer = new StopAnalyzer(Paths.get("src", "main", "resources", "stopword.dic"));
        System.out.println("停用词分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        System.out.println();

        // 中文智能分词
        analyzer = new SmartChineseAnalyzer();
        System.out.println("中文智能分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        System.out.println();
    }

    public static void printAnalyzer(Analyzer analyzer) throws IOException {
        StringReader reader = new StringReader(str);
        TokenStream tokenStream = analyzer.tokenStream(str, reader);
        tokenStream.reset();

        CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        System.out.println("分词结果：");
        while (tokenStream.incrementToken()) {
            System.out.print(termAttribute.toString() + "|");
        }
        System.out.println();

        analyzer.close();
    }
}
