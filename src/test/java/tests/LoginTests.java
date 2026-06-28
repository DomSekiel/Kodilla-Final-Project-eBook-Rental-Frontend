package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import utils.ConfigReader;
import utils.TestDataGenerator;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    void initPage() {
        loginPage = new LoginPage(driver);
    }

    @Test
    void shouldLoginWithValidCredentials() { // TC #1 Logowanie poprawne

        loginPage.login(
                ConfigReader.getProperty("valid.login"),
                ConfigReader.getProperty("valid.password")
        );

        assertThat(loginPage.isTitlesPageDisplayed())
                .isTrue();
    }

    @Test
    void shouldNotLoginWithInvalidPassword() { // TC #2 Logowanie błędne hasło

        loginPage.login(
                ConfigReader.getProperty("valid.login"),
                ConfigReader.getProperty("invalid.password")
        );

        assertThat(loginPage.isTitlesPageDisplayed())
                .isFalse();

        assertThat(loginPage.getErrorMessage())
                .contains("Login failed");
    }

    @Test
    void shouldNotLoginWithEmptyCredentials() { // TC #3 Logowanie puste pola

        loginPage.login("","");
                //ConfigReader.getProperty("empty.login"), // do wyjaśnienia z Mentorem
                //ConfigReader.getProperty("empty.password") // do wyjaśnienia z Mentorem
        //);

        assertThat(loginPage.isTitlesPageDisplayed())
                .isFalse();

        assertThat(loginPage.getErrorMessage())
                .isNotEmpty();
    }

    @Test
    void shouldNotLoginAfterMultipleInvalidAttempts() { // TC #4 Wielokrotne błędne logowanie

        for (int i = 0; i < 3; i++) {

            loginPage.login(
                    ConfigReader.getProperty("valid.login"),
                    ConfigReader.getProperty("invalid.password")
            );

            assertThat(loginPage.getErrorMessage())
                    .contains("Login failed");
        }

        assertThat(loginPage.isTitlesPageDisplayed())
                .isFalse();
    }

    @Test
    void shouldRegisterNewUser() { // TC #5 Rejestracja użytkownika

        String login =
                TestDataGenerator.generateLogin() + "@bayern.de";

        String password =
                ConfigReader.getProperty("valid.password");

        loginPage.register(
                login,
                password
        );

        driver.navigate().to(
                ConfigReader.getProperty("base.url")
        );

        loginPage = new LoginPage(driver);

        loginPage.login(
                login,
                password
        );

        assertThat(loginPage.isTitlesPageDisplayed())
                .isTrue();
    }
}