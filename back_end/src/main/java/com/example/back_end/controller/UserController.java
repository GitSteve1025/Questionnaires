package com.example.back_end.controller;

import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.user.AccountUser;
import com.example.back_end.interceptor.AuthorizeInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/user")//反映是否请求成功
public class UserController {

    @GetMapping("/me")
    public RestBean<AccountUser> me(@SessionAttribute("account")AccountUser user){
        return RestBean.success(user);
    }
}
