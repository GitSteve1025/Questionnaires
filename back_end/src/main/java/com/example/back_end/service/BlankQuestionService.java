package com.example.back_end.service;

import com.example.back_end.entity.Question.BlankQuestion.BlankQuestion;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;

public interface BlankQuestionService {
    // 获取该填空题创建者的ID
    Integer getUserIdOfBlankQuestion(int blankQuestionId);
    // 检查是否属于 account
    Boolean belongsToAccount(Account account, int blankQuestionId);
    // 添加填空题
    String createBlankQuestion(Questionnaire questionnaire, BlankQuestion blankQuestion);
    // 删除填空题
    String deleteBlankQuestion(Account account, int blankQuestionId);
    // 修改填空题
    String updateBlankQuestion(Account account, BlankQuestion blankQuestion);
    // 获取填空题
    BlankQuestion findBlankQuestion(Account account, int blankQuestionId);
}
