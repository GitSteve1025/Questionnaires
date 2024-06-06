package com.example.back_end.mapper;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.Question;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BlankMapper {
    // 获取 Blank
    @Select("select * from blank where questionId = #{questionId}")
    Blank getBlankByQuestionId(int questionId);

    // 获取 Blank
    @Select("select * from blank where blankId = #{blankId}")
    Blank getBlank(int blankId);

    // 获取 blank 对应的 questionId
    @Select("select questionId from blank where blankId = #{blankId}")
    Integer getQuestionIdOfBlank(int blankId);

    // 添加 Blank
    @Insert("insert into blank " +
            "(questionId, minCount, maxCount) " +
            "values " +
            "(#{question.questionId}, #{blank.minCount}, #{blank.maxCount})")
    @Options(useGeneratedKeys = true, keyProperty = "blank.blankId", keyColumn = "blankId")
    Integer createBlank(Question question, Blank blank);

    // 修改 Blank
    @Update("update blank set " +
            "minCount = #{minCount}, " +
            "maxCount = #{maxCount} " +
            "where blankId = #{blankId}")
    Integer updateBlank(Blank blank);

    // 删除 Blank
    @Delete("delete from blank where blankId = #{blankId}")
    Integer deleteBlank(int blankId);
}
