package com.example.back_end.entity.Question.ChoiceQuestion;

import com.example.back_end.entity.Question.Categories;
import lombok.Data;

@Data
public class MultipleChoiceQuestion extends ChoiceQuestion {

    // 初始化多选题
    MultipleChoiceQuestion() {
        setCategory(Categories.SINGLE_CHOICE_QUESTION); // 设置类型为多选
        setState(false); // 问题未填写
    }



    // 转换为单选题 改变 minSelected, maxSelected, category 即可
    void transformToSingleChoice() {
        // to do



    }

}
