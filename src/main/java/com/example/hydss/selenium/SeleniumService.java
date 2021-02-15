package com.example.hydss.selenium;

import com.example.hydss.HydssApplication;
import com.example.hydss.config.HydssConst;
import com.example.hydss.pojo.Acc;
import com.example.hydss.pojo.RequestResult;
import lombok.Data;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utils.CommonUtil;
import utils.CookieUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Data
@ConfigurationProperties(prefix = "selenium")
@PropertySource("classpath:selenium.properties")
public class SeleniumService {
    String chromedriverpath;
    String chromedriver;

    public SeleniumService() throws IOException {

    }

    public Acc loginone(Acc acc) throws InterruptedException, IOException {
        if (acc.getState() == 1) return acc;

        System.setProperty(chromedriver, chromedriverpath);// chromedriver服务地址
        WebDriver driver = new ChromeDriver();
        switch (acc.getShop()) {
            case HydssConst.JDCODE:
                acc = loginoneJD(driver, acc);
                break; //可选
            case HydssConst.SNCODE:
                acc = loginoneSN(driver, acc);
                break; //可选
            default: //可选
                //语句
        }
        return acc;
    }

    public Acc loginoneJD(WebDriver driver, Acc acc) {
        return acc;
    }

    public Acc loginoneSN(WebDriver driver, Acc acc) throws InterruptedException, IOException {
        String url = "https://passport.suning.com/ids/login";
        //页面加载超时时间设置为 5s
        //driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.get(url);

        //定位对象时给 5s 的时间, 如果5s 内还定位不到则抛出异常
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement e1 = (new WebDriverWait(driver, 5)).until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//span[contains(text(), '账户登录')]")));
        //WebElement e1 = driver.findElement(By.xpath("//span[contains(text(), '账户登录')]"));
        e1.click();

        Thread.sleep(666);
        WebElement userName = driver.findElement(By.xpath("//input[@id='userName']"));
        userName.sendKeys(acc.getUser());

        Thread.sleep(666);
        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys(acc.getPwd());

        //driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement capture = (new WebDriverWait(driver, 5)).until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//div[@id='iarUsername'][contains(@style,'block')]")));
        //可能有图形验证码//span[@class='sncaptcha-init-button-text'][contains(string(),'完成验证')]
        if (capture != null) {
            Thread.sleep(1111);
            capture.click();
            WebElement captureClear = (new WebDriverWait(driver, 30)).until(ExpectedConditions.
                    presenceOfElementLocated(By.xpath("//span[@class='sncaptcha-init-button-text'][contains(string(),'完成验证')]")));

        }

        Thread.sleep(666);
        WebElement submit = (new WebDriverWait(driver, 5)).until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//a[@id='submit']")));
        if (submit != null) {
            Thread.sleep(1111);
            submit.click();
        }

//        Thread.sleep(666);
//        WebElement phonecode =(new WebDriverWait(driver,3)).until(ExpectedConditions.
//                presenceOfElementLocated(By.xpath("//iframe[@id='security-loginProtect']")));
//        if(phonecode!=null) {
//            (new WebDriverWait(driver,30)).until(ExpectedConditions.
//                    presenceOfElementLocated(By.xpath("//span[contains(string(),'我的订单')]")));
//
//        }
        WebElement orders = (new WebDriverWait(driver, 30)).until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//span[contains(string(),'我的订单')]")));
        if (orders != null) {
            acc.setState(1);
        } else {
            acc.setState(0);
        }

        //Thread.sleep(1111);
        Set<Cookie> cookies = driver.manage().getCookies();
        List<String> cookie = CookieUtil.cookieSet2List(cookies);

        String file = CommonUtil.getProjectPath() + "/" + CommonUtil.getCookieFilename(acc);
        CookieUtil.saveObjectByObjectOutput(cookie, new File(file));

        //driver.close();
        return acc;
    }
}
