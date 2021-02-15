package com.example.hydss.controller;

import com.example.hydss.config.HydssConst;
import com.example.hydss.pojo.Acc;
import com.example.hydss.selenium.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import utils.CookieUtil;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AccountController {
    @Autowired
    WebService webService;

    @RequestMapping(value = "/account")
    public String account(HttpSession session,Model model) throws IOException {
        Map<Integer, String> map=new HashMap();
        map.put(0,"未登录或未知状态");
        map.put(1,"已登录");
        model.addAttribute(HydssConst.ACCSTATEMAP,map);
        return "account.html";
    }

    @RequestMapping("/testlogin")
    public String testLogin(HttpSession session) throws InterruptedException, IOException, ClassNotFoundException {
        Map<String, ArrayList<Acc>> map = (Map<String, ArrayList<Acc>>) session.getAttribute(HydssConst.ACCMAP);
        for(String key:map.keySet()){
            ArrayList<Acc> accs = map.get(key);
            for(Acc acc:accs){
                Boolean isLogin = webService.testLogin(acc);
                if(isLogin){
                    acc.setState(1);
                }else {
                    acc.setState(0);
                }
            }
        }
        session.setAttribute(HydssConst.ACCMAP,map);
        return "forward:/account";
    }



    @RequestMapping(value = "/a")
    public String a(Model m, HttpSession session)   {
        return "a.html";
    }

    @RequestMapping(value = "/b")
    public String ab(HttpSession session)   {
        return "a";
    }
}
