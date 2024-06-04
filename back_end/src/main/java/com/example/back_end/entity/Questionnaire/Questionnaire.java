package com.example.back_end.entity.Questionnaire;

import com.example.back_end.entity.Question.BlankQuestion.BlankQuestion;
import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Questionnaire {
    // 问卷 ID
    private int questionnaireId;
    // 问卷标题
    private String title;
    // 问卷简介
    private String description;
    // 选择题
    private ArrayList<ChoiceQuestion> choiceQuestions;
    // 填空题
    private ArrayList<BlankQuestion> blankQuestions;
    // 问卷状态
    State state;
    // 问卷创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date createdTime;
    // 问卷开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date startTime;
    // 问卷结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime;

    // 初始化
    public Questionnaire() {
        this.choiceQuestions = new ArrayList<>(); // 问题
        this.blankQuestions = new ArrayList<>();
        this.state = State.UNPUBLISHED; // 默认未发布
        this.createdTime = new Date(); // 创建时间
    }
}
