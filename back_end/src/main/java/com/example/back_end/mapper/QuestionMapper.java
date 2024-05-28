package com.example.back_end.mapper;

import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.Questionnaire.Questionnaire;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    // 读出某个问卷的所有问题
    @Select("select * from question where questionnaireId = #{questionnaireId}")
    List<Question> getQuestions(Questionnaire questionnaire);

    // 添加问题
    @Insert("insert into question " +
            "(questionnaireId, sequenceId, title, category, necessary) " +
            "values " +
            "(#{questionnaire.questionnaireId}, #{question.sequenceId}, #{question.title}, #{question.category}, #{question.necessary})")
    @Options(useGeneratedKeys = true, keyProperty = "question.questionId", keyColumn = "questionId")
    Integer createQuestion(Questionnaire questionnaire, Question question);

    // 修改问题
    @Update("update question set " +
            "sequenceId = #{sequenceId}, " +
            "title = #{title}, " +
            "category = #{category}, " +
            "necessary = #{necessary} " +
            "where questionId = #{questionId}")
    Integer updateQuestion(Question question);

    // 删除问题
    @Delete("delete from question where questionId = #{questionId}")
    Integer deleteQuestion(Question question);
}
