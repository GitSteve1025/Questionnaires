package com.example.back_end.service.impl;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.BlankInfoMapper;
import com.example.back_end.mapper.ChoiceInfoMapper;
import com.example.back_end.mapper.QuestionnaireInfoMapper;
import com.example.back_end.service.InfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InfoServiceImpl implements InfoService {
    @Resource
    BlankInfoMapper blankInfoMapper;

    @Resource
    ChoiceInfoMapper choiceInfoMapper;

    @Resource
    QuestionnaireInfoMapper questionnaireInfoMapper;

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
}
