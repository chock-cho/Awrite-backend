package Awrite_project.Awrite.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;

public class ViewController {

    // 홈 페이지
    @GetMapping("/home")
    public String home(){
        return "home";
    }

    // 로그인
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // 회원가입?
    @GetMapping("/")
    public String signup(){
        return "/";
    }

    // 로그아웃 구현
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
