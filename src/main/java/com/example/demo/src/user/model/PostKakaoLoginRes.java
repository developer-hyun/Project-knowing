package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostKakaoLoginRes {
   // private int id;
    private String status;
    private String jwt;
}
