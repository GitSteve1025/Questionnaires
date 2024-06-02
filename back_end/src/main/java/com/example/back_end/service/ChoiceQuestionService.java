package com.example.back_end.service;


import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;

public interface ChoiceQuestionService {
    // 获取该选择题创建者的ID
    Integer getUserIdOfChoiceQuestion(int choiceQuestionId);
    // 检查是否属于 account
    Boolean belongsToAccount(Account account, int choiceQuestionId);
    // 添加选择题
    String createChoiceQuestion(Questionnaire questionnaire, ChoiceQuestion choiceQuestion);
    // 删除选择题
    String deleteChoiceQuestion(Account account, int choiceQuestionId);
    // 修改选择题
    String updateChoiceQuestion(Account account, ChoiceQuestion choiceQuestion);
    // 获取选择题
    ChoiceQuestion findChoiceQuestion(Account account, int choiceQuestionId);
    // 删除所有选项
    String deleteChoices(Account account, int choiceQuestionId);
}
