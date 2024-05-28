package com.example.back_end.mapper;

import com.example.back_end.entity.Question.BlankQuestion.BlankQuestion;
import com.example.back_end.entity.Question.Question;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BlankQuestionMapper {
    // 读出填空限制信息
    @Select("select * from blankquestion where questionId = #{questionId}")
    BlankQuestion getBlankQuestion(Question question);

    // 写入填空限制信息
    @Insert("insert into blankquestion " +
            "(questionId, validation, type) " +
            "values " +
            "(#{questionId}, #{validation}, #{type})")
    Integer insertBlankQuestion(BlankQuestion blankQuestion);

    // 修改填空限制信息
    @Update("update blankquestion set " +
            "validation = #{validation}, " +
            "type = #{type} " +
            "where questionId = #{questionId}")
    Integer updateBlankQuestion(BlankQuestion blankQuestion);

    // 删除填空限制信息
    @Delete("delete from blankquestion where questionId = #{questionId}")
    Integer deleteBlankQuestion(BlankQuestion blankQuestion);
}
