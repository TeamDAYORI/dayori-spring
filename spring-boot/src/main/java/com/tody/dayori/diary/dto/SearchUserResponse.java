package com.tody.dayori.diary.dto;


import com.tody.dayori.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserResponse {

    Long UserSeq;
    String NickName;
    String UserEmail;

    public static SearchUserResponse response(User user){
        SearchUserResponse ur = new SearchUserResponse();
        ur.UserSeq = user.getUserSeq();
        ur.UserEmail = user.getUserEmail();
        ur.NickName = user.getNickName();
        return ur;
    }
}
