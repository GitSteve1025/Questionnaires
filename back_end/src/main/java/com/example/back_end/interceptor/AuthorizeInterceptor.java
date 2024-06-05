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

//������
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    @Resource
    UserMapper mapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();//��context��ȡ��ǰ�Ѿ���֤���û�
       // System.out.println(authentication.getPrincipal());
        User user = (User)authentication.getPrincipal();//���õ�������֤���û���Ϣת��ΪUser����
        String username =user.getUsername();
        AccountUser account = mapper.findAccountUserByNameOrEmail(username);//ת����AccountUser
        request.getSession().setAttribute("account",account);//��account�����account����������
        return true;
}
}




