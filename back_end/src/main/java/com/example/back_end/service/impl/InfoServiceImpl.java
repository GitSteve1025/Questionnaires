package com.example.back_end.service.impl;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.BlankInfoMapper;
import com.example.back_end.mapper.ChoiceInfoMapper;
import com.example.back_end.mapper.QuestionnaireInfoMapper;
import com.example.back_end.mapper.UserMapper;
import com.example.back_end.service.InfoService;
import jakarta.annotation.Resource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InfoServiceImpl implements InfoService {
    @Resource
    BlankInfoMapper blankInfoMapper;

    @Resource
    ChoiceInfoMapper choiceInfoMapper;

    @Resource
    QuestionnaireInfoMapper questionnaireInfoMapper;

    @Resource
    UserMapper userMapper;

    // 添加 blank info
    @Override
    public Integer insertBlankInfo(Blank blank) {
        return blankInfoMapper.insertBlankInfo(blank);
    }

    // 删除 blank
    @Override
    public Integer deleteBlankInfo(int blankId) {
        Blank blank = new Blank();
        blank.setBlankId(blankId);
        return blankInfoMapper.deleteBlankInfo(blank);
    }

    // 添加 choice info
    @Override
    public Integer insertChoiceInfo(Choice choice) {
        Integer count = choiceInfoMapper.getChoiceInfo(choice);
        if (count == null) {
            return choiceInfoMapper.insertChoiceInfo(choice, 1);
        } else {
            return choiceInfoMapper.updateChoiceInfo(choice, count + 1);
        }
    }

    // 删除 choice
    @Override
    public Integer deleteChoiceInfo(int choiceId) {
        Choice choice = new Choice();
        choice.setChoiceId(choiceId);
        return choiceInfoMapper.deleteChoiceInfo(choice);
    }

    // 添加 questionnaire info
    @Override
    public Integer insertQuestionnaireInfo(Account account, Questionnaire questionnaire) {
        return questionnaireInfoMapper.insertQuestionnaireInfo(account, questionnaire, new Date());
    }

    // 删除 questionnaire
    @Override
    public Integer deleteQuestionnaireInfo(int questionnaireId) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionnaireId(questionnaireId);
        return questionnaireInfoMapper.deleteQuestionnaireInfo(questionnaire);
    }

    // 获取选项选择次数
    @Override
    public Integer getChoiceInfo(int choiceId) {
        Choice choice = new Choice();
        choice.setChoiceId(choiceId);
        Integer count = choiceInfoMapper.getChoiceInfo(choice);
        if (count == null) {
            count = 0;
        }
        return count;
    }

    // 获取填空题填写的内容表
    @Override
    public List<String> getBlankInfo(int blankId) {
        Blank blank = new Blank();
        blank.setBlankId(blankId);
        return blankInfoMapper.getBlankInfo(blank);
    }

    // 获取问卷填写者和填写时间的二元组
    @Override
    public Map<Account, Date> getQuestionnaireInfo(int questionnaireId) {
        Map<Account, Date> info = new HashMap<>();
        Map<Integer, Date> temp = questionnaireInfoMapper.getQuestionnaireInfo(questionnaireId);
        for (Map.Entry<Integer, Date> entry : temp.entrySet()) {
            Account account = userMapper.findAccountById(entry.getKey());
            info.put(account, entry.getValue());
        }
        return info;
    }
}
