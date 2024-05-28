package com.example.back_end.mapper;

import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Question.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChoiceMapper {
    // 获取问题的所有选项
    @Select("select * from choice where questionId = #{questionId}")
    List<Choice> getChoices(Question question);

    // 添加选项
    @Insert("insert into choice " +
            "(questionId, sequenceId, content) " +
            "values " +
            "(questionId = #{question.questionId}, sequenceId = #{choice.sequenceId}, content = #{choice.content})")
    Integer createChoice(Question question, Choice choice);

    // 修改选项
    @Update("update choice set " +
            "sequenceId = #{sequenceId}, " +
            "content = #{content} " +
            "where choiceId = #{choiceId}")
    Integer updateChoice(Choice choice);
}
