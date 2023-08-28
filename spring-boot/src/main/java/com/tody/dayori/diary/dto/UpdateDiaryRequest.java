package com.tody.dayori.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDiaryRequest {

    private String title;
    private String cover;
    private List<Long> additionalMembers;
}
