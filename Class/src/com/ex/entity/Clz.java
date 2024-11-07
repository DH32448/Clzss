package com.ex.entity;

import lombok.Data;

@Data
public class Clz {
    private String clzno;
    private String clzname;

    public Clz(String clzno, String clzname) {
        this.clzno = clzno;
        this.clzname = clzname;
    }
}
