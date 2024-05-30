package com.example.back_end.service;

import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;

import java.util.List;

public interface QuestionnaireService {
    // 创建问卷
    String createQuestionnaire(Account account, Questionnaire questionnaire);
    // 修改问卷
    String updateQuestionnaire(Questionnaire questionnaire);
    // 删除问卷
    String deleteQuestionnaire(int questionnaireId);
    // 问卷创作者获取问卷
    Questionnaire createrFindQuestionnaire(Account account, int questionnaireId);
    // 其他人获取问卷
    Questionnaire getQuestionnaire(int questionnaireId);
    // 获取创建该问卷的 userId
    Integer userIdOfQuestionnaire(int questionnaireId);
    // 获取该用户所有问卷
    List<Questionnaire> getAllQuestionnaire(Account account);
}
