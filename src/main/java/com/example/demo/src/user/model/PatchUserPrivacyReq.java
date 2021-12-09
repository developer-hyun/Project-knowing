package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserPrivacyReq {
    private String email;
    private String name;
    private String pwd;
    private String phNum;
    private String gender;
    private int birth;
}
