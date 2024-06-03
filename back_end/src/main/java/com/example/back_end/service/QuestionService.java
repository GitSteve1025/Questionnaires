package com.example.back_end.service;

import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.auth.Account;

import java.util.List;

public interface QuestionService {
    // 获取该题创建者的ID
    Integer getUserIdOfQuestion(Integer questionId);
    // 获取问题的问卷 ID
    Integer getQuestionnaireIdOfQuestion(Integer questionId);
    // 检查是否属于 account
    Boolean belongsToQuestion(Account account, Integer questionId);
    // 删除问题
    String deleteQuestion(Account account, Integer questionId);
    // 修改问题
    String updateQuestion(Account account, Question question);
    // 获取问题
    Question getQuestion(Account account, Integer questionId);
    // 获取所有问题
    List<Question> getAllQuestions(Account account, Integer questionnaireId);
}
