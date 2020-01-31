package com.example.lucenedemo.analyzer;

import com.example.lucenedemo.ik.IKAnalyzer8x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class ExtDicTest {
    public static final String str = "厉害了我的哥，中国环保部门即将发布治理北京雾霾的办法！";

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new IKAnalyzer8x(true);
        StringReader reader = new StringReader(str);
        TokenStream tokenStream = analyzer.tokenStream(str, reader);
        tokenStream.reset();

        CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.print(termAttribute.toString() + "|");
        }

        analyzer.close();
    }
}
