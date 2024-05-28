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
            return "修改失败";
        }
    }

    @Override
    public String deleteQuestionnaire(int questionnaireId) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionnaireId(questionnaireId);
        if (questionnaireMapper.deleteQuestionnaire(questionnaire) > 0) {
            return null;
        } else {
            return "删除失败";
        }
    }

    @Override
    public Questionnaire findQuestionnaire(int questionnaireId) {
        return questionnaireMapper.getQuestionnaire(questionnaireId);
    }

    @Override
    public List<Questionnaire> getAllQuestionnaire(Account account) {
        return questionnaireMapper.getQuestionnaires(account);
    }
}
