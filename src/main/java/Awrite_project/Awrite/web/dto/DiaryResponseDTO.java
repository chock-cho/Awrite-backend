package Awrite_project.Awrite.web.dto;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.Theme;
import Awrite_project.Awrite.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiaryResponseDTO {

    private Long id;
    private String title;
    private String content;
    private Theme theme;
    private LocalDateTime createdAt;
    private String month;
    private String imageUrl;
    private boolean secret;
    private boolean visible; // 비밀글이지만 권한이 있어 조회 가능할 때
    private Long heartCount; // 좋아요 개수

//    public DiaryResponseDTO(Diary diary, User user) {
//        this.id = diary.getId();
//        this.title = diary.getTitle();
//        this.content = diary.getContent();
//        this.theme = diary.getTheme();
//        this.createdAt = diary.getCreatedAt();
//        this.month = diary.getMonth();
//        // 모르겟음.................. 이게 왜 필요한거지....?
//        this.heartCount = diary.countHearts();
//    }
}
