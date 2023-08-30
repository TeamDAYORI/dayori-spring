package com.tody.dayori.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingDiaryRequest {

    private String title;
    private String cover;
    private String password;
    private Integer period;
    private List<Long> additionalMembers;
}
