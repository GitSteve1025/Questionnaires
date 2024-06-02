package com.example.back_end.entity.Question.BlankQuestion;

import lombok.Data;

@Data
public class Blank {
    // blank ID
    private int blankId;
    // 填空内容
    private String content;
    // 状态 是否正确填写
    private Boolean state;
    // 最少字数
    private int minCount;
    // 最多字数
    private int maxCount;

    public Blank() {
        state = Boolean.FALSE;
        minCount = 0;
        maxCount = Integer.MAX_VALUE;
    }
}
