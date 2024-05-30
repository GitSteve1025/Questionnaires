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

    // 获取创建该问卷的 userId
    @Override
    public Integer findUserIdOfQuestionnaire(int questionnaireId) {
        return questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
    }

    // 检查问卷是否属于 account
    @Override
    public Boolean belongsToAccount(Account account, int questionnaireId) {
        Integer userId = findUserIdOfQuestionnaire(questionnaireId);
        if (userId == null || userId != account.getId()) { // 问卷不存在 或 问卷不属于 account
            return false;
        }
        return true;
    }

    // 创建问卷
    @Override
    public String createQuestionnaire(Account account, Questionnaire questionnaire) {
        if (questionnaireMapper.createQuestionnaire(account, questionnaire) > 0) {
            return null;
        } else {
            return "创建问卷失败";
        }
    }

    // 删除问卷
    @Override
    public String deleteQuestionnaire(Account account, int questionnaireId) {
        if (belongsToAccount(account, questionnaireId)) { // 属于 account
            if (questionnaireMapper.deleteQuestionnaire(questionnaireId) > 0) {
                return null;
            } else {
                return "未知错误，删除失败，请联系管理员";
            }
        }
        return "没有权限";
    }

    // 修改问卷
    @Override
    public String updateQuestionnaire(Account account, Questionnaire questionnaire) {
        if (belongsToAccount(account, questionnaire.getQuestionnaireId())) { // 属于 account
            if (questionnaireMapper.updateQuestionnaire(questionnaire) > 0) {
                return null;
            } else {
                return "未知错误，修改失败，请联系管理员";
            }
        }
        return "没有权限";
    }

    // 获取问卷
    @Override
    public Questionnaire findQuestionnaire(Account account, int questionnaireId) {
        if (belongsToAccount(account, questionnaireId)) { // 属于 account
            return questionnaireMapper.getQuestionnaire(questionnaireId);
        }
        return null;
    }

    // 获取该用户所有问卷
    @Override
    public List<Questionnaire> getAllQuestionnaire(Account account) {
        return questionnaireMapper.getQuestionnaires(account);
    }
}
