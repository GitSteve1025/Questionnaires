package com.example.back_end.entity.Question;

import lombok.Data;

@Data
public class Question {
    // 问题 ID
    private int questionId;
    // 问题顺序
    private int sequenceId;
    // 问题 题目
    private String title;
    // 问题种类
    private Categories category;
    // 问题是否为必填项
    private Boolean necessary;
    // 问题填写状态
    private Boolean state;


}
