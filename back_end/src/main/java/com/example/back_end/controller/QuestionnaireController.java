package com.example.back_end.controller;

import com.example.back_end.entity.Question.BlankQuestion.BlankQuestion;
import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.service.*;
import jakarta.annotation.Resource;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// @RequestParam 是前端必要参数
// @RequestBody json 格式

// 问卷接口
@Validated
@RestController
@RequestMapping("/questionnaires")
public class QuestionnaireController {

    @Resource
    AuthorizeService authorizeService;

    @Resource
    QuestionnaireService questionnaireService;

    @Resource
    QuestionService questionService;

    @Resource
    ChoiceQuestionService choiceQuestionService;

    @Resource
    ChoiceService choiceService;

    @Resource
    BlankQuestionService blankQuestionService;

    @Resource
    BlankService blankService;

    // 问卷所有者层面

    // 通过标题搜索问卷
    @GetMapping("/search")
    public RestBean<List<Questionnaire>> searchQuestionnaire(@RequestParam @Length(min = 1) String title) {
        // to do
        // 根据标题搜索相关问卷
        return null;
    }

    // 添加问卷
    @PostMapping("/create")
    public RestBean<Questionnaire> createQuestionnaire(@Length(min = 1) @RequestParam("title") String title, // 标题不能为空
                                                       @RequestParam(value = "description", required = false) String description) {
        Account account = authorizeService.currentAccount(); // 当前用户
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitle(title);
        questionnaire.setDescription(description);
        String s = questionnaireService.createQuestionnaire(account, questionnaire);
        if (s == null) {
            return RestBean.success(questionnaire); // 创建成功返回该问卷
        } else {
            return RestBean.failure(400, null); // 创建失败返回  null
        }
    }

    // 删除问卷
    @PostMapping("/delete")
    public RestBean<String> deleteQuestionnaire(@RequestBody Integer questionnaireId) {
        Account account = authorizeService.currentAccount(); // 当前用户
        Questionnaire questionnaire = questionnaireService.findQuestionnaire(account, questionnaireId); // 先找到该问卷
        if (questionnaire == null) {
            return RestBean.failure(400, "问卷不存在");
        }

        List<Question> questions = questionService.getAllQuestions(account, questionnaireId);
        for (Question question : questions) {
            String s = questionService.deleteQuestion(account, question.getQuestionId());
            if (s != null) {
                return RestBean.failure(400, s);
            }
        }

        String s = questionnaireService.deleteQuestionnaire(account, questionnaireId);
        if (s == null) {
            return RestBean.success("删除成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 修改问卷的标题/描述
    @PostMapping("/update")
    public RestBean<String> updateQuestionnaire(@RequestBody Integer questionnaireId, // 需要提供问卷ID
                                                @Length(min = 1) @RequestParam("title") String title, // 标题不能为空
                                                @RequestParam(value = "description", required = false) String description) {
        Account account = authorizeService.currentAccount(); // 当前用户
        Questionnaire questionnaire = questionnaireService.findQuestionnaire(account, questionnaireId); // 找到问卷
        if (questionnaire == null) {
            return RestBean.failure(400, "问卷不存在");
        }
        questionnaire.setTitle(title); // 更改标题
        questionnaire.setDescription(description); // 更改描述
        String s = questionnaireService.updateQuestionnaire(account, questionnaire);// 更新问卷
        if (s == null) {
            return RestBean.success("修改成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 查找问卷
    @GetMapping("/find")
    public RestBean<Questionnaire> findQuestionnaire(@RequestBody Integer questionnaireId) {
        Account account = authorizeService.currentAccount();
        Questionnaire questionnaire = questionnaireService.findQuestionnaire(account, questionnaireId);
        if (questionnaire == null) {
            return RestBean.failure(400, null);
        }

        List<Question> questions = questionService.getAllQuestions(account, questionnaireId);
        for (Question question : questions) {
            switch (question.getCategory()) {
                case SINGLE_CHOICE_QUESTION :
                case MULTIPLE_CHOICE_QUESTION :
                    ChoiceQuestion choiceQuestion = choiceQuestionService.findChoiceQuestion(account, question.getQuestionId());
                    choiceQuestion.setChoices(choiceService.getChoices(account, question.getQuestionId()));
                    questionnaire.getQuestions().add(choiceQuestion);
                    break;
                case BLANK_QUESTION:
                    BlankQuestion blankQuestion = blankQuestionService.findBlankQuestion(account, question.getQuestionId());
                    blankQuestion.setBlank(blankService.findBlank(account, question.getQuestionId()));
                    questionnaire.getQuestions().add(blankQuestion);
                    break;
                default:
                    return RestBean.failure(400, null);
            }
        }
        return RestBean.success(questionnaire);
    }

    // 展示所有问卷
    @GetMapping("/display-all")
    public RestBean<List<Questionnaire>> displayAllQuestionnaires() {
        return RestBean.success(questionnaireService.getAllQuestionnaire(authorizeService.currentAccount()));
    }
}
