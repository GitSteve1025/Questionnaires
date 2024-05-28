package com.example.back_end.service.impl;

import com.example.back_end.entity.auth.Account;
import com.example.back_end.mapper.UserMapper;
import com.example.back_end.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
    String from;

    @Resource
    UserMapper mapper;

    @Resource
    MailSender mailSender;

    @Resource
    StringRedisTemplate template;

    @Lazy
    @Resource
    BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {// 用户名为空
            throw new UsernameNotFoundException("用户名不能为空");
        }
        Account account =  mapper.findAccountByNameOrEmail(username);
        if (account == null) {// 用户名不存在
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }

    // 发送邮件
    /*
     * 1.先生成对应的验证码
     * 2.把邮箱和对应的验证码直接放到 Redis 里面
     * （过期时间 3 mins， 如果此时重新要求发送邮件，只要剩余时间小于 2 mins（等价于每 60s 才能发送一次），就可以重新发送，重复此流程）
     * 3.发送验证码到指定邮箱
     * 4.如果发送失败，把 Redis 里面的刚刚插入的删除
     * 5.用户在注册时，从 Redis 里面取出键值对，check 验证码是否一致
     */

    @Override
    public String sendValidateEmail(String email, String sessionId,boolean hasAccount) {
        String key = "email:" + sessionId + ":" + email+":"+hasAccount;
        // 判断是否含有 key
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            Long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(180L);// 默认180，默认false
            if (expire > 120) {
                return "请求频繁，请稍后再试！";
            }
        }
        Account account = mapper.findAccountByNameOrEmail(email);
        if(hasAccount && account==null) return "没有此邮件地址的账户";
        if(!hasAccount && account!=null) return "此邮箱已被其他用户注册";
        // 生成 验证码
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // 生成 [100000, 999999] 的验证码
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("验证码是: " + code);
        try {
            mailSender.send(message);
            template.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);// 存入 redis 倒计时 3 mins
            return null;// 发送成功
        } catch (MailException e) {
            e.printStackTrace();
            return "邮件发送失败";// 发送失败
        }
    }

    //验证和注册
    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email+":false";
        // 判断是否含有 key
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            String s = template.opsForValue().get(key);
            if (s == null) return "验证码失效，请重新请求";
            if (s.equals(code)) {// 验证码正确 创建账号
                Account account= mapper.findAccountByNameOrEmail(username);
                if(account!=null) return "此用户名已被注册，请更换用户名";
                template.delete(key);//验证码使用后删除
                password = encoder.encode(password);// 密码加密
                if (mapper.createAccount(username, password, email) > 0) {// 插入成功返回 null
                    return null;
                } else {
                    return "内部错误，请联系管理员";
                }
            } else {// 验证码错误
                return "验证码错误";
            }
        } else {
            return "请先发送验证码";
        }
    }

    //验证
    @Override
    public String validateOnly(String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email+":true";
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            String s = template.opsForValue().get(key);
            if (s == null) return "验证码失效，请重新请求";
            if (s.equals(code)) {// 验证码正确 创建账号
                template.delete(key);//验证码使用后删除
                return null;
            } else {// 验证码错误
                return "验证码错误";
            }
        } else {
            return "请先发送验证码";
        }
    }

    @Override
    public boolean resetPassword(String password, String email) {
        password=encoder.encode(password);
        return mapper.resetPasswordByEmail(password,email) > 0;
    }
}

