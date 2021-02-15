package com.example.hydss.selenium;

import com.google.gson.Gson;
import okhttp3.Cookie;
import org.junit.Test;
import utils.CommonUtil;
import utils.CookieUtil;

import java.io.*;
import java.util.ArrayList;

public class TestJSON {
    String file="";
    @Test
    public void t1(){
        ArrayList<String> s = new ArrayList<>();
        for(int i=0;i<30;i++){
            s.add("obj"+i);
        }
    }
    @Test
    public void t2() throws IOException {
        CookieUtil.saveObjectByObjectOutput(1,new File("e:/1/2/3.txt"));
    }

}
