package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class LoginPage extends BasePage {

    private final By loginInput = By.id("login");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-btn");
    private final By registerButton = By.id("register-btn");
    private final By errorMessage = By.className("alert__content");
    private final By repeatPasswordInput = By.id("password-repeat");
    private final By titlesHeader = By.xpath("//h2[contains(text(),'Titles catalog')]");

    public LoginPage(WebDriver driver) {

        super(driver);
    }

    public void login(String login, String password) {

        clearLoginForm();

        type(loginInput, login);
        type(passwordInput, password);

        click(loginButton);

        waitForLoaderToDisappear();
    }

    public void register(String login, String password) {

        click(registerButton);

        wait.until(
                ExpectedConditions.urlContains("/register"));

        type(loginInput, login);
        type(passwordInput, password);
        type(repeatPasswordInput, password);

        click(registerButton);

        waitForLoaderToDisappear();
    }

    public void clearLoginForm() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginInput)
        ).clear();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordInput)
        ).clear();
    }

    public String getErrorMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }

    public boolean isTitlesPageDisplayed() {

        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(
                            ConfigReader.getIntProperty(
                                    "short.timeout.seconds"
                            )
                    )
            ).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            titlesHeader
                    )
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }
}