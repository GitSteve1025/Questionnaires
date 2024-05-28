package com.example.back_end.mapper;

import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionnaireMapper {
    // 利用用户 ID 得到创建的所有问卷
    @Select("select * from questionnaire where userId = #{id}")
    List<Questionnaire> getQuestionnaires(Account account);

    // 获取单个问卷
    @Select("select * from questionnaire where questionnaireId = #{questionnaireId}")
    Questionnaire getQuestionnaire(int questionnaireId);

    // 加入问卷
    @Insert("insert into questionnaire " +
            "(userId, title, description, state, createdTime, startTime, endTime) " +
            "values " +
            "(#{account.id}, #{questionnaire.title}, #{questionnaire.description}, #{questionnaire.state}, " +
            "#{questionnaire.createdTime}, #{questionnaire.startTime}, #{questionnaire.endTime})")
    @Options(useGeneratedKeys = true, keyProperty = "questionnaire.questionnaireId", keyColumn="questionnaireId")
    Integer createQuestionnaire(Account account, Questionnaire questionnaire);

    //修改问卷
    @Update("update questionnaire set " +
            "title = #{title}, " +
            "description = #{description}, " +
            "state = #{state}, " +
            "createdTime = #{createdTime}, " +
            "startTime = #{startTime}, " +
            "endTime = #{endTime} " +
            "where questionnaireId = #{questionnaireId}")
    Integer updateQuestionnaire(Questionnaire questionnaire);

    // 删除该问卷
    @Delete("delete from questionnaire where questionnaireId = #{questionnaireId}")
    Integer deleteQuestionnaire(Questionnaire questionnaire);
}
