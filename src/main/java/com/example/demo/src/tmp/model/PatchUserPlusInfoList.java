package com.example.demo.src.tmp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserPlusInfoList {
    private String address;
    private String addressDetail;
    private List<String> specialStatus;
    private String incomeLevel;
    private String incomeAvg;
    private List<String> employmentState;
    private String schoolRecords;
    private String school;
    private String mainMajor;
    private String subMajor;
    private String semester;
    private String lastSemesterScore;
}
