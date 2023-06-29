package com.tody.dayori.diary.domain;

import com.tody.dayori.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_user_diary")
@Entity
public class UserDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDiarySeq;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "diary_seq")
    private Diary diary;

    @Column(nullable = false)
    private Integer groupAuth;

    @Builder
    public UserDiary (User user, Diary diary, Integer groupAuth) {
        this.user = user;
        this.diary = diary;
        this.groupAuth = groupAuth;
    }

}
