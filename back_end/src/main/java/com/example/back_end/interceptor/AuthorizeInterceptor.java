package com.example.back_end.interceptor;

import com.example.back_end.entity.user.AccountUser;
import com.example.back_end.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.HandlerInterceptor;

//拦截器
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    @Resource
    UserMapper mapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();//从context获取当前已经验证的用户
       // System.out.println(authentication.getPrincipal());
        User user = (User)authentication.getPrincipal();//将得到的已验证的用户信息转换为User类型
        String username =user.getUsername();
        AccountUser account = mapper.findAccountUserByNameOrEmail(username);//转换成AccountUser
        request.getSession().setAttribute("account",account);//将account与键“account”关联起来
        return true;
}
}




