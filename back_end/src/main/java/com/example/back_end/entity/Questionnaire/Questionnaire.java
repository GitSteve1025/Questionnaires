package com.example.back_end.entity.Questionnaire;

import com.example.back_end.controller.QuestionController;
import com.example.back_end.entity.Question.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Questionnaire {
    // 问卷 ID
    private int questionnaireId;
    // 问卷标题
    private String title;
    // 问卷简介
    private String description;
    // 问卷控制
    private List<Question> questions;
    // 问卷状态
    State state;
    // 问卷创建时间
    Date createdTime;
    // 问卷开始时间
    Date startTime;
    // 问卷结束时间
    Date endTime;

    // 初始化
    public Questionnaire() {
        this.questions = new ArrayList<>(); // 问题
        this.state = State.UNPUBLISHED; // 默认未发布
        this.createdTime = new Date(); // 创建时间
    }
}
