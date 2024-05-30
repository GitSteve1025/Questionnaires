package com.example.back_end.mapper;

import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Question.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChoiceMapper {
    // 获取问题的所有选项
    @Select("select * from choice where questionId = #{questionId}")
    List<Choice> getChoices(int questionId);

    // 获取选项
    @Select("select * from choice where choiceId = #{choiceId}")
    Choice getChoice(int choiceId);

    // 获取该选项对应问题的 ID
    @Select("select questionId from choice where choiceId = #{choiceId}")
    Integer getQuestionIdOfChoice(int choiceId);

    // 添加选项
    @Insert("insert into choice " +
            "(questionId, sequenceId, content) " +
            "values " +
            "(#{question.questionId}, #{choice.sequenceId}, #{choice.content})")
    @Options(useGeneratedKeys = true, keyProperty = "choice.choiceId", keyColumn = "choiceId")
    Integer createChoice(Question question, Choice choice);

    // 修改选项
    @Update("update choice set " +
            "sequenceId = #{sequenceId}, " +
            "content = #{content} " +
            "where choiceId = #{choiceId}")
    Integer updateChoice(Choice choice);

    // 删除某个选项
    @Delete("delete from choice where choiceId = #{choiceId}")
    Integer deleteChoice(int choiceId);

    // 删除某个问题的所有选项
    @Delete("delete from  choice where questionId = #{questionId}")
    Integer deleteAllChoicesOfQuestion(int questionId);
}
