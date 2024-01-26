package Awrite_project.Awrite.domain;

import Awrite_project.Awrite.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_ID")
    private Long id; // 일기 PK

    @Column(length = 300)
    private String title; // 일기 제목

    @Column(length = 2048)
    private String content; // 일기 내용

    @Enumerated(EnumType.ORDINAL)
    private Theme theme; // 일기 테마(감정)

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 단방향 관계, user 삭제되면 일기도 삭제
    @JoinColumn(name = "user_id")
    private User user; // 작성자 pk(FK)

    private LocalDate created_at; // 생성 시간
    @PrePersist
    public void created_at(){
        this.created_at = LocalDate.now();
        setMonth(created_at);
    }

    public String month; // 연월
    public void setMonth(LocalDate created_at){
        String year = Integer.toString(created_at.getYear());
        String month = Integer.toString(created_at.getMonthValue());
        this.month = year+month;
    }
    @Column
    private String imageUrl; // 첨부 이미지 파일

    private boolean secret; // 비밀글 여부(공개/비공개 여부)

    public Diary(User user, String content) {
        this.user = user;
        this.content = content;
    }


    public boolean isWrittenBy(User user) {
        return this.user == user;
    }
}