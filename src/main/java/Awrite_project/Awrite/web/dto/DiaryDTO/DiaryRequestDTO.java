package Awrite_project.Awrite.web.dto.DiaryDTO;

import Awrite_project.Awrite.config.S3Config;
import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@Setter
@Component

/*  일기 작성 시 사용할 DTO  */
public class DiaryRequestDTO {
    private User author;


    private MultipartFile imgUrl;
    private String title;
    private String content;
    private boolean secret;
    @Min(value = 1, message = "테마 값은 1 이상이어야 합니다.")
    @Max(value = 4, message = "테마 값은 4 이하여야 합니다.")
    private Integer theme;
    public Diary toEntity(){
        return Diary.builder()
                .author(author)
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .title(title)
                .content(content)
                .secret(secret)
                .theme(theme)
                .build();
    }
    public void setAuthor(User author) {
        this.author = author;
    }
}
