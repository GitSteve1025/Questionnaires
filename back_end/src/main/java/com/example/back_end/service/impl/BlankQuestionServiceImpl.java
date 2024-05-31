package com.example.back_end.service.impl;

import com.example.back_end.entity.Question.BlankQuestion.BlankQuestion;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.BlankQuestionMapper;
import com.example.back_end.mapper.QuestionMapper;
import com.example.back_end.mapper.QuestionnaireMapper;
import com.example.back_end.service.BlankQuestionService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BlankQuestionServiceImpl implements BlankQuestionService {
    @Resource
    QuestionnaireMapper questionnaireMapper;

    @Resource
    QuestionMapper questionMapper;

    @Resource
    BlankQuestionMapper blankQuestionMapper;

    // 获取该填空题创建者的ID
    @Override
    public Integer findUserIdOfBlankQuestion(int blankQuestionId) {
        Integer questionnaireId = questionMapper.getQuestionnaireIdOfQuestion(blankQuestionId);
        if (questionnaireId == null) { // 问卷不存在
            return null;
        }
        return questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
    }

    // 检查是否属于 account
    @Override
    public Boolean belongsToAccount(Account account, int blankQuestionId) {
        Integer userId = findUserIdOfBlankQuestion(blankQuestionId);
        if (userId == null || userId != account.getId()) { // 不属于 account
            return false;
        }
        return true;
    }

    // 添加填空题
    @Override
    public String createBlankQuestion(Questionnaire questionnaire, BlankQuestion blankQuestion) {
        if (questionMapper.createQuestion(questionnaire, blankQuestion) > 0 // 这个一定要先执行， 算出 questionId
                && blankQuestionMapper.createBlankQuestion(blankQuestion) > 0) {
            return null; // 成功返回 null
        } else {
            return "创建填空题失败";
        }
    }

    // 删除填空题
    @Override
    public String deleteBlankQuestion(Account account, int blankQuestionId) {
        if (belongsToAccount(account, blankQuestionId)) { // 属于 account
            if (blankQuestionMapper.deleteBlankQuestion(blankQuestionId) > 0
                    && questionMapper.deleteQuestion(blankQuestionId) > 0) {
                return null;
            } else {
                return "未知原因，删除填空题失败，请联系管理员";
            }
        }
        return "没有权限";
    }

    // 修改填空题
    @Override
    public String updateBlankQuestion(Account account, BlankQuestion blankQuestion) {
        if (belongsToAccount(account, blankQuestion.getQuestionId())) { // 属于 account
            if (blankQuestionMapper.updateBlankQuestion(blankQuestion) > 0
                    && questionMapper.updateQuestion(blankQuestion) > 0) {
                return null;
            } else {
                return "未知原因，修改填空题失败，请联系管理员";
            }
        }
        return "没有权限";
    }

    // 获取填空题
    @Override
    public BlankQuestion findBlankQuestion(Account account, int blankQuestionId) {
        if (belongsToAccount(account, blankQuestionId)) { // 属于 account
            Question question = questionMapper.getQuestion(blankQuestionId);
            if (question == null) {
                return null;
            }
            BlankQuestion blankQuestion = blankQuestionMapper.getBlankQuestion(question);
            if (blankQuestion == null) {
                return null;
            }
            BeanUtils.copyProperties(question, blankQuestion); // 赋值给子类
            return blankQuestion;
        }
        return null;
    }
}
