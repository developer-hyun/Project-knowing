package com.example.demo.src.main.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.SpringVersion;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class GetMainViewRes {
    private List<GetStudent> studentCategory;
    private List<GetStudent> employCategory;
    private List<GetStudent> foundationCategory;
    private List<GetStudent> residentCategory;
    private List<GetStudent> lifeCategory;
    private List<GetStudent> covidCategory;
    private GetTotalCategory TotalCategory;

}
