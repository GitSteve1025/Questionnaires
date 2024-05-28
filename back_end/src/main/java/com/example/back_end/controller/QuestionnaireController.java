package com.example.back_end.controller;

import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.service.AuthorizeService;
import com.example.back_end.service.QuestionnaireService;
import jakarta.annotation.Resource;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@Validated
@RestController
@RequestMapping("/questionnaires")
public class QuestionnaireController {
//    // 所有问卷
//    private ArrayList<Questionnaire> questionnaires = new ArrayList<>();

    @Resource
    AuthorizeService authorizeService;

    @Resource
    QuestionnaireService questionnaireService;

    // 添加问卷
    @PostMapping("/create-questionnaire")
    public RestBean<String> addQuestionnaire(@Length(min = 1) @RequestParam("title") String title,
                                      @RequestParam("description") String description) {
        Account account = authorizeService.currentAccount();
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitle(title);
        questionnaire.setDescription(description);
        String s = questionnaireService.createQuestionnaire(account, questionnaire);
        if (s == null) {
            return RestBean.success("问卷创建成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 修改问卷
    @PostMapping("/update-questionnaire")
    public RestBean<String> updateQuestionnaire(@RequestParam("questionnaireId") int questionnaireId,
                                                @Length(min = 1) @RequestParam("title") String title,
                                                @RequestParam("description") String description) {
        Questionnaire questionnaire = questionnaireService.findQuestionnaire(questionnaireId);
        if (questionnaire == null) {
            return RestBean.failure(400, "questionnaireId 错误");
        }
        questionnaire.setTitle(title);
        questionnaire.setDescription(description);
        String s = questionnaireService.updateQuestionnaire(questionnaire);
        if (s == null) {
            return RestBean.success("修改成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 删除问卷
    @PostMapping("/delete-questionnaire")
    public RestBean<String> deleteQuestionnaire(@RequestParam("questionnaireId") int questionnaireId) {
        String s = questionnaireService.deleteQuestionnaire(questionnaireId);
        if (s == null) {
            return RestBean.success("删除成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/display-all-questionnaires")
    public RestBean<List<Questionnaire>> displayAllQuestionnaires() {
        return RestBean.success(questionnaireService.getAllQuestionnaire(authorizeService.currentAccount()));
    }
}
