package com.example.back_end.mapper;

import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuestionnaireInfoMapper {
    // 获取填写该问卷的所有用户 ID
    @Select("select * from questionnaire_info where questionnaireId = #{questionnaireId}")
    List<Integer> getAccountsOfQuestionnaire(Questionnaire questionnaire);

    // 获取该用户填写的所有问卷 ID
    @Select("select * from questionnaire_info where userId = #{id}")
    List<Integer> getQuestionnairesOfAccount(Account account);

    // 添加填写信息
    @Insert("insert into questionnaire_info " +
            "(userId, questionnaireId, createdTime) " +
            "values " +
            "(#{account.id}, #{questionnaire.questionnaireId}, #{currentTime})")
    Integer insertQuestionnaireInfo(Account account, Questionnaire questionnaire, Date currentTime);

    // 删除某个问卷的所有信息
    @Delete("delete from questionnaire_info where questionnaireId = #{questionnaireId}")
    Integer deleteQuestionnaireInfo(Questionnaire questionnaire);
}