//package com.ebook.smoke;
//
//import base.BaseTest;
//import org.junit.jupiter.api.Test;
//import pages.LoginPage;
//import utils.ConfigReader;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class LoginSmokeTest extends BaseTest {
//
//    @Test
//    void shouldOpenLoginPage() {
//
//        LoginPage loginPage = new LoginPage(driver);
//
//        loginPage.login(
//                ConfigReader.getProperty("valid.login"),
//                ConfigReader.getProperty("valid.password")
//        );
//
//        assertThat(loginPage.isTitlesPageDisplayed())
//                .isTrue();
//    }
//}