package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserPlusInfo {
    private String address;
    private String addressDetail;
    private String specialStatus;
    private String incomeLevel;
    private String incomeAvg;
    private String employmentState;
    private String schoolRecords;
    private String school;
    private String mainMajor;
    private String subMajor;
    private String semester;
    private String lastSemesterScore;

}
