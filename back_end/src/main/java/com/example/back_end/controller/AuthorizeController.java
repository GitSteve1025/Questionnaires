package com.example.back_end.controller;

import com.example.back_end.entity.RestBean;
import com.example.back_end.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    private final String EMAIL_REGEX = "^[a-z0-9A-Z]+[-|a-z0-9A-Z._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";

    @Resource
    AuthorizeService authorizeService;

    // 发送邮件
    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email, HttpSession session) {
        if (authorizeService.sendValidateEmail(email, session.getId())) {
            return RestBean.success("邮件已发送");
        } else {
            return RestBean.failure(400, "邮件发送失败，请联系管理员");
        }
    }
}
