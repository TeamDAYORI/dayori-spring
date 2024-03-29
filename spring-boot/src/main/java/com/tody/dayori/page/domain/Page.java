package com.tody.dayori.page.domain;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.auth.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_page")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Page {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_seq")
    private Diary diary;

    @Column(name = "page_title")
    private String title;

    @Column(name="page_content")
    private String content;

    @CreationTimestamp
    @Column(name="page_date")
    private LocalDateTime date;

    @Builder
    public Page (String title, String content, LocalDateTime date, User userInfo, Diary diary) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.userInfo = userInfo;
        this.diary = diary;
    }

    public void update (String title, String content) {
        this.title = title;
        this.content = content;
    }
}
