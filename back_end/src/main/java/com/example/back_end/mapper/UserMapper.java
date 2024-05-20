package com.example.back_end.mapper;

import com.example.back_end.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 利用 username 或 email 登录
    @Select("select * from db_account where username = #{text} or email = #{text}")
    Account findAccountByNameOrEmail(String text);

    // 添加账号到数据库
    @Select("insert into db_account (email, username, password) values (#{email}, #{username}, #{password})")
    Integer createAccount(String username, String password, String email);
}
