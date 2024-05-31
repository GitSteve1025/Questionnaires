package com.example.back_end.controller;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.BlankQuestion.BlankQuestion;
import com.example.back_end.entity.Question.BlankQuestion.Type;
import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import com.example.back_end.entity.Question.ChoiceQuestion.MultipleChoiceQuestion;
import com.example.back_end.entity.Question.ChoiceQuestion.SingleChoiceQuestion;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.service.*;
import jakarta.annotation.Resource;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Resource
    AuthorizeService authorizeService;

    @Resource
    QuestionnaireService questionnaireService;

    @Resource
    ChoiceQuestionService choiceQuestionService;

    @Resource
    BlankQuestionService blankQuestionService;

    @Resource
    BlankService blankService;

    // 添加单选题
    @PostMapping("/create-SingleChoiceQuestion")
    public RestBean<Question> createSingleChoiceQuestion(@RequestBody Integer questionnaireId,
                                                         @Length(min = 1) @RequestParam("title") String title,
                                                         @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary) {
        Account account = authorizeService.currentAccount();
        if (questionnaireService.belongsToAccount(account, questionnaireId)) {  // 权限验证
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setQuestionnaireId(questionnaireId);
            Integer numberOfQuestions = questionnaireService.getNumberOfQuestionOfQuestionnaire(questionnaireId); // 获取当前问题数量
            SingleChoiceQuestion singleChoiceQuestion = new SingleChoiceQuestion(); // 单选题
            singleChoiceQuestion.setSequenceId(numberOfQuestions + 1);
            singleChoiceQuestion.setTitle(title);
            singleChoiceQuestion.setNecessary(necessary);
            String s = choiceQuestionService.createChoiceQuestion(questionnaire, singleChoiceQuestion);
            if (s == null) {
                return RestBean.success(singleChoiceQuestion) ;
            } else {
                return RestBean.failure(400, null);
            }
        }
        return RestBean.failure(400, null);
    }

    // 添加多选题
    @PostMapping("/create-MultipleChoiceQuestion")
    public RestBean<MultipleChoiceQuestion> createMultipleChoiceQuestion(@RequestBody Integer questionnaireId,
                                                                         @Length(min = 1) @RequestParam("title") String title,
                                                                         @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary) {
        Account account = authorizeService.currentAccount();
        if (questionnaireService.belongsToAccount(account, questionnaireId)) {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setQuestionnaireId(questionnaireId);
            Integer numberOfQuestions = questionnaireService.getNumberOfQuestionOfQuestionnaire(questionnaireId); // 获取当前问题数量
            MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
            multipleChoiceQuestion.setSequenceId(numberOfQuestions + 1);
            multipleChoiceQuestion.setTitle(title);
            multipleChoiceQuestion.setNecessary(necessary);
            String s = choiceQuestionService.createChoiceQuestion(questionnaire, multipleChoiceQuestion);
            if (s == null) {
                return RestBean.success(multipleChoiceQuestion);
            } else {
                return RestBean.failure(400, null);
            }
        }
        return RestBean.failure(400, null);
    }

    // 添加填空题
    @PostMapping("/create-BlankQuestion")
    public RestBean<BlankQuestion> createBlankQuestion(@RequestBody Integer questionnaireId,
                                                       @Length(min = 1) @RequestParam("title") String title,
                                                       @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary,
                                                       @RequestParam(value = "validation", defaultValue = "false") Boolean validation,
                                                       @RequestParam(value = "type", defaultValue = "NULL") Type type) {
        Account account = authorizeService.currentAccount();
        if (questionnaireService.belongsToAccount(account, questionnaireId)) {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setQuestionnaireId(questionnaireId);
            Integer numberOfQuestions = questionnaireService.getNumberOfQuestionOfQuestionnaire(questionnaireId); // 获取当前问题数量
            BlankQuestion blankQuestion = new BlankQuestion();
            blankQuestion.setSequenceId(numberOfQuestions + 1);
            blankQuestion.setTitle(title);
            blankQuestion.setNecessary(necessary);
            blankQuestion.setValidation(validation);
            blankQuestion.setType(type);
            String s = blankQuestionService.createBlankQuestion(questionnaire, blankQuestion);
            Blank blank = new Blank();
            String t = blankService.createBlank(blankQuestion, blank); // 添加对应的 blank
            if (s == null && t == null) {
                return RestBean.success(blankQuestion) ;
            } else {
                return RestBean.failure(400, null);
            }
        }
        return RestBean.failure(400, null);
    }

    // 删除问题
    @PostMapping("/delete")
    public RestBean<String> deleteQuestion(int questionId) {
        // to do
        return null;
    }

    // 修改单选题
    @PostMapping("/update-SingleChoiceQuestion")
    public RestBean<String> updateSingleChoiceQuestion(@RequestBody Integer questionId,
                                                       @Length(min = 1) @RequestParam("title") String title,
                                                       @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary) {
        Account account = authorizeService.currentAccount();
        ChoiceQuestion choiceQuestion = choiceQuestionService.findChoiceQuestion(account, questionId);
        if (choiceQuestion == null) {
            return RestBean.failure(400, "问题不存在");
        }
        choiceQuestion.setTitle(title);
        choiceQuestion.setNecessary(necessary);
        String s = choiceQuestionService.updateChoiceQuestion(account, choiceQuestion);
        if (s == null) {
            return RestBean.success("修改成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 修改单选题
    @PostMapping("/update-MultipleChoiceQuestion")
    public RestBean<String> updateMultipleChoiceQuestion(@RequestBody Integer questionId,
                                                         @Length(min = 1) @RequestParam("title") String title,
                                                         @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary,
                                                         @RequestParam(value = "minSelected", defaultValue = "1") Integer minSelected,
                                                         @RequestParam(value = "maxSelected", defaultValue = "Integer.MAX_VALUE") Integer maxSelected) {
        Account account = authorizeService.currentAccount();
        ChoiceQuestion choiceQuestion = choiceQuestionService.findChoiceQuestion(account, questionId);
        if (choiceQuestion == null) {
            return RestBean.failure(400, "问题不存在");
        }
        choiceQuestion.setTitle(title);
        choiceQuestion.setNecessary(necessary);
        choiceQuestion.setMinSelected(minSelected);
        choiceQuestion.setMaxSelected(maxSelected);
        String s = choiceQuestionService.updateChoiceQuestion(account, choiceQuestion);
        if (s == null) {
            return RestBean.success("修改成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 修改单选题
    @PostMapping("/update-BlankQuestion")
    public RestBean<String> updateBlankQuestion(@RequestBody Integer questionId,
                                                @Length(min = 1) @RequestParam("title") String title,
                                                @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary,
                                                @RequestParam(value = "validation", defaultValue = "false") Boolean validation,
                                                @RequestParam(value = "type", defaultValue = "NULL") Type type) {
        Account account = authorizeService.currentAccount();
        BlankQuestion blankQuestion = blankQuestionService.findBlankQuestion(account, questionId);
        if (blankQuestion == null) {
            return RestBean.failure(400, "问题不存在");
        }
        blankQuestion.setTitle(title);
        blankQuestion.setNecessary(necessary);
        blankQuestion.setValidation(validation);
        blankQuestion.setType(type);
        String s = blankQuestionService.updateBlankQuestion(account, blankQuestion);
        if (s == null) {
            return RestBean.success("修改成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 获取问题
    // to do
}
