package com.example.back_end.mapper;

import com.example.back_end.entity.Question.ChoiceQuestion.ChoiceQuestion;
import com.example.back_end.entity.Question.Question;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ChoiceQuestionMapper {
    // 读取选择题的限制信息
    @Select("select * from choicequestion where questionId = #{questionId}")
    ChoiceQuestion getChoiceQuestion(Question question);

    // 写入选择题的限制信息
    @Insert("insert into choicequestion " +
            "(questionId, minSelected, maxSelected) " +
            "values " +
            "(#{questionId}, #{minSelected}, #{maxSelected})")
    Integer insertChoiceQuestion(ChoiceQuestion choiceQuestion);

    // 修改选择题的限制信息
    @Update("update choicequestion set " +
            "minSelected = #{minSelected}, " +
            "maxSelected = #{maxSelected} " +
            "where questionId = #{questionId}")
    Integer updateChoiceQuestion(ChoiceQuestion choiceQuestion);

    // 删除限制信息
    @Delete("delete from choicequestion where questionId = #{questionId}")
    Integer deleteChoiceQuestion(ChoiceQuestion choiceQuestion);
}
