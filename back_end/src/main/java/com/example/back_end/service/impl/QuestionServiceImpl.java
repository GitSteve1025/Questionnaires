package com.example.back_end.service.impl;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.QuestionMapper;
import com.example.back_end.mapper.QuestionnaireMapper;
import com.example.back_end.service.BlankQuestionService;
import com.example.back_end.service.BlankService;
import com.example.back_end.service.ChoiceQuestionService;
import com.example.back_end.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    // 获取该题创建者的ID
    @Resource
    QuestionMapper questionMapper;

    @Resource
    QuestionnaireMapper questionnaireMapper;

    @Resource
    ChoiceQuestionService choiceQuestionService;

    @Resource
    BlankQuestionService blankQuestionService;

    @Resource
    BlankService blankService;

    @Override
    public Integer getUserIdOfQuestion(Integer questionId) {
        Integer questionnaireId = questionMapper.getQuestionnaireIdOfQuestion(questionId);
        if (questionnaireId == null) {
            return null;
        }
        return questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
    }

    // 检查是否属于 account
    @SuppressWarnings("RedundantIfStatement")
    @Override
    public Boolean belongsToQuestion(Account account, Integer questionId) {
        Integer userId = getUserIdOfQuestion(questionId);
        if (userId == null || userId != account.getId()) {
            return false;
        }
        return true;
    }

    // 删除问题
    @SuppressWarnings("RedundantIfStatement")
    @Override
    public String deleteQuestion(Account account, Integer questionId) {
        if (belongsToQuestion(account, questionId)) {
            Question question = questionMapper.getQuestion(questionId);
            String s; // 存储结果
            switch (question.getCategory()) {
                case SINGLE_CHOICE_QUESTION :
                case MULTIPLE_CHOICE_QUESTION :
                    s = choiceQuestionService.deleteChoiceQuestion(account, questionId);
                    if (s == null) {
                        return null;
                    } else {
                        return s;
                    }
                case BLANK_QUESTION :
                    Blank blank = blankService.findBlank(account, questionId);
                    s = blankService.deleteBlank(account, blank.getBlankId());
                    if (s != null) {
                        return s;
                    }
                    s = blankQuestionService.deleteBlankQuestion(account, questionId);
                    if (s == null) {
                        return null;
                    } else {
                        return s;
                    }
                default :
                    return "问题种类错误";
            }
        }
        return "问题不存在";
    }

    // 获取所有问题
    @Override
    public List<Question> getAllQuestions(Account account, Integer questionnaireId) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionnaireId(questionnaireId);
        return questionMapper.getQuestions(questionnaire);
    }
}
