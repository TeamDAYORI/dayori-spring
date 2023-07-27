package com.tody.dayori.diary.domain;

import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tb_diary")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Diary extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diarySeq;

//    @Column(nullable = false)
//    private String diaryCreator;

    @CreationTimestamp
    @Column
    private LocalDateTime diaryCreateAt;

    @Column(nullable = false)
    private String diaryTitle;

    @Column(nullable = false)
    private Boolean diaryWithdraw;

    @Column(nullable = false)
    private String diaryCover;

    @Column(nullable = false)
    private Integer diaryDuration;

    private String diaryPassword;

    private String invitationCode;

    @Column(nullable = false)
    private Long diaryWriter;

    @Column(nullable = false)
    private Boolean nextAble;

    @OneToMany(mappedBy = "diary")
    private List<UserDiary> userDiaryList = new ArrayList<>();


    public static Diary create(Long userSeq, String title, String cover, Integer duration, String password) {
        Diary diary = new Diary();
        diary.diaryTitle = title;
        diary.diaryCover = cover;
        diary.diaryDuration = duration;
        diary.diaryPassword = password;
        diary.diaryWithdraw = false;
        diary.invitationCode = "";
        diary.diaryWriter = userSeq;
        diary.nextAble = false;
        return diary;
    }

    public void addInvCode(String code) {
        this.invitationCode = code;
    }

    public void update(String title, Integer duration) {
        this.diaryTitle = title;
        this.diaryDuration = duration;
    }

    public void updateWriter(Long userSeq) {
        this.diaryWriter = userSeq;
        this.nextAble = false;
    }

}
