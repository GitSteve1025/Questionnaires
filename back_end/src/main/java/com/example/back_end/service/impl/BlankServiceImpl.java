package com.example.back_end.service.impl;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.BlankMapper;
import com.example.back_end.mapper.QuestionMapper;
import com.example.back_end.mapper.QuestionnaireMapper;
import com.example.back_end.service.BlankService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BlankServiceImpl implements BlankService {
    @Resource
    BlankMapper blankMapper;

    @Resource
    QuestionMapper questionMapper;

    @Resource
    QuestionnaireMapper questionnaireMapper;

    // 得到创建blank的 userId
    @Override
    public Integer getUserIdOfBlank(int blankId) {
        Integer questionId = blankMapper.getQuestionIdOfBlank(blankId);
        if (questionId == null) {
            return null;
        }
        Integer questionnaireId = questionMapper.getQuestionnaireIdOfQuestion(questionId);
        if (questionnaireId == null) {
            return null;
        }
        return questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
    }

    // 检查是否属于 account
    @Override
    public Boolean belongsToAccount(Account account, int blankId) {
        Integer userId = getUserIdOfBlank(blankId);
        if (userId == null || userId != account.getId()) {
            return false;
        }
        return true;
    }

    // 增加blank
    @Override
    public String createBlank(Question question, Blank blank) {
        if (blankMapper.createBlank(question, blank) > 0) {
            return null;
        } else {
            return "创建 Blank 失败";
        }
    }

    // 删除blank
    @Override
    public String deleteBlank(Account account, int blankId) {
        if (belongsToAccount(account, blankId)) {
            if (blankMapper.deleteBlank(blankId) > 0) {
                return null;
            } else {
                return "未知错误，删除 Blank 失败，请联系管理员";
            }
        }
        return "没有权限";
    }

    // 修改blank
    @Override
    public String updateBlank(Account account, Blank blank) {
        if (belongsToAccount(account, blank.getBlankId())) {
            if (blankMapper.updateBlank(blank) > 0) {
                return null;
            } else {
                return "未知错误，修改 Blank 失败，请联系管理员";
            }
        }
        return "没有权限";
    }

    // 获取blank
    @Override
    public Blank findBlank(Account account, int questionId) {
        Integer questionnaireId = questionMapper.getQuestionnaireIdOfQuestion(questionId);
        if (questionnaireId == null) { // 问卷不存在
            return null;
        }
        Integer userId =  questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
        if (userId == null || userId != account.getId()) { // 无权限
            return null;
        }
        return blankMapper.getBlankByQuestionId(questionId);
    }
}
