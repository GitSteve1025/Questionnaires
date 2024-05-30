package com.example.back_end.service.impl;

import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.ChoiceQuestionMapper;
import com.example.back_end.mapper.QuestionMapper;
import com.example.back_end.mapper.QuestionnaireMapper;
import com.example.back_end.service.ChoiceQuestionService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;

public class ChoiceQuestionServiceImpl implements ChoiceQuestionService {
    @Resource
    QuestionnaireMapper questionnaireMapper;

    @Resource
    QuestionMapper questionMapper;

    @Resource
    ChoiceQuestionMapper choiceQuestionMapper;

    // 获取该选择题创建者的ID
    @Override
    public Integer findUserIdOfChoiceQuestion(int choiceQuestionId) {
        Integer questionnaireId = questionMapper.getQuestionnaireIdOfQuestion(choiceQuestionId);
        if (questionnaireId == null) { // 问卷不存在
            return null;
        }
        return questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
    }

    // 检查是否属于 account
    @Override
    public Boolean belongsToAccount(Account account, int choiceQuestionId) {
        Integer userId = findUserIdOfChoiceQuestion(choiceQuestionId);
        if (userId == null || userId != account.getId()) { // 不属于 account
            return false;
        }
        return true;
    }

    // 添加选择题
    @Override
    public String createChoiceQuestion(Questionnaire questionnaire, ChoiceQuestion choiceQuestion) {
        if (questionMapper.createQuestion(questionnaire, choiceQuestion) > 0 // 这个一定要先执行， 算出 questionId
                && choiceQuestionMapper.createChoiceQuestion(choiceQuestion) > 0) {
            return null; // 成功返回 null
        } else {
            return "创建选择题失败";
        }
    }

    // 删除选择题
    @Override
    public String deleteChoiceQuestion(Account account, int choiceQuestionId) {
        if (belongsToAccount(account, choiceQuestionId)) { // 属于 account
            if (choiceQuestionMapper.deleteChoiceQuestion(choiceQuestionId) > 0
                    && questionMapper.deleteQuestion(choiceQuestionId) > 0) {
                return null;
            } else {
                return "未知原因，删除选择题失败，请联系管理员";
            }
        }
        return "没有权限";
    }

    // 修改选择题
    @Override
    public String updateChoiceQuestion(Account account, ChoiceQuestion choiceQuestion) {
        if (belongsToAccount(account, choiceQuestion.getQuestionId())) { // 属于 account
            if (choiceQuestionMapper.updateChoiceQuestion(choiceQuestion) > 0
                    && questionMapper.updateQuestion(choiceQuestion) > 0) {
                return null;
            } else {
                return "未知原因，修改选择题失败，请联系管理员";
            }
        }
        return "没有权限";
    }

    // 获取选择题
    @Override
    public ChoiceQuestion findChoiceQuestion(Account account, int choiceQuestionId) {
        if (belongsToAccount(account, choiceQuestionId)) { // 属于 account
            Question question = questionMapper.getQuestion(choiceQuestionId);
            if (question == null) {
                return null;
            }
            ChoiceQuestion choiceQuestion = choiceQuestionMapper.getChoiceQuestion(question);
            if (choiceQuestion == null) {
                return null;
            }
            BeanUtils.copyProperties(question, choiceQuestion); // 赋值给子类
            return choiceQuestion;
        }
        return null;
    }
}
