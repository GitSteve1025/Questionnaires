package com.example.back_end.service;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InfoService {
    // 添加 blank info
    Integer insertBlankInfo(Blank blank);
    // 删除 blank
    Integer deleteBlankInfo(int blankId);

    // 添加 choice info
    Integer insertChoiceInfo(Choice choice);
    // 删除 choice
    Integer deleteChoiceInfo(int choiceId);

    // 添加 questionnaire info
    Integer insertQuestionnaireInfo(Account account, Questionnaire questionnaire);
    // 删除 questionnaire
    Integer deleteQuestionnaireInfo(int questionnaireId);
    // 获取选项选择次数
    Integer getChoiceInfo(int choiceId);
    // 获取填空题填写的内容表
    List<String> getBlankInfo(int blankId);
    // 获取问卷填写者和填写时间的二元组
    Map<Account, Date> getQuestionnaireInfo(int questionnaireId);
}
