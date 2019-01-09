package com.cherry.jeeves.utils.browser;

import com.thoughtworks.selenium.BrowserConfigurationOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;


import java.io.File;

public class Mozila {
    public static void main(String[] args) {
      /*  System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        System.setProperty("webdriver.gecko.driver","D:\\driver\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile(new File("C:\\Users\\18431\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\efzu2oem.default"));
        options.setProfile(profile);
        WebDriver driver = new FirefoxDriver();
        driver.get("www.baidu.com");*/
        WebDriver driver = new FirefoxDriver();
        driver.get("www.baidu.com");
        String title = driver.getTitle();
        System.out.printf(title);

        driver.close();

    }
}
