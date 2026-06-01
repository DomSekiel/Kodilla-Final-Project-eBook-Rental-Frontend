package com.ebook.smoke;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class LoginSmokeTest extends BaseTest {

    @Test
    void shouldOpenLoginPage() {

       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));

        driver.findElement(By.id("login"))
                .sendKeys(ConfigReader.getProperty("valid.login"));

        driver.findElement(By.id("password"))
                .sendKeys(ConfigReader.getProperty("valid.password"));

        driver.findElement(By.id("login-btn"))
                .click();
    }
}