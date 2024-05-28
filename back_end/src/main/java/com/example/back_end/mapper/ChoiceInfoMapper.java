package com.example.back_end.mapper;

import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ChoiceInfoMapper {
    // 获取该选项被选择的次数
    @Select("select * from choice_info where choiceId = #{choiceId}")
    @Result(column = "count")
    Integer getChoiceInfo(Choice choice);

    // 添加选项
    @Insert("insert into choice_info " +
            "(choiceId, count)" +
            "values " +
            "(#{choice.choiceId}, #{count})")
    Integer insertChoiceInfo(Choice choice, int count);

    // 修改某个选项被选次数
    @Update("update choice_info set " +
            "count = #{count} " +
            "where choiceId = #{choice.choiceId}")
    Integer updateChoiceInfo(Choice choice, int count);

    // 删除选项信息
    @Delete("delete from choice_info where choiceId = #{choiceId}")
    Integer deleteChoiceInfo(Choice choice);
}
