package com.example.back_end.service.impl;

import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.QuestionnaireMapper;
import com.example.back_end.service.QuestionnaireService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Resource
    QuestionnaireMapper questionnaireMapper;

    // 创建问卷
    @Override
    public String createQuestionnaire(Account account, Questionnaire questionnaire) {
        if (questionnaireMapper.createQuestionnaire(account, questionnaire) > 0) {
            return null;
        } else {
            return "创建问卷失败";
        }
    }

    @Override
    public String updateQuestionnaire(Questionnaire questionnaire) {
        if (questionnaireMapper.updateQuestionnaire(questionnaire) > 0) {
            return null;
        } else {
            return "未知错误，请联系管理员";
        }
    }

    @Override
    public String deleteQuestionnaire(int questionnaireId) {
        if (questionnaireMapper.deleteQuestionnaire(questionnaireId) > 0) {
            return null;
        } else {
            return "未知错误，请联系管理员";
        }
    }

    @Override
    public Questionnaire createrFindQuestionnaire(Account account, int questionnaireId) {
        Questionnaire questionnaire =  questionnaireMapper.getQuestionnaire(questionnaireId);
        if (questionnaire == null) {
            return null;
        }
        Integer userId = questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
        if (userId != account.getId()) {// 不是创建者的问卷
            return null;
        }
        return questionnaire;
    }

    @Override
    public Questionnaire getQuestionnaire(int questionnaireId) {
        return questionnaireMapper.getQuestionnaire(questionnaireId);
    }

    @Override
    public Integer userIdOfQuestionnaire(int questionnaireId) {
        return questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
    }

    @Override
    public List<Questionnaire> getAllQuestionnaire(Account account) {
        return questionnaireMapper.getQuestionnaires(account);
    }
}
