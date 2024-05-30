package com.example.back_end.controller;

import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.service.AuthorizeService;
import com.example.back_end.service.QuestionnaireService;
import jakarta.annotation.Resource;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RequestParam 是前端必要参数
// @RequestBody json 格式

// 问卷接口
@Data
@Validated
@RestController
@RequestMapping("/questionnaires")
public class QuestionnaireController {

    @Resource
    AuthorizeService authorizeService;

    @Resource
    QuestionnaireService questionnaireService;

    // 通过标题搜索问卷
    @GetMapping("/search")
    public RestBean<List<Questionnaire>> searchQuestionnaire(@RequestParam @Length(min = 1) String title) {
        // to do
        // 根据标题搜索相关问卷
        return null;
    }

    // 通过问卷 ID 获取问卷 (填写者使用)
    @GetMapping("/")
    public RestBean<Questionnaire> searchQuestionnaireById(@RequestBody int questionnaireId) {
        return RestBean.success(questionnaireService.getQuestionnaire(questionnaireId)); // 未找到则返回 null
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

    // 修改问卷的标题/描述
    @PostMapping("/update")
    public RestBean<String> updateQuestionnaire(@RequestBody int questionnaireId, // 需要提供问卷ID
                                                @Length(min = 1) @RequestParam("title") String title, // 标题不能为空
                                                @RequestParam(value = "description", required = false) String description) {
        Account account = authorizeService.currentAccount(); // 当前用户
        Questionnaire questionnaire = questionnaireService.createrFindQuestionnaire(account, questionnaireId); // 先找到该问卷
        if (questionnaire == null) { // 问卷ID错误 或 不是自己的问卷
            return RestBean.failure(400, "问卷不存在");
        }
        questionnaire.setTitle(title); // 更改标题
        questionnaire.setDescription(description); // 更改描述
        String s = questionnaireService.updateQuestionnaire(questionnaire);// 更新问卷
        if (s == null) {
            return RestBean.success("修改成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 删除问卷
    @PostMapping("/delete")
    public RestBean<String> deleteQuestionnaire(@RequestBody int questionnaireId) {
        Account account = authorizeService.currentAccount(); // 当前用户
        Questionnaire questionnaire = questionnaireService.createrFindQuestionnaire(account, questionnaireId); // 先找到该问卷
        if (questionnaire == null) { // 问卷ID错误 或 不是自己的问卷
            return RestBean.failure(400, "问卷不存在");
        }
        String s = questionnaireService.deleteQuestionnaire(questionnaireId);
        if (s == null) {
            return RestBean.success("删除成功");
        } else {
            return RestBean.failure(400, s);
        }
    }

    // 展示所有问卷
    @PostMapping("/display-all")
    public RestBean<List<Questionnaire>> displayAllQuestionnaires() {
        return RestBean.success(questionnaireService.getAllQuestionnaire(authorizeService.currentAccount()));
    }
}
