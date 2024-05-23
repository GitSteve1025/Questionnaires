package com.example.back_end.entity.Question.BlankQuestion;

import com.example.back_end.entity.Question.Question;
import lombok.Data;

@Data
public class BlankQuestion extends Question {
    // 填空栏
    private Blank blank;
    // 填空是否开启类型验证
    private Boolean validation;
    // 数据验证的类型
    private Type type;
}
