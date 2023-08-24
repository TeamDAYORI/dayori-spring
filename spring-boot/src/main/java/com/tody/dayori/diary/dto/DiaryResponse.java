package com.tody.dayori.diary.dto;

import com.tody.dayori.diary.domain.Diary;
import com.tody.dayori.diary.domain.UserDiary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponse {

    Long DiarySeq;
    String DiaryCover;
    String DiaryTitle;
    Integer IsJoined;
    Integer MyTurn;

    public static DiaryResponse response(UserDiary userDiary){
        DiaryResponse dr = new DiaryResponse();
        Diary diary = userDiary.getDiary();
        dr.DiarySeq = diary.getDiarySeq();
        dr.DiaryCover = userDiary.getUserCover();
        dr.DiaryTitle = userDiary.getUserTitle();
        dr.IsJoined = userDiary.getIsJoined();
        dr.MyTurn = userDiary.getMyTurn();
        return dr;
    }

}
