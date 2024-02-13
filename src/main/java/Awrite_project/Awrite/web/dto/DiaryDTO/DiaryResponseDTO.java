package Awrite_project.Awrite.web.dto.DiaryDTO;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
/* 일기 하나 상세 조회 시 반환할 DTO */
public class DiaryResponseDTO {

//    private long diaryId;
    private String title;
    private String content;
    private String imgUrl;
    private String authorName; // 작성자 이름
    private int authorProfile; // 작성자 프로필 사진
    private boolean secret; // 비밀글 여부
//    private Long heartsCount; // 좋아요 개수
    private boolean heartby; // 로그인한 사용자가 좋아요 눌렀는지


    public DiaryResponseDTO(Diary diary){
//        this.diaryId = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.imgUrl = diary.getImgUrl();
        this.authorName = (diary.getAuthor() != null) ? diary.getAuthor().getNickname() : null;
        this.authorProfile = (diary.getAuthor() != null) ? diary.getAuthor().getProfilePicture() : 1;
        this.secret = diary.isSecret();
//        this.heartsCount = diary.countHearts();
        this.heartby = diary.isHeartBy(diary.getAuthor());
    }

}
