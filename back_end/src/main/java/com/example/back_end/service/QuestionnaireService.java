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
    // 获取问卷
    Questionnaire findQuestionnaire(int questionnaireId);
    // 获取该用户所有问卷
    List<Questionnaire> getAllQuestionnaire(Account account);
}
