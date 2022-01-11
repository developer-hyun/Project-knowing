package com.example.demo.src.tmp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserWelfareStringReq {
    private String studentCategory;
    private String empolyCategory;
    private String foundationCategory;
    private String residentCategory;
    private String lifeCategory;  //23
    private String medicalCategory;
}
