package Awrite_project.Awrite.domain;

import Awrite_project.Awrite.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

//    private LocalDateTime createdAt_LD; // 생성 시간(년월)
    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
        setMonth(createdAt);
    }

    public String month; // 연월
    public void setMonth(LocalDateTime createdAt){
        String year = Integer.toString(createdAt.getYear());
        String month = Integer.toString(createdAt.getMonthValue());
        this.month = year + month;
    }
    @Column
    private String imageUrl; // 첨부 이미지 파일

    private boolean secret; // 비밀글 여부(공개/비공개 여부)

//    public Diary(User user, String content) {
//        this.user = user;
//        this.content = content;
//    }

    @OneToMany(mappedBy = "diary")
    private List<Heart> hearts = new ArrayList<>();

    @Builder
    public Diary(User user, String title, String content, String month, LocalDateTime createdAt) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.month = month;
        this.createdAt = createdAt;
    }

    public boolean isWrittenBy(User user) {
        return this.user == user;
    }

//    public boolean isHeartBy(User user) {
//        Objects.requireNonNull(user);
//        return this.hearts.stream().anyMatch(it -> it.getUser() == user);
//    }

    public long countHearts() {
        return this.hearts.size();
    }
}