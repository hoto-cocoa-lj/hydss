package com.example.hydss.controller;

import com.example.hydss.config.HydssConst;
import com.example.hydss.pojo.Acc;
import com.example.hydss.pojo.RequestResult;
import com.example.hydss.selenium.SeleniumService;
import com.example.hydss.selenium.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import utils.CommonUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class AccountRestController {
    @Autowired
    SeleniumService seleniumService;
    @Autowired
    WebService webService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/uploadaccount", method = {RequestMethod.POST, RequestMethod.GET})
    public void uploadaccount(@RequestParam(value = "acc") MultipartFile file, HttpSession session,
                              HttpServletResponse res) throws IOException {
        Map<String, ArrayList<Acc>> accMap = CommonUtil.convertStreamToString(file.getInputStream());
        session.setAttribute(HydssConst.ACCMAP, accMap);
        res.sendRedirect("/account");
    }

    @RequestMapping("/loginmany")
    public void loginmany(HttpSession session) throws InterruptedException, IOException {
        Map<String, ArrayList<Acc>> obj = (Map<String, ArrayList<Acc>>) session.getAttribute(HydssConst.ACCMAP);
        List<Acc> accs = (ArrayList<Acc>) obj;
        for (Acc a : accs) {
            seleniumService.loginone(a);
        }
    }

    @RequestMapping("/loginone")
    public Acc loginone(HttpSession session, @RequestParam String name,
                        @RequestParam String shopname) throws InterruptedException, IOException {
        Map<String, ArrayList<Acc>> obj = (Map<String, ArrayList<Acc>>) session.getAttribute(HydssConst.ACCMAP);
        List<Acc> accs = obj.get(shopname);
        for (Acc a : accs) {
            if (name.equals(a.getUser())) {
                return seleniumService.loginone(a);
            }
        }
        return null;
    }

    @PostMapping("/get0")
    public List<RequestResult> get0(HttpSession session, @RequestBody Map data) throws InterruptedException {
        Map<String, ArrayList<Acc>> obj = (Map<String, ArrayList<Acc>>) session.getAttribute(HydssConst.ACCMAP);
        String url = (String) data.get("url");
        String shopname = (String) data.get("shopname");
        ArrayList<String> names = (ArrayList<String>) data.get("names");
        List<Acc> accs = obj.get(shopname);
        List<RequestResult> rrs = new ArrayList();
        for (Acc acc : accs) {
            for (String name : names) {
                if (name.equals(acc.getUser())) {
                    RequestResult rr = webService.get0(url, acc);
                    rrs.add(rr);
                }
            }
        }
        return rrs;
    }


}
