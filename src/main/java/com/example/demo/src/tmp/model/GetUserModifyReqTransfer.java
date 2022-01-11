package com.example.demo.src.tmp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserModifyReqTransfer {
    private String email;
    private String name;
    private String pwd;
    private String phNum;
    private String gender;
    private int birth;
    private String address; //7
    private String  addressDetail;
    private List<String> specialStatus;
    private String incomeLevel;
    private String incomeAvg;
    private List<String> employmentState;
    private String schoolRecords; //13
    private String school;
    private String mainMajor;
    private String subMajor;
    private String semester;  //17
    private String lastSemesterScore;
    private List<String> studentCategory;
    private List<String> empolyCategory;
    private List<String> foundationCategory;
    private List<String> residentCategory;
    private List<String> lifeCategory;  //23
    private List<String> medicalCategory;
    private List<String> bookmark;
    private String provider; //26
    private String FCMTOKEN;
}
