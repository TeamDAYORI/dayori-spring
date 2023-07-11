package com.tody.dayori.user.domain;

import com.tody.dayori.diary.domain.UserDiary;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    private String userEmail;

    private String nickname;

    private String userImgUrl;

    @OneToMany(mappedBy = "user")
    private List<UserDiary> userDiaryList = new ArrayList<>();

    public void addDiary(UserDiary user) {
        this.userDiaryList.add(user);
    }
}
