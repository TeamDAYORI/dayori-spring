package com.tody.dayori.auth.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String userName;
    private String email;
    private char[] password;
}
