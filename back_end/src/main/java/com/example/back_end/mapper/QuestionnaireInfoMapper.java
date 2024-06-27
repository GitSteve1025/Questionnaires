package com.example.back_end.mapper;

import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionnaireInfoMapper {
    // 获取填写该问卷的所有用户 ID 和 对应的时间
    @Select("select userId, createdTime from questionnaire_info where questionnaireId = #{questionnaireId}")
    Map<Integer, Date> getQuestionnaireInfo(int questionnaireId);

    // 获取该用户填写的所有问卷 ID
    @Select("select questionnaireId from questionnaire_info where userId = #{id}")
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