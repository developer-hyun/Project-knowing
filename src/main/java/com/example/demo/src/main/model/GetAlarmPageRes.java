package com.example.demo.src.main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetAlarmPageRes {

   private List<GetAlarmList> alarm;
}
