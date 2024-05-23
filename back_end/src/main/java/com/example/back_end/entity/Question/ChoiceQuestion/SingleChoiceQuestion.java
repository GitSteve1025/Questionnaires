package com.example.back_end.entity.Question.ChoiceQuestion;

import lombok.Data;

@Data
public class SingleChoiceQuestion extends ChoiceQuestion {

    // 转换为多选题 改变 minSelected, maxSelected, category 即可
    public void transformToMultipleChoice() {
        // to do
    }
}
