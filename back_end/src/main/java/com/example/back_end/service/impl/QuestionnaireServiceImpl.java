package com.example.back_end.service.impl;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.BlankQuestion.BlankQuestion;
import com.example.back_end.entity.Question.BlankQuestion.Type;
import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.QuestionMapper;
import com.example.back_end.mapper.QuestionnaireMapper;
import com.example.back_end.service.QuestionnaireService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Resource
    QuestionnaireMapper questionnaireMapper;

    @Resource
    QuestionMapper questionMapper;

    // 获取创建该问卷的 userId
    @Override
    public Integer getUserIdOfQuestionnaire(int questionnaireId) {
        return questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
    }

    // 检查问卷是否属于 account
    @Override
    public Boolean belongsToAccount(Account account, int questionnaireId) {
        Integer userId = this.getUserIdOfQuestionnaire(questionnaireId);
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
    @Override
    public Questionnaire findQuestionnaire(int questionnaireId) {
        return questionnaireMapper.getQuestionnaire(questionnaireId);
    }
    // 获取该用户所有问卷
    @Override
    public List<Questionnaire> getAllQuestionnaire(Account account) {
        return questionnaireMapper.getQuestionnaires(account);
    }

    // 获取问卷题目数量
    @Override
    public Integer getNumberOfQuestionOfQuestionnaire(int questionnaireId) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionnaireId(questionnaireId);
        return questionMapper.getQuestions(questionnaire).size();
    }

    @Override
    @Validated
    public String checkQuestionnaire(Questionnaire questionnaire) {
        for (ChoiceQuestion choiceQuestion : questionnaire.getChoiceQuestions()) {
            if (choiceQuestion.getNecessary() || choiceQuestion.getState()) {
                if (choiceQuestion.getSelectedCount() < choiceQuestion.getMinSelected()
                        || choiceQuestion.getSelectedCount() > choiceQuestion.getMaxSelected()) {
                    return "问题 " + choiceQuestion.getSequenceId() + " 填写格式错误";
                }
            }
        }

        for (BlankQuestion blankQuestion : questionnaire.getBlankQuestions()) {
            if (blankQuestion.getNecessary() || blankQuestion.getState()) {
                Blank blank = blankQuestion.getBlank();
                if (blank.getContent().length() < blank.getMinCount()  // 字数验证
                        || blank.getContent().length() > blank.getMaxCount()) {
                    return "问题 " + blankQuestion.getSequenceId() + " 填写格式错误";
                }

                if (blankQuestion.getValidation() && blankQuestion.getType() != Type.NULL) { // 数据类型验证
                    if (!Pattern.matches(blankQuestion.getType().toString(), blank.getContent())) {
                        return "问题 " + blankQuestion.getSequenceId() + " 填写格式错误";
                    }
                }
            }
        }
        return null;
    }
}
