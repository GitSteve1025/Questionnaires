package com.example.back_end.entity.Question.ChoiceQuestion;

import com.example.back_end.entity.Question.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChoiceQuestion extends Question {
    // 选择题的选项
    private List<Choice> choices;
    // 已经选择选项的数量
    private int selectedCount;
    // 最少选择数
    private int minSelected;
    // 最多选择数
    private int maxSelected;

    public ChoiceQuestion() {
        choices = new ArrayList<>();
        minSelected = 0;
    }
}
