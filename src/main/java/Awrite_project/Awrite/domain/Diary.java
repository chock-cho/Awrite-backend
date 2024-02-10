package Awrite_project.Awrite.domain;

import Awrite_project.Awrite.domain.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 추가된 부분
    @JoinColumn(name = "user_id")
    private User author; // 작성자 pk(FK)

    @Builder
    public Diary(String title, String content, String imgUrl, User author, boolean secret){
        super();  // Set discriminator value in the constructor
        this.title = title; // 제목
        this.content = content; // 내용
        this.author = author; // 작성자
        this.imgUrl = imgUrl != null && !imgUrl.isEmpty()? imgUrl : null; // 첨부 사진
        this.secret = secret; // 비밀글 여부
    }
    @Column
    private String imgUrl; // 첨부 이미지 파일

    @Column
    private boolean secret; // 비밀글 여부(공개/비공개 여부)

//    public Diary(User user, String content) {
//        this.user = user;
//        this.content = content;
//    }

    //  @OneToMany(mappedBy = "diary")
    // private List<Heart> hearts = new ArrayList<>();

    public boolean isWrittenBy(Long authorId) {
        return this.author.getId() == authorId;
    }

    @OneToMany(mappedBy = "diary")
    private List<Heart> hearts = new ArrayList<>();

//    public boolean isHeartBy(User user) {
//        Objects.requireNonNull(user);
//        return this.hearts.stream().anyMatch(it -> it.getUser() == user);
//    }

    public long countHearts() {
        return this.hearts.size();
    }

}