package com.example.back_end.service;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;

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
}
