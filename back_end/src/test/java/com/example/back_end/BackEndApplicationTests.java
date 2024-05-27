package com.example.back_end;


import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import  com.example.back_end.mapper.QuestionnaireMapper;


import org.springframework.security.core.userdetails.User;

import java.util.List;

@SpringBootTest
class BackEndApplicationTests {

    @Resource
    UserMapper userMapper;

    @Resource
    QuestionnaireMapper questionnaireMapper;


    @Test
    void contextLoads() {
        // 测试 questionnaireMapper 功能
        Account account = new Account();
        account.setId(1);
        account.setUsername("admin");
        account.setEmail("3440346452@qq.com");
        account.setPassword("123456");

        List<Questionnaire> questionnaires = questionnaireMapper.getQuestionnaires(account);
        for (Questionnaire questionnaire : questionnaires) {
            System.out.println(questionnaire);
        }
    }
}
