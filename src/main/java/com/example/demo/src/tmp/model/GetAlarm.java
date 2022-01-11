package com.example.demo.src.tmp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAlarm {
    private String  uid;
    private String title;
    private String subTitle;
    private String date;
    private String postUid;
    private String isRead;
}
