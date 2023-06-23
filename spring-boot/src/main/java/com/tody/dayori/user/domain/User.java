package com.tody.dayori.user.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userSeq;

    private String userEmail;

    private String nickname;

    private String userImgUrl;

}
