package com.ex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Teacher {
    private Integer tid;
    private String tname;
    private String phone;
    private String pwd;
}
