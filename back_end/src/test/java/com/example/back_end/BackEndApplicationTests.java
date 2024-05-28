package com.example.back_end;


import com.example.back_end.entity.Question.Categories;
import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.Questionnaire.State;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.ChoiceMapper;
import com.example.back_end.mapper.QuestionMapper;
import com.example.back_end.mapper.UserMapper;
import jakarta.annotation.Resource;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.back_end.mapper.QuestionnaireMapper;
import com.example.back_end.entity.Questionnaire.Questionnaire;


import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;

@SpringBootTest
class BackEndApplicationTests {

    @Resource
    UserMapper userMapper;

    @Resource
    QuestionnaireMapper questionnaireMapper;

    @Resource
    QuestionMapper questionMapper;

    @Resource
    ChoiceMapper choiceMapper;

    @Test
    void contextLoads() {



    }
}
