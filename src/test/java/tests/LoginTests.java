package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import utils.ConfigReader;
import utils.TestDataGenerator;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests extends BaseTest {

    @Test
    void shouldLoginWithValidCredentials() {    // TC #1 Logowanie poprawne

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("valid.login"),
                ConfigReader.getProperty("valid.password")
        );

        assertThat(loginPage.isTitlesPageDisplayed())
                .isTrue();
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {     // TC #2 Logowanie błędne hasło

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("valid.login"),
                ConfigReader.getProperty("invalid.password")
        );

        assertThat(loginPage.getErrorMessage())
                .contains("Login failed");
    }

    @Test
    void shouldNotLoginWithEmptyCredentials() {     // TC #3 Logowanie puste pola

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("", "");

        assertThat(
                loginPage.getErrorMessage()
        ).isNotEmpty();
    }

    @Test
    void shouldNotLoginAfterMultipleInvalidAttempts() {     // TC #4 Wielokrotne błędne logowanie

        LoginPage loginPage = new LoginPage(driver);

        for (int i = 0; i < 3; i++) {

            loginPage.login(
                    ConfigReader.getProperty("valid.login"),
                    ConfigReader.getProperty("invalid.password")
            );

            assertThat(
                    loginPage.getErrorMessage()
            ).contains("Login failed");
        }
    }

    @Test
    void shouldRegisterNewUser() {      // TC #5 Rejestracja użytkownika

        LoginPage loginPage = new LoginPage(driver);

        String login = TestDataGenerator.generateLogin() + "@bayern.de";

        String password = "bayern123";

        loginPage.register(
                login,
                password
        );

        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getPageSource());

        driver.navigate().to(ConfigReader.getProperty("base.url"));

        loginPage.login(
                login,
                password
        );

        assertThat(
                loginPage.isTitlesPageDisplayed()
        ).isTrue();
    }
}