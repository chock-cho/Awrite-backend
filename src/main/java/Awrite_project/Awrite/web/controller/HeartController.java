package Awrite_project.Awrite.web.controller;


import Awrite_project.Awrite.domain.Heart;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/hearts/{diaryId}/{userId}")
    // @PostMapping("/diary/{diaryId}/heart")
    public ResponseEntity<Void> addHeart(User user, @PathVariable Long diaryId) {
        Heart heart = heartService.addHeart(user, diaryId);
//        return ResponseEntity
//                .created(URI.create("/diary/" + diaryId + "/heart/" + heart.getId()))
//                .created(URI.create("/diary/" + diaryId + "/heart/" + heart.getId()))
//                .build();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/hearts/{diaryId}/{userId}")
//    @DeleteMapping("/diary/{diaryId}/heart")
    public ResponseEntity<Void> deleteHeart(User user, @PathVariable Long diaryId) {
        heartService.deleteHeart(user, diaryId);
        return ResponseEntity.noContent().build();
    }
}