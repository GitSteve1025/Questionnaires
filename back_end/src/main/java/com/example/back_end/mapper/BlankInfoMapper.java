package com.example.back_end.mapper;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlankInfoMapper {
    // 获取该填空的所有回答
    @Select("select * from blank_info where blankId = #{blankId}")
    List<String> getBlankInfo(Blank blank);

    // 添加某个填空的回答
    @Insert("insert into blank_info " +
            "(blankId, content)" +
            "values " +
            "(#{blankId}, #{content})")
    Integer insertBlankInfo(Blank blank);

    // 删除某个填空的所有回答
    @Delete("delete from blank_info where blankId = #{blankId}")
    Integer deleteBlankInfo(Blank blank);
}
