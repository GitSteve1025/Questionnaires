package com.example.back_end.mapper;

import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import org.apache.ibatis.annotations.*;

@Mapper
public interface Choice_InfoMapper {
    // 获取该选项被选择的次数
    @Select("select * from choice_info where choiceId = #{choiceId}")
    Integer getChoiceInfo(Choice choice);

    // 添加选项
    @Insert("insert into choice_info " +
            "(choiceId, count)" +
            "values " +
            "(#{choiceId}, #{count})")
    Integer insertChoiceInfo(Choice choice);

    // 修改某个选项被选次数
    @Update("update choice_info set " +
            "count = #{count}")
    Integer updateChoiceInfo(Choice choice);

    // 删除选项信息
    @Delete("delete from choice_info where choiceId = #{choiceId}")
    Integer deleteChoiceInfo(Choice choice);
}
