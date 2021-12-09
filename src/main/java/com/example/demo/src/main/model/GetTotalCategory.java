package com.example.demo.src.main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetTotalCategory {
    private List<GetStudent> studentCategory;
    private List<GetStudent> employCategory;
    private List<GetStudent> foundationCategory;
    private List<GetStudent> residentCategory;
    private List<GetStudent> lifeCategory;
    private List<GetStudent> covidCategory;
}
