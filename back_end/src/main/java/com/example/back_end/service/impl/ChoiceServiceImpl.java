package com.example.back_end.service.impl;

import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.ChoiceMapper;
import com.example.back_end.mapper.QuestionMapper;
import com.example.back_end.mapper.QuestionnaireMapper;
import com.example.back_end.service.ChoiceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoiceServiceImpl implements ChoiceService {
    @Resource
    ChoiceMapper choiceMapper;

    @Resource
    QuestionMapper questionMapper;

    @Resource
    QuestionnaireMapper questionnaireMapper;

    // 得到创建选项的 userId
    @Override
    public Integer getUserIdOfChoice(int choiceId) {
        Integer questionId = choiceMapper.getQuestionIdOfChoice(choiceId);
        if (questionId == null) { // 问题不存在
            return null;
        }
        Integer questionnaireId = questionMapper.getQuestionnaireIdOfQuestion(questionId);
        if (questionnaireId == null) { // 问卷不存在
            return null;
        }
        return questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
    }

    // 检查是否属于 account
    @Override
    public Boolean belongsToAccount(Account account, int choiceId) {
        Integer userId = getUserIdOfChoice(choiceId);
        if (userId == null || userId != account.getId()) {
            return false;
        }
        return true;
    }

    // 增加选项
    @Override
    public String createChoice(Question question, Choice choice) {
        if (choiceMapper.createChoice(question, choice) > 0) {
            return null;
        } else {
            return "创建选项失败";
        }
    }

    // 删除现象
    @Override
    public String deleteChoice(Account account, int choiceId) {
        if (belongsToAccount(account, choiceId)) {
            if (choiceMapper.deleteChoice(choiceId) > 0) {
                return null;
            } else {
                return "未知错误，删除选项失败，请联系管理员";
            }
        }
        return "没有权限";
    }

    // 修改选项
    @Override
    public String updateChoice(Account account, Choice choice) {
        if (belongsToAccount(account, choice.getChoiceId())) {
            if (choiceMapper.updateChoice(choice) > 0) {
                return null;
            } else {
                return "未知错误，修改选项失败，请联系管理员";
            }
        }
        return null;
    }

    // 获取选项
    @Override
    public Choice findChoice(Account account, int choiceId) {
        if (belongsToAccount(account, choiceId)) {
            return choiceMapper.getChoice(choiceId);
        }
        return null;
    }

    // 获取问题的所有选项
    @Override
    public List<Choice> getChoices(Account account, int questionId) {
        Integer questionnaireId = questionMapper.getQuestionnaireIdOfQuestion(questionId);
        if (questionnaireId == null) { // 问卷不存在
            return null;
        }
        Integer userId =  questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
        if (userId == null || userId != account.getId()) { // 无权限
            return null;
        }
        return choiceMapper.getChoices(questionId);
    }
}
