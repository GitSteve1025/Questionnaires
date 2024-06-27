package com.example.back_end.controller;


import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.service.InfoService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/info")
public class InfoController {
    @Resource
    InfoService infoService;
    // 获取选项被选的次数
    @PostMapping("/choice")
    RestBean<Integer> getChoiceInfo(@RequestParam("choiceId") Integer choiceId) {
        return RestBean.success(infoService.getChoiceInfo(choiceId));
    }
    // 获取填空的内容
    @PostMapping("/blank")
    RestBean<List<String>> getBlankInfo(@RequestParam("blankId") Integer blankId) {
        return RestBean.success(infoService.getBlankInfo(blankId));
    }
    // 获取user date
    @PostMapping("/user")
    RestBean<Map<Account, Date>> getQuestionnaireInfo(@RequestParam("questionnaireId") Integer questionnaireId) {
        return RestBean.success(infoService.getQuestionnaireInfo(questionnaireId));
    }
}
