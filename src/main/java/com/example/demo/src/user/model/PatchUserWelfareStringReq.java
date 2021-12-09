package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserWelfareStringReq {
    private String studentCategory;
    private String empolyCategory;
    private String foundationCategory;
    private String residentCategory;
    private String lifeCategory;  //23
    private String covidCategory;
}
