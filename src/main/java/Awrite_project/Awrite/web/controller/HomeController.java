package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.service.DiaryService;
import Awrite_project.Awrite.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private LikeService likeService;

    // 다른 사용자들의 일기 목록보기
    //@GetMapping
}
