package com.example.hydss.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Acc implements Serializable {
    String user;
    String pwd;
    int state;
    Integer shop;

    public Acc(String user, String pwd,Integer shop) {
        this.user = user;
        this.pwd = pwd;
        this.shop=shop;
    }
}
