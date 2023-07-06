package com.tody.dayori.comment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tody.dayori.page.domain.Page;
import com.tody.dayori.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_seq")
    private Page page;

    @Column(name = "comment_content")
    private String content;

    @CreationTimestamp
    @Column(name = "comment_date")
    private LocalDateTime date;

    @Builder
    public Comment(String content, LocalDateTime date, User userInfo, Page page) {
        this.content = content;
        this.date = date;
        this.userInfo = userInfo;
        this.page = page;
    }
}
