package com.tody.dayori.diary.domain;

import com.tody.dayori.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_user_diary")
@Entity
@IdClass(UserDiary.class)
public class UserDiary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDiarySeq;

//    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

//    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary")
    private Diary diary;

    // 0: 생성자, 1: 관리자, 2: 편집자
    @Column(nullable = false)
    private Integer groupAuth;

    public static UserDiary create(User user, Diary diary) {
        UserDiary userDiary = new UserDiary();
        userDiary.groupAuth = 0;
        userDiary.user = user;
        userDiary.diary = diary;
        return userDiary;
    }

//    public UserDiary(User user, Diary diary) {
//        setUser(user);
//        setDiary(diary);
//        this.groupAuth = 0;
//    }
//
//    private void setUser(User user) {
//        this.user = user;
//        user.addDiary(this);
//    }
//    private void setDiary(Diary diary) {
//        this.diary = diary;
//        diary.addDiary(this);
//    }



}
