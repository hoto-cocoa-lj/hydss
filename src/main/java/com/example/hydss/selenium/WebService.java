package com.example.hydss.selenium;

import com.example.hydss.config.HydssConst;
import com.example.hydss.pojo.Acc;
import com.example.hydss.pojo.RequestResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utils.CommonUtil;
import utils.CookieUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@ConfigurationProperties(prefix = "webservice")
@PropertySource("classpath:webservice.properties")
public class WebService {
    String userAgent;
    @Autowired
    RestTemplate restTemplate;

    public RequestResult get0(String url, Acc acc) throws InterruptedException{
        String file = CommonUtil.getProjectPath()+"/" + CommonUtil.getCookieFilename(acc);
        List<String> cookie = null;
        try {
            cookie = (List<String>) CookieUtil.getObjectByObjectInput(new File(file));
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookie);
        ArrayList<String> ua = new ArrayList<>();
        ua.add(userAgent);
        headers.put(HttpHeaders.USER_AGENT,ua);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        int statusCode = result.getStatusCode().value();
        String body = result.getBody();
        //System.out.println(body);
        //todo:这里返回的body可能太大，需要截断，但是testLogin的判断要用body
        RequestResult requestResult = new RequestResult(statusCode,body,acc);
        return requestResult;
    }

    public Boolean testLogin( Acc acc) throws InterruptedException, IOException, ClassNotFoundException {
        switch (acc.getShop()) {
            case  HydssConst.JDCODE:
                //get0(String url, Acc acc)
                break; //可选
            case HydssConst.SNCODE:
                RequestResult rr = get0(HydssConst.TESTLOGINSN, acc);
                if(rr!=null && rr.getStatusCode()==200 && rr.getBody().indexOf(HydssConst.TESTLOGINSNPATTERN)>0){
                    return true;
                }
                break;
            default:

        }
        return false;
    }
}
