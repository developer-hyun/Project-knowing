package com.example.demo.src.tmp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
