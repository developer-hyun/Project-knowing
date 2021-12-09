package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostUserSingUpReq {
    private String email;
    private String name;
    private String pwd;
    private String phNum;
    private String gender;
    private int birth;
    private String address; //7
    private String addressDatil;
    private String specialStatus;
    private String incomeLevel;
    private String incomeAvg;
    private String employmentState;
    private String schoolRecords; //11
    private String school;
    private String mainMajor;
    private String subMajor;
    private String semester;  //15
    private String lastSemesterScore;
    private String studentCategory;
    private String empolyCategory;
    private String foundationCategory;
    private String residentCategory;
    private String lifeCategory;  //20
    private String covidCategory;
    private String bookmark;
    private String provider;
    private String FCMTOKEN;
}
