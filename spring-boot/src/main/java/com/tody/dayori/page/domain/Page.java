package com.tody.dayori.page.domain;

import com.tody.dayori.user.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_page")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Page {

    @Id @GeneratedValue
    @Column(name = "page_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    // 다이어리

    @Column(name = "page_title")
    private String title;

    @Column(name="page_content")
    private String content;

    @CreatedDate
    @Column(name="page_date")
    private LocalDateTime date;

    @Builder
    public Page (String title, String content, LocalDateTime date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

}
