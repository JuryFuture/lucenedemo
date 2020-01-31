package com.example.lucenedemo.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class IKAnalyzer8x extends Analyzer {

    private boolean useSmart;

    public boolean isUseSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public IKAnalyzer8x() {
        // IK 分词器Lucene Analyzer 接口实现类；
        // 默认细粒度切分算法
        this(false);
    }

    /**
     * IK 分词器Lucene Analyzer 接口实现类；
     * 当为true时，分词器进行智能切分
     * @param useSmart
     */
    public IKAnalyzer8x(boolean useSmart) {
        super();
        this.useSmart = useSmart;
    }

    /**
     * Creates a new {@link TokenStreamComponents} instance for this analyzer.
     *
     * @param fieldName the name of the fields content passed to the
     *                  {@link TokenStreamComponents} sink as a reader
     * @return the {@link TokenStreamComponents} for this analyzer.
     */
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer _IKTokenizer = new IKTokenizer8x(this.useSmart);

        return new TokenStreamComponents(_IKTokenizer);
    }
}
