package com.example.instagram.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MAN("man"),
    WOMAN("woman");

    private String gender;
}
