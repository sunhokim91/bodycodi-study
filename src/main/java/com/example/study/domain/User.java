package com.example.study.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String name;
    private String phone;
    private UserLevel userLevel;
}
