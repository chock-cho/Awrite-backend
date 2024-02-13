package Awrite_project.Awrite.domain;

import Awrite_project.Awrite.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Heart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE) // 일기 지워지면 좋아요도 지워지게
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "diary_id")
    private Diary diary; // 좋아요 눌린 일기

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user; // 좋아요를 누른 유저


//    @Builder
//    public Heart(Diary diary, User user, LocalDateTime createdAt) {
//        this.diary = diary;
//        this.user = user;
//        this.createdAt = createdAt;
//    }

    @Builder
    public Heart(User user, Diary diary) {
        this.diary = diary;
        this.user = user;
    }

//    public static Heart create(){
//        return new Heart();
//    }
}
