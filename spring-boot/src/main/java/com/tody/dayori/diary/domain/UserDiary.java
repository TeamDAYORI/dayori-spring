package com.tody.dayori.diary.domain;

import com.tody.dayori.auth.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_user_diary")
@Entity
public class UserDiary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDiarySeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_seq")
    private Diary diary;

    // 0: 관리자X, 1: 관리자
    @Column(nullable = false)
    private Integer groupAuth;

    @CreationTimestamp
    @Column(name="ins_date")
    private LocalDateTime insDate;

    // 내가 create하면 1, 초대받았을때 수신자 0으로 추가, 수락하면 1로 변경
    @Column(nullable = false)
    private Integer isJoined;

    // 내 차례면 1, 아니면 0
    @Column(nullable = false)
    private Integer myTurn;

    private String userTitle;
    private String userCover;

    public static UserDiary create(User user, Diary diary) {
        UserDiary userDiary = new UserDiary();
        userDiary.groupAuth = 1;
        userDiary.user = user;
        userDiary.diary = diary;
        userDiary.userTitle = diary.getDiaryTitle();
        userDiary.userCover = diary.getDiaryCover();
        userDiary.isJoined = 1;
        userDiary.myTurn = 1;
        return userDiary;
    }

    public static UserDiary invited(User user, Diary diary) {
        UserDiary userDiary = new UserDiary();
        userDiary.groupAuth = 0;
        userDiary.user = user;
        userDiary.diary = diary;
        userDiary.userTitle = diary.getDiaryTitle();
        userDiary.userCover = diary.getDiaryCover();
        userDiary.isJoined = 0;
        userDiary.myTurn = 0;
        return userDiary;
    }

    public void accept(){
        this.isJoined = 1;
    }

    // 개인 아이콘, 제목 변경
    public void update(String title, String cover) {
        this.userCover = cover;
        this.userTitle = title;
    }

    public void grant() {
        this.myTurn = 1;
    }

    public void revoke() {
        this.myTurn = 0;
    }


}
