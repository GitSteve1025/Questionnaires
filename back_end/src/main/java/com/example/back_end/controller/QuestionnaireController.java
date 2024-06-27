package com.example.back_end.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.back_end.entity.Question.BlankQuestion.BlankQuestion;
import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.Questionnaire.State;
import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.service.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Resource;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

// @RequestParam 是前端必要参数
// @RequestParam("questionnaireId json 格式

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

    @Resource
    InfoService infoService;

    // 问卷所有者层面

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
    public RestBean<String> deleteQuestionnaire(@RequestParam("questionnaireId") Integer questionnaireId) {
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
            infoService.deleteQuestionnaireInfo(questionnaireId);
            return RestBean.success("删除成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 修改问卷的标题/描述
    @PostMapping("/update")
    public RestBean<String> updateQuestionnaire(@RequestParam("questionnaireId") Integer questionnaireId, // 需要提供问卷ID
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
    @PostMapping("/find")
    public RestBean<Questionnaire> findQuestionnaire(@RequestParam("questionnaireId") Integer questionnaireId) {
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
                    questionnaire.getChoiceQuestions().add(choiceQuestion);
                    break;
                case BLANK_QUESTION:
                    BlankQuestion blankQuestion = blankQuestionService.findBlankQuestion(account, question.getQuestionId());
                    blankQuestion.setBlank(blankService.findBlank(account, question.getQuestionId()));
                    questionnaire.getBlankQuestions().add(blankQuestion);
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

    // 发布问卷
    @PostMapping("/publish")
    public RestBean<String> publishQuestionnaire(@RequestParam("questionnaireId") int questionnaireId,
                                                 @RequestParam("startTime")
                                                 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                                 @RequestParam("endTime")
                                                 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        Account account = authorizeService.currentAccount();
        Questionnaire questionnaire = questionnaireService.findQuestionnaire(account, questionnaireId);
        if (questionnaire == null) {
            return RestBean.failure(400, "问卷不存在");
        }
        questionnaire.setState(State.PUBLISHED);
        questionnaire.setStartTime(startTime);
        questionnaire.setEndTime(endTime);
        String s = questionnaireService.updateQuestionnaire(account, questionnaire);
        if (s == null) {
            return RestBean.success("发布成功");
        } else {
            return RestBean.failure(400, s);
        }
    }


    // 问卷填写者层面

    // 获取问卷
    @PostMapping("/get")
    public RestBean<Questionnaire> getQuestionnaire(@RequestParam("questionnaireId") Integer questionnaireId) {
        Integer userId = questionnaireService.getUserIdOfQuestionnaire(questionnaireId);
        Account account = new Account();
        account.setId(userId);
        Questionnaire questionnaire = questionnaireService.findQuestionnaire(account, questionnaireId);
        if (questionnaire == null) {
            return RestBean.failure(400, null);
        }
        Date currentTime = new Date();
        if (questionnaire.getState() == State.PUBLISHED
                && questionnaire.getStartTime().before(currentTime)
                && questionnaire.getEndTime().after(currentTime)) {
            List<Question> questions = questionService.getAllQuestions(account, questionnaireId);
            for (Question question : questions) {
                switch (question.getCategory()) {
                    case SINGLE_CHOICE_QUESTION :
                    case MULTIPLE_CHOICE_QUESTION :
                        ChoiceQuestion choiceQuestion = choiceQuestionService.findChoiceQuestion(account, question.getQuestionId());
                        choiceQuestion.setChoices(choiceService.getChoices(account, question.getQuestionId()));
                        questionnaire.getChoiceQuestions().add(choiceQuestion);
                        break;
                    case BLANK_QUESTION:
                        BlankQuestion blankQuestion = blankQuestionService.findBlankQuestion(account, question.getQuestionId());
                        blankQuestion.setBlank(blankService.findBlank(account, question.getQuestionId()));
                        questionnaire.getBlankQuestions().add(blankQuestion);
                        break;
                    default:
                        return RestBean.failure(400, null);
                }
            }
            return RestBean.success(questionnaire);
        }
        return RestBean.failure(400, null);
    }

    // 填写问卷
    @PostMapping("/fill")
    public RestBean<String> fillQuestionnaire(@RequestParam("questionnaire") String text) {
        JSONObject object =  JSON.parseObject(text);
        System.out.println(text);
        Integer questionnaireId = object.getInteger("questionnaireId");
        JSONArray choiceQuestionsParams = object.getJSONArray("choiceQuestions");
        List<ChoiceQuestion> choiceQuestions = choiceQuestionsParams.toJavaList(ChoiceQuestion.class);
        JSONArray blankQuestionsParams = object.getJSONArray("blankQuestions");
        List<BlankQuestion> blankQuestions = blankQuestionsParams.toJavaList(BlankQuestion.class);
        Questionnaire questionnaire = questionnaireService.findQuestionnaire(questionnaireId);
        if (questionnaire == null || questionnaire.getState() == State.UNPUBLISHED) {
            return RestBean.failure(400, "问卷不存在");
        }
        questionnaire.setQuestionnaireId(questionnaireId);
        questionnaire.setChoiceQuestions((ArrayList<ChoiceQuestion>) choiceQuestions);
        questionnaire.setBlankQuestions((ArrayList<BlankQuestion>) blankQuestions);
        Date currentTime = new Date();
        if (questionnaire.getStartTime().after(currentTime)) {
            return RestBean.failure(400, "问卷未开始");
        }
        if (questionnaire.getEndTime().before(currentTime)) {
            return RestBean.failure(400, "问卷已截止");
        }
        String s = questionnaireService.checkQuestionnaire(questionnaire);
        if (s != null) {
            return RestBean.failure(400, s);
        }
        Account account = authorizeService.currentAccount();
        infoService.insertQuestionnaireInfo(account, questionnaire);
        for (ChoiceQuestion choiceQuestion : questionnaire.getChoiceQuestions()) {
            if (choiceQuestion.getState()) {
                for (Choice choice : choiceQuestion.getChoices()) {
                    if (choice.getState()) {
                        infoService.insertChoiceInfo(choice);
                    }
                }
            }
        }
        for (BlankQuestion blankQuestion : questionnaire.getBlankQuestions()) {
            if (blankQuestion.getState()) {
                System.out.println(blankQuestion.getBlank());
                if (blankQuestion.getBlank().getState()) {
                    infoService.insertBlankInfo(blankQuestion.getBlank());
                }
            }
        }
        return RestBean.success("问卷填写成功");
    }
}
