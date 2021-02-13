package com.demo.support;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.demo.base.DriverScript;
import com.demo.util.Util;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserStackSupport extends DriverScript {
    public static String USERNAME = null;
    public static String AUTOMATE_KEY = null;
    public static String REMOTE_URL = null;
    
    public static String OS = null;
    public static String OS_VERSION = null;
    public static String BROSWER = null;
    public static String BROWSER_VERSION = null;

    public static void setUp(String browser, String name) {
        try {
            USERNAME = Util.getProperty("USERNAME");
            AUTOMATE_KEY = Util.getProperty("AUTOMATE_KEY");
            REMOTE_URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
            
            OS = Util.getProperty("OS");
            OS_VERSION = Util.getProperty("OS_VERSION");
            BROWSER = Util.getProperty("BROWSER");
            BROWSER_VERSION = Util.getProperty("BROWSER_VERSION");
        } catch (IOException e) {
            e.printStackTrace();
        }
        name = name + "_" + Util.getDateTime();
        
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os", OS);
        caps.setCapability("os_version", OS_VERSION);
        caps.setCapability("browser", BROWSER);
        caps.setCapability("browser_version", BROWSER_VERSION);
        caps.setCapability("name", name);

        if (browser.toUpperCase().equals("CHROME")) {
            WebDriverManager.chromedriver().setup();
            caps.setCapability("browser", "Chrome");
        } else if (browser.toUpperCase().equals("FIREFOX")) {
            WebDriverManager.firefoxdriver().setup();
            caps.setCapability("browser", "Firefox");
        }
        try {
            driver = new RemoteWebDriver(new URL(REMOTE_URL), caps);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.get(baseUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}