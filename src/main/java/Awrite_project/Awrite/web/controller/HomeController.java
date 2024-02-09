package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.service.DiaryService;
import Awrite_project.Awrite.service.HeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private HeartService heartService;

    // 다른 사용자들의 일기 목록보기
    //@GetMapping
}
