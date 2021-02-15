package com.example.hydss;

import com.example.hydss.config.HttpClientConfig;
import com.example.hydss.config.HttpClientConfig_;
import com.example.hydss.selenium.SeleniumService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootApplication
public class HydssApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(HydssApplication.class, args);
        //RestTemplate r = run.getBean(RestTemplate.class);
        //new TestSelenium().t1(r);

    }
    void f1(ConfigurableApplicationContext run){
        RestTemplate b = run.getBean(RestTemplate.class);
        HttpClientConfig bean = run.getBean(HttpClientConfig.class);
        //HttpClientConfig_ bean1 = run.getBean(HttpClientConfig_.class);
        System.out.println(b);
        String url="https://passport.suning.com/ids/login";


        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username","111123123");
        map.add("password2","111123123");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT,"User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36");
        headers.set(HttpHeaders.CONTENT_TYPE,"application/x-www-form-urlencoded; charset=UTF-8");
        headers.set(HttpHeaders.REFERER,"https://passport.suning.com/ids/login?service=https%3A%2F%2Forder.suning.com%2Fauth%3FtargetUrl%3Dhttps%253A%252F%252Forder.suning.com%252Forder%252ForderList.do&loginTheme=b2c&multipleActive=false");
        headers.set(HttpHeaders.COOKIE,"TGC=TGTBisO1401JEbMkuoBrK2BLKH4MRV04HtSFzuYzr0u; TGCB=TGTBisO1401JEbMkuoBrK2BLKH4MRV04HtSFzuYzr0u; tradeLdc=NJGX_YG; _snmc=1; _snsr=direct%7Cdirect%7C%7C%7C; _snvd=1613276881512ELulGLSVyBa; route=e19c7e4e0cec6699637ef19d395c3f6f; authId=si5MdURkdUuZqJMQ0upFBi16lylzim2Bzq; secureToken=B4C3C598FF3E478F4292FE6B6139453B; tradeMA=194; cityCode=731; SN_CITY=160_731_1000151_9151_01_11192_2_0; districtId=11192; streetCode=7340199; totalProdQty=0; cityId=9151; _snzwt=THnSgK1779eca1918fnDo27e1; hm_guid=a38ab060-4fe4-460f-941a-8f95aebffd22; _df_ud=1df3a58d-c24e-4661-ab94-6e55bf4953e0; _device_session_id=p_5aeb7b69-b724-4091-b77e-ee4dc6b9ca66; token=b2b7421c-3dd0-49f4-ac5d-4a721047d7f9; _snma=1%7C161327677574857664%7C1613276775748%7C1613276776231%7C1613276994186%7C2%7C1; _snmp=161327698957089740; _snmb=161327677623565575%7C1613276994202%7C1613276994191%7C2; ids_qr_uuid=qr9aNScQ5L4JX6m1W2inAfAh1yymtTDtpD");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> e = b.postForEntity(url, map,String.class);
        System.out.println(e);
    }
}
