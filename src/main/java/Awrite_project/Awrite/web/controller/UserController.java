package Awrite_project.Awrite.web.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class UserController {
    // 인증을 받아야 함 -> 회원가입 (피그마 상 1페이지로 넘어감)
    @GetMapping("/home")
    public String joinForm(){
        return "/users/join";
    }


}
