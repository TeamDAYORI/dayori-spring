package com.tody.dayori.auth.entity;

import com.tody.dayori.auth.dto.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userSeq;

    private String userEmail;

    private String password;

    private String userName;

    private String userImgUrl;

    public AuthResponse toAuthResponse() {
        return AuthResponse.builder()
                .seq(this.userSeq)
                .userEmail(this.userEmail)
                .build();
    }
}
