package com.example.back_end.mapper;

import com.example.back_end.entity.Questionnaire.Questionnaire;
import com.example.back_end.entity.auth.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionnaireMapper {
    // 利用用户 ID 得到创建的所有问卷
    @Select("select * from questionnaire where userId = #{id}")
//    @Result(column = "questionnaireId", property = "questionnaireId")
//    @Result(column = "title", property = "title")
//    @Result(column = "description", property = "description")
//    @Result(column = "state", property = "state")
//    @Result(column = "createdTime", property = "createdTime")
//    @Result(column = "startTime", property = "startTime")
//    @Result(column = "endTime", property = "endTime")
//    column 表示数据库表中的字段，property 表示映射的变量，一样则可以省略
    List<Questionnaire> getQuestionnaires(Account account);
}
