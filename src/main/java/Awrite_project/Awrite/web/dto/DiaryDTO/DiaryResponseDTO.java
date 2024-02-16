package Awrite_project.Awrite.web.dto.DiaryDTO;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
/* 일기 하나 상세 조회 시 반환할 DTO */
public class DiaryResponseDTO {

    private LocalDate ymlCreatedAt;
    private String title;
    private String content;
    private String imgUrl;
    private String myName; // 작성자 이름
    private int myProfile; // 작성자 프로필 사진

    public DiaryResponseDTO(Diary diary){
        this.ymlCreatedAt = diary.getYmlCreatedAt();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.imgUrl = diary.getImgUrl();
        this.myName = (diary.getAuthor() != null) ? diary.getAuthor().getNickname() : null;
        this.myProfile = (diary.getAuthor() != null) ? diary.getAuthor().getProfilePicture() : 1;
    }

}
