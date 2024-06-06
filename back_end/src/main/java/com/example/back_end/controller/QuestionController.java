package com.example.back_end.controller;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.BlankQuestion.BlankQuestion;
import com.example.back_end.entity.Question.BlankQuestion.Type;
import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import com.example.back_end.entity.Question.ChoiceQuestion.MultipleChoiceQuestion;
import com.example.back_end.entity.Question.ChoiceQuestion.SingleChoiceQuestion;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.service.*;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Resource
    QuestionService questionService;

    @Resource
    ChoiceService choiceService;

    // 添加单选题
    @PostMapping("/create-SingleChoiceQuestion")
    public RestBean<SingleChoiceQuestion> createSingleChoiceQuestion(@RequestParam("questionnaireId") Integer questionnaireId,
                                                         @Length(min = 1) @RequestParam("title") String title,
                                                         @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary,
                                                         @Size(min = 2) @RequestParam("content") List<String> content) {
        Account account = authorizeService.currentAccount();
        if (questionnaireService.belongsToAccount(account, questionnaireId)) {  // 权限验证
            Integer numberOfQuestions = questionnaireService.getNumberOfQuestionOfQuestionnaire(questionnaireId); // 获取当前问题数量
            SingleChoiceQuestion singleChoiceQuestion = new SingleChoiceQuestion(); // 单选题
            singleChoiceQuestion.setSequenceId(numberOfQuestions + 1);
            singleChoiceQuestion.setTitle(title);
            singleChoiceQuestion.setNecessary(necessary);
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setQuestionnaireId(questionnaireId);
            String s = choiceQuestionService.createChoiceQuestion(questionnaire, singleChoiceQuestion);
            if (s == null) {
                List<Choice> choices = new ArrayList<>();
                for (int i = 0; i < content.size(); i++) {
                    Choice choice = new Choice();
                    choice.setSequenceId(i + 1);
                    choice.setContent(content.get(i));
                    String t = choiceService.createChoice(singleChoiceQuestion, choice);
                    if (t == null) {
                        choices.add(choice);
                    } else {
                        return null;
                    }
                }
                singleChoiceQuestion.setChoices(choices);
                return RestBean.success(singleChoiceQuestion) ;
            } else {
                return RestBean.failure(400, null);
            }
        }
        return RestBean.failure(400, null);
    }

    // 添加多选题
    @PostMapping("/create-MultipleChoiceQuestion")
    public RestBean<MultipleChoiceQuestion> createMultipleChoiceQuestion(@RequestParam("questionnaireId") Integer questionnaireId,
                                                                         @Length(min = 1) @RequestParam("title") String title,
                                                                         @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary,
                                                                         @Size(min = 2) @RequestParam("content") List<String> content,
                                                                         @RequestParam(value = "minSelected", defaultValue = "1") Integer minSelected,
                                                                         @RequestParam(value = "maxSelected", defaultValue = "Integer.MAX_VALUE") Integer maxSelected) {
        Account account = authorizeService.currentAccount();
        if (questionnaireService.belongsToAccount(account, questionnaireId)) { // 权限验证
            Integer numberOfQuestions = questionnaireService.getNumberOfQuestionOfQuestionnaire(questionnaireId); // 获取当前问题数量
            MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(); // 多选题
            multipleChoiceQuestion.setSequenceId(numberOfQuestions + 1);
            multipleChoiceQuestion.setTitle(title);
            multipleChoiceQuestion.setNecessary(necessary);
            multipleChoiceQuestion.setMinSelected(minSelected);
            multipleChoiceQuestion.setMaxSelected(maxSelected);
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setQuestionnaireId(questionnaireId);
            String s = choiceQuestionService.createChoiceQuestion(questionnaire, multipleChoiceQuestion);
            if (s == null) {
                List<Choice> choices = new ArrayList<>();
                for (int i = 0; i < content.size(); i++) {
                    Choice choice = new Choice();
                    choice.setSequenceId(i + 1);
                    choice.setContent(content.get(i));
                    String t = choiceService.createChoice(multipleChoiceQuestion, choice);
                    if (t == null) {
                        choices.add(choice);
                    } else {
                        return null;
                    }
                }
                multipleChoiceQuestion.setChoices(choices);
                return RestBean.success(multipleChoiceQuestion);
            } else {
                return RestBean.failure(400, null);
            }
        }
        return RestBean.failure(400, null);
    }

    // 添加填空题
    @PostMapping("/create-BlankQuestion")
    public RestBean<BlankQuestion> createBlankQuestion(@RequestParam("questionnaireId") Integer questionnaireId,
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
    public RestBean<String> deleteQuestion(@RequestParam("questionnaireId") Integer questionId) {
        Account account = authorizeService.currentAccount();
        Integer questionnaireId = questionService.getQuestionnaireIdOfQuestion(questionId);
        String s = questionService.deleteQuestion(account, questionId);
        if (s == null) {
            List<Question> questions = questionService.getAllQuestions(account, questionnaireId);
            Collections.sort(questions, (left, right) -> left.getSequenceId() - right.getSequenceId()); // 升序排序
            for (int i = 0; i < questions.size(); i++) { // 重新编号
                questions.get(i).setSequenceId(i + 1);
                questionService.updateQuestion(account, questions.get(i));
            }
            return RestBean.success("删除成功");
        } else {
            return  RestBean.failure(400, s);
        }
    }

    // 修改单选题
    @PostMapping("/update-SingleChoiceQuestion")
    public RestBean<String> updateSingleChoiceQuestion(@RequestParam("questionnaireId") Integer questionId,
                                                       @Length(min = 1) @RequestParam("title") String title,
                                                       @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary,
                                                       @Size(min = 2) @RequestParam("content") List<String> content) {
        Account account = authorizeService.currentAccount();
        ChoiceQuestion choiceQuestion = choiceQuestionService.findChoiceQuestion(account, questionId);
        if (choiceQuestion == null) {
            return RestBean.failure(400, "问题不存在");
        }
        choiceQuestion.setTitle(title);
        choiceQuestion.setNecessary(necessary);
        choiceQuestionService.updateChoiceQuestion(account, choiceQuestion); // 更新 title necessary
        choiceQuestionService.deleteChoices(account, questionId); // 把之前的选项删除
        for (int i = 0; i < content.size(); i++) {
            Choice choice = new Choice();
            choice.setSequenceId(i + 1);
            choice.setContent(content.get(i));
            String t = choiceService.createChoice(choiceQuestion, choice); // 添加新选项
            if (t != null) {
                return RestBean.failure(400, t);
            }
        }
        return RestBean.success("修改成功");
    }

    // 修改单选题
    @PostMapping("/update-MultipleChoiceQuestion")
    public RestBean<String> updateMultipleChoiceQuestion(@RequestParam("questionnaireId") Integer questionId,
                                                         @Length(min = 1) @RequestParam("title") String title,
                                                         @RequestParam(value = "necessary", defaultValue = "true") Boolean necessary,
                                                         @Size(min = 2) @RequestParam("content") List<String> content,
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
        choiceQuestionService.updateChoiceQuestion(account, choiceQuestion); // 更新 title necessary
        choiceQuestionService.deleteChoices(account, questionId); // 把之前的选项删除
        for (int i = 0; i < content.size(); i++) {
            Choice choice = new Choice();
            choice.setSequenceId(i + 1);
            choice.setContent(content.get(i));
            String t = choiceService.createChoice(choiceQuestion, choice); // 添加新选项
            if (t != null) {
                return RestBean.failure(400, t);
            }
        }
        return RestBean.success("修改成功");
    }

    // 修改单选题
    @PostMapping("/update-BlankQuestion")
    public RestBean<String> updateBlankQuestion(@RequestParam("questionnaireId") Integer questionId,
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

    @GetMapping("/find")
    public RestBean<Question> findQuestion(@RequestParam("questionId") Integer questionId) {
        Account account = authorizeService.currentAccount();
        Question question = questionService.getQuestion(account, questionId);
        if (question == null) {
            return null;
        }
        return switch (question.getCategory()) {
            case SINGLE_CHOICE_QUESTION, MULTIPLE_CHOICE_QUESTION -> {
                ChoiceQuestion choiceQuestion = choiceQuestionService.findChoiceQuestion(account, questionId);
                yield RestBean.success(choiceQuestion);
            }
            case BLANK_QUESTION -> {
                BlankQuestion blankQuestion = blankQuestionService.findBlankQuestion(account, questionId);
                yield RestBean.success(blankQuestion);
            }
        };
    }
}
