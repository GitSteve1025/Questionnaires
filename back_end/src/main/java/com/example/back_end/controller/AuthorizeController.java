package com.example.back_end.controller;

import com.example.back_end.entity.RestBean;
import com.example.back_end.entity.auth.Account;
import com.example.back_end.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    private final String EMAIL_REGEX = "^[a-z0-9A-Z]+[-|a-z0-9A-Z._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
    private final String USERNAME_REGEX = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";

    @Resource
    AuthorizeService authorizeService;

    //（注册时） 发送邮件
    @PostMapping("/valid-register-email")
    public RestBean<String> validateRegisterEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email, HttpSession session) {
        String s = authorizeService.sendValidateEmail(email, session.getId(),false);
        if (s == null) {
            return RestBean.success("邮件已发送,请注意查收");
        } else {
            return RestBean.failure(400, s);
        }
    }

    //发送邮件，重置密码
    @PostMapping("/valid-reset-email")
    public RestBean<String> validateResetEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email, HttpSession session) {
        String s = authorizeService.sendValidateEmail(email, session.getId(),true);
        if (s == null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, s);
        }
    }

    //注册接口
    @PostMapping("/register")
    public RestBean<String> registerUser(@Pattern(regexp = USERNAME_REGEX) @Length(min = 2, max = 8) @RequestParam("username") String username,
                                         @Length(min = 6, max = 16) @RequestParam("password") String password,
                                         @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                         @Length(min = 6, max = 6) @RequestParam("code") String code,
                                         HttpSession session) {
        String s = authorizeService.validateAndRegister(username, password, email, code, session.getId());
        if (s == null) {
            return RestBean.success("注册成功");
        } else {
            return RestBean.failure(400, s);
        }
    }
    //重置密码
    /*
    1.发验证邮件
    2.验证验证码是否正确，若正确在Session中存一个标记
    3.用户发起重置密码请求，若存在标记，则成功重置
     */
    @PostMapping("/start-reset")
    public RestBean<String> startReset(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                       @Length(min = 6, max = 6) @RequestParam("code") String code,
                                       HttpSession session)
    {
        String s= authorizeService.validateOnly(email,code,session.getId());
            if(s==null)//验证成功
            {
                session.setAttribute("reset-password",email);
                return RestBean.success();
            }
            else{
                return RestBean.failure(400,s);
            }
    }

    @PostMapping("/do-reset")
    public RestBean<String> resetPassword(@Length(min = 6, max = 16) @RequestParam("password") String password,
                                          HttpSession session)
    {
        String email= (String) session.getAttribute("reset-password");
        if(email==null) return RestBean.failure(401,"请先完成邮箱验证");
        else if(authorizeService.resetPassword(password,email)){
            session.removeAttribute("reset-password");
            return RestBean.success("密码重置成功");
        }
        else {
            return RestBean.failure(500,"内部错误，请联系管理员");
        }
    }

    @GetMapping("/currentUser")
    public RestBean<Account> getCurrentUser() {
        return RestBean.success(authorizeService.currentAccount());
    }
}
