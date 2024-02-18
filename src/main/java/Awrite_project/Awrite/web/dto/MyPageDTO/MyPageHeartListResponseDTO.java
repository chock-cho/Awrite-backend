package Awrite_project.Awrite.web.dto.MyPageDTO;

import Awrite_project.Awrite.domain.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
/* MyPage 화면에서 보일 좋아요 일기 리스트(top5는 사진, 제목, 날짜) */
public class MyPageHeartListResponseDTO {
    private long diaryId;
    private String title;
    private String content;
    private String imgUrl;
    private boolean secret; // 비밀글 여부
    private String authorName; // 작성자 이름
    private int authorProfile; // 작성자 프로필 사진
    private int theme;
    private LocalDate ymlCreatedAt;
    private Long heartsCount; // 좋아요 개수
    private boolean heartby; // 로그인한 사용자가 좋아요 눌렀는

    public MyPageHeartListResponseDTO(Diary diary, boolean heartby){
        this.diaryId = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.imgUrl = diary.getImgUrl();
        this.secret = diary.isSecret();
        this.authorName = (diary.getAuthor() != null) ? diary.getAuthor().getNickname() : null;
        this.authorProfile = (diary.getAuthor() != null) ? diary.getAuthor().getProfilePicture() : 1;
        this.theme = diary.getTheme();
        this.ymlCreatedAt = diary.getYmlCreatedAt();
        this.heartsCount = diary.countHearts();
        this.heartby = heartby;

    }

}

