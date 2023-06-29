package com.tody.dayori.diary.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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

//    @OneToMany(mappedBy = "tb_diary", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Page> pages = new ArrayList<>();

    public static Diary create(String title, String cover, Integer duration, String password) {
        Diary diary = new Diary();
        diary.diaryTitle = title;
        diary.diaryCover = cover;
        diary.diaryDuration = duration;
        diary.diaryPassword = password;
        diary.diaryWithdraw = false;
        return diary;
    }

    public void update(String title, Integer duration) {
        this.diaryTitle = title;
        this.diaryDuration = duration;
    }

}
