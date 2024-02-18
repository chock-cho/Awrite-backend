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
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Component

/*  일기 작성 시 사용할 DTO  */
public class DiaryRequestDTO {
    private User author;

    @Autowired
    private S3Config s3Config;

    private LocalDate date; //yyyy-MM-dd 형식

    private MultipartFile imgUrl;
    private String title;
    private String content;
    private boolean secret;
    @Min(value = 1, message = "테마 값은 1 이상이어야 합니다.")
    @Max(value = 4, message = "테마 값은 4 이하여야 합니다.")
    private Integer theme;
    public Diary toEntity() throws IOException {
        return Diary.builder()
                .date(date) // 문자열을 LocalDate로 변환
                .author(author)
                .imgUrl(imgUrl.getOriginalFilename())
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