package com.example.demo.src.tmp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserWelfareReq {
    private List<String> studentCategory;
    private List<String> empolyCategory;
    private List<String> foundationCategory;
    private List<String> residentCategory;
    private List<String> lifeCategory;  //23
    private List<String> medicalCategory;
}
