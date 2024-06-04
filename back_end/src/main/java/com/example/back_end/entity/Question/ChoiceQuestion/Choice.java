package com.example.back_end.entity.Question.ChoiceQuestion;

import lombok.Data;

@Data
public class Choice {
    // 选项 ID
    private int choiceId;
    // 选项顺序
    private int sequenceId;
    // 选项内容
    private String content;
    // 选项状态 （是否被选）
    private Boolean state;

    public Choice() {
        state = Boolean.FALSE;
    }
}
