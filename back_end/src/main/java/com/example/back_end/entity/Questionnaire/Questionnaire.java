package com.example.back_end.entity.Questionnaire;

import com.example.back_end.controller.QuestionController;
import lombok.Data;

import java.util.Date;

@Data
public class Questionnaire {
    // 问卷 ID
    private int questionnaireId;
    // 问卷标题
    private String title;
    // 问卷简介
    private String description;
    // 问卷控制
    private QuestionController questionController;
    // 问卷状态
    State state;
    // 问卷创建时间
    Date createdTime;
    // 问卷开始时间
    Date startTime;
    // 问卷结束时间
    Date endTime;

    public Questionnaire() {
        this.state = State.UNPUBLISHED; // 默认未发布
        createdTime = new Date(); // 创建时间
    }
}
