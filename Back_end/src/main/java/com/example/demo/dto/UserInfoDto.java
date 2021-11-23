package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private int userNum;
    private String id;
    private String password;
    private String auth;

    public UserInfoDto(String id, String password, String auth) {
        this.id = id;
        this.password = password;
        this.auth = auth;
    }
}
