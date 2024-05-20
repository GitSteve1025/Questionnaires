package com.example.back_end.service.impl;

import com.example.back_end.entity.Account;
import com.example.back_end.mapper.UserMapper;
import com.example.back_end.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public boolean sendValidateEmail(String email, String sessionId) {
        String key = "email:" + sessionId + ":" + email;
        if (Boolean.TRUE.equals(template.hasKey(sessionId))) {// 含有 key
            template.getExpire(key, TimeUnit.SECONDS);
            Long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(180L);// 默认180，默认false
            if (expire > 120) {
                return false;
            }
        }
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
            return true;// 发送成功
        } catch (MailException e) {
            e.printStackTrace();
            return false;// 发送失败
        }
    }
}
