package Awrite_project.Awrite.web.dto.DiaryDTO;

import Awrite_project.Awrite.domain.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
/* Home 화면에서 보일 일기 리스트 */
public class DiaryListResponseDTO {
    private String title;
    private String content;
    private String imgUrl;
    private boolean secret; // 비밀글 여부
    private String authorName; // 작성자 이름
    private int authorProfile; // 작성자 프로필 사진

    public DiaryListResponseDTO(Diary diary){
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.imgUrl = diary.getImgUrl();
        this.secret = diary.isSecret();
        this.authorName = (diary.getAuthor() != null) ? diary.getAuthor().getNickname() : null;
        this.authorProfile = (diary.getAuthor() != null) ? diary.getAuthor().getProfilePicture() : 1;
    }

}
