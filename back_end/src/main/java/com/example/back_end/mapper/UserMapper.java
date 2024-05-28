package com.example.back_end.mapper;

import com.example.back_end.entity.auth.Account;
import com.example.back_end.entity.user.AccountUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    // 利用 username 或 email 登录
    @Select("select * from db_account where username = #{text} or email = #{text}")
    Account findAccountByNameOrEmail(String text);

    @Select("select * from db_account where username = #{text} or email = #{text}")
    AccountUser findAccountUserByNameOrEmail(String text);

    // 添加账号到数据库
    @Insert("insert into db_account (email, username, password) values (#{email}, #{username}, #{password})")
    Integer createAccount(String username, String password, String email);

    @Update("update db_account set password = #{password} where email = #{email}")
    Integer resetPasswordByEmail(String password,String email);
}
