package com.tody.dayori.diary.dto;

import com.tody.dayori.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiaryRequest {

    private String title;
    private String cover;
    private Integer duration;
    private String password;
    private List<Long> members;

}

