package Awrite_project.Awrite.web.dto;

import Awrite_project.Awrite.domain.Theme;
import lombok.Data;

@Data
public class DiaryUpdateRequestDTO {
    // 데이터 전송 객체(request body에 담겨온 부분 업데이트 수용할 DTO 만듦)
    private String title;
    private String content;
    private Theme theme;
    private boolean secret;
}
