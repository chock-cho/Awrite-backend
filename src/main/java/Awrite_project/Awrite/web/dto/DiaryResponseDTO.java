package Awrite_project.Awrite.web.dto;

import Awrite_project.Awrite.domain.Theme;
import lombok.Data;

import java.time.LocalDate;
@Data
public class DiaryResponseDTO {

    private Long id;
    private String content;
    private Theme theme;
    private LocalDate created_at;
    private String month;
    private String imageUrl;
    private boolean secret;
    private boolean visible; // 비밀글이지만 권한이 있어 조회 가능할 때
}
