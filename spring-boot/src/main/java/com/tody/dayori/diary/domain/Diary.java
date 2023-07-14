package com.tody.dayori.diary.domain;

import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
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

    @Column(nullable = false)
    private String diaryTitle;

    @Column(nullable = false)
    private Boolean diaryWithdraw;

    @Column(nullable = false)
    private String diaryCover;

    @Column(nullable = false)
    private Integer diaryDuration;

    private String diaryPassword;

    @Column(nullable = false)
    private String invitationCode;

    @OneToMany(mappedBy = "diary")
    private List<UserDiary> userDiaryList = new ArrayList<>();

    public static String createInvitationCode(int length) {
        byte[] uuidBytes = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
        byte[] base64Bytes = Base64.encodeBase64(uuidBytes);
        String base64String = new String(base64Bytes, StandardCharsets.UTF_8).trim();
        return base64String.substring(0, length);
    }

    public static Diary create(String title, String cover, Integer duration, String password) {
        Diary diary = new Diary();
        diary.diaryTitle = title;
        diary.diaryCover = cover;
        diary.diaryDuration = duration;
        diary.invitationCode = createInvitationCode(32);
        diary.diaryPassword = password;
        diary.diaryWithdraw = false;
        return diary;
    }

    public void update(String title, Integer duration) {
        this.diaryTitle = title;
        this.diaryDuration = duration;
    }

//    public void addDiary(UserDiary diary) {
//        this.userDiaryList.add(diary);
//    }

}
