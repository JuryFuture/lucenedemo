package com.example.lucenedemo.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.Reader;

public class IKTokenizer8x extends Tokenizer {
    // IK分词器实现
    private IKSegmenter _IKImplement;

    // 词元文本属性
    private final CharTermAttribute termAttribute;

    // 词元位移属性
    private final OffsetAttribute offsetAttribute;

    // 词元分类属性
    // （该属性参考org.wltea.analyzer.core.Lexeme 中的分类常量）
    private final TypeAttribute typeAttribute;

    // 记录最后一个词元的结束位置
    private int endPosition;

    /**
     * Construct a tokenizer with no input, awaiting a call to {@link #setReader(Reader)}
     * to provide input.
     */
    public IKTokenizer8x(boolean useSmart) {
        super();
        termAttribute = addAttribute(CharTermAttribute.class);
        offsetAttribute = addAttribute(OffsetAttribute.class);
        typeAttribute = addAttribute(TypeAttribute.class);
        _IKImplement = new IKSegmenter(input, useSmart);
    }

    /**
     * Consumers (i.e., {@link IndexWriter}) use this method to advance the stream to
     * the next token. Implementing classes must implement this method and update
     * the appropriate {@link AttributeImpl}s with the attributes of the next
     * token.
     * <p>
     * The producer must make no assumptions about the attributes after the method
     * has been returned: the caller may arbitrarily change it. If the producer
     * needs to preserve the state for subsequent calls, it can use
     * {@link #captureState} to create a copy of the current attribute state.
     * <p>
     * This method is called for every token of a document, so an efficient
     * implementation is crucial for good performance. To avoid calls to
     * {@link #addAttribute(Class)} and {@link #getAttribute(Class)},
     * references to all {@link AttributeImpl}s that this stream uses should be
     * retrieved during instantiation.
     * <p>
     * To ensure that filters and consumers know which attributes are available,
     * the attributes must be added during instantiation. Filters and consumers
     * are not required to check for availability of attributes in
     * {@link #incrementToken()}.
     *
     * @return false for end of stream; true otherwise
     */
    @Override
    public boolean incrementToken() throws IOException {
        // 清清除所有的词元属性
        clearAttributes();

        Lexeme nextLexeme = _IKImplement.next();
        if (nextLexeme != null) {
            // 设置词元文本
            termAttribute.append(nextLexeme.getLexemeText());

            // 设置词元长度
            termAttribute.setLength(nextLexeme.getLength());

            // 设置词元位移
            offsetAttribute.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());

            // 记录分词的最后位置
            endPosition = nextLexeme.getEndPosition();

            // 设置词元分类
            typeAttribute.setType(nextLexeme.getLexemeTypeString());

            return true;
        }

        return false;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        _IKImplement.reset(input);
    }

    @Override
    public void end() {
        int finalOffset = correctOffset(this.endPosition);
        offsetAttribute.setOffset(finalOffset, finalOffset);
    }
}
