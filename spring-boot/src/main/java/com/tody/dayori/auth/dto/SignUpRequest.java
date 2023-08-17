package com.tody.dayori.auth.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String nickName;
    private String email;
    private String password;
}
