package com.example.back_end.service;

import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;

import java.util.List;

public interface QuestionnaireService {
    // 获取创建该问卷的 userId
    Integer findUserIdOfQuestionnaire(int questionnaireId);
    // 检查问卷是否属于 account
    Boolean belongsToAccount(Account account, int questionnaireId);
    // 创建问卷
    String createQuestionnaire(Account account, Questionnaire questionnaire);
    // 删除问卷
    String deleteQuestionnaire(Account account, int questionnaireId);
    // 修改问卷
    String updateQuestionnaire(Account account, Questionnaire questionnaire);
    // 获取问卷
    Questionnaire findQuestionnaire(Account account, int questionnaireId);
    // 获取该用户所有问卷
    List<Questionnaire> getAllQuestionnaire(Account account);
    // 获取问卷题目数量
    Integer getNumberOfQuestionOfQuestionnaire(int questionnaireId);
}
