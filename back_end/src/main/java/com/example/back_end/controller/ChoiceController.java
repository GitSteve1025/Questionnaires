package com.example.back_end.controller;

import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.QuestionMapper;
import com.example.back_end.mapper.QuestionnaireMapper;
import com.example.back_end.service.AuthorizeService;
import com.example.back_end.service.ChoiceService;
import jakarta.annotation.Resource;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/choice")
public class ChoiceController {

    @Resource
    ChoiceService choiceService;

    @Resource
    AuthorizeService authorizeService;

    @Resource
    QuestionMapper questionMapper;

    @Resource
    QuestionnaireMapper questionnaireMapper;

    @PostMapping("/create")
    public RestBean<List<Choice>> createChoices(@RequestBody Integer questionId,
                                                @RequestParam("content") List<String> content) {
        Account account = authorizeService.currentAccount();
        Integer questionnaireId = questionMapper.getQuestionnaireIdOfQuestion(questionId);
        if (questionnaireId == null) {
            return null;
        }
        Integer userId = questionnaireMapper.getUserIdOfQuestionnaire(questionnaireId);
        if (userId == null || userId != account.getId()) {
            return null;
        } // 权限验证
        Question question = new Question();
        question.setQuestionId(questionId);
        List<Choice> choices = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            Choice choice = new Choice();
            choice.setSequenceId(i + 1);
            choice.setContent(content.get(i));
            String s = choiceService.createChoice(question, choice);
            if (s == null) {
                choices.add(choice);
            } else {
                return null;
            }
        }
        return RestBean.success(choices);
    }

    @PostMapping("/delete")
    public RestBean<String> deleteChoice() {
        // to do
        return null;
    }

    @PostMapping("/update")
    public RestBean<String> updateChoice(@RequestBody Integer choiceId,
                                         @RequestParam("content") String content) {
        Account account = authorizeService.currentAccount();
        Choice choice = choiceService.findChoice(account, choiceId);
        if (choice == null) {
            return RestBean.failure(400, "选项不存在");
        }
        choice.setContent(content);
        String s = choiceService.updateChoice(account, choice);
        if (s == null) {
            return RestBean.success("修改成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @GetMapping("/find")
    public RestBean<Choice> findChoice(@RequestBody Integer choiceId) {
        return RestBean.success(choiceService.findChoice(authorizeService.currentAccount(), choiceId));
    }

    @GetMapping("/display-all")
    public RestBean<List<Choice>> findChoices(@RequestBody Integer questionId) {
        return RestBean.success(choiceService.getChoices(authorizeService.currentAccount(), questionId));
    }
}
