package Awrite_project.Awrite.domain;

import Awrite_project.Awrite.domain.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Column
    private Long id; // 일기 PK

    @Column(length = 300)
    private String title; // 일기 제목

    @Column(length = 2048)
    private String content; // 일기 내용

    @Column
    @Min(value = 1, message = "유효하지 않은 테마 값입니다. 1이상의 테마 값을 입력하세요")
    @Max(value = 4, message = "유효하지 않은 테마 값입니다. 4이하의 테마 값을 입력하세요")
    private Integer theme; // 일기 테마(감정)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author; // 작성자 pk(FK)

    @Column
    @NotNull
    private String imgUrl; // 첨부 이미지 파일

    @Column
    private boolean secret; // 비밀글 여부(공개/비공개 여부)

    @Column(unique = true)
    @NotNull
    // 지정 날짜. 한 날짜 당 하나의 일기만 조회 가능해야 한다.
    private LocalDate date;

    @Builder
    public Diary(LocalDate date, String title, String content, String imgUrl, User author, boolean secret){
        super();
        this.title = title; // 제목
        this.date = date; // 일기 작성 날짜
        this.content = content; // 내용
        this.author = author; // 작성자
        this.imgUrl = (imgUrl != null && !imgUrl.isEmpty())? imgUrl : null; // 첨부 사진
        this.secret = secret; // 비밀글 여부
    }
    //  @OneToMany(mappedBy = "diary")
    // private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<Heart> hearts = new ArrayList<>();

//    public boolean isHeartBy(User user) {
//        Objects.requireNonNull(user);
//        return this.hearts.stream().anyMatch(it -> it.getUser() == user);
//    }

    public long countHearts() {
        return this.hearts.size();
    }


}