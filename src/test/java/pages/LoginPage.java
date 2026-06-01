package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    private final By loginInput = By.id("login");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-btn");
    private final By registerButton = By.id("register-btn");
    private final By errorMessage = By.className("alert__content");
    private final By repeatPasswordInput = By.id("password-repeat");
    private final By loadingOverlay = By.className("fog");
    private final By titlesHeader = By.xpath("//h2[contains(text(),'Titles catalog')]");


    public void login(String login, String password) {

        clearLoginForm();

        wait.until(ExpectedConditions.visibilityOfElementLocated(loginInput))
                .sendKeys(login);

        driver.findElement(passwordInput)
                .sendKeys(password);

        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();

        waitForLoaderToDisappear();
    }

    public void register(String login, String password) {

        wait.until(
                ExpectedConditions.elementToBeClickable(registerButton)
        ).click();

        wait.until(
                ExpectedConditions.urlContains("/register")
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(loginInput)
        ).sendKeys(login);

        wait.until(
                ExpectedConditions.elementToBeClickable(passwordInput)
        ).sendKeys(password);

        wait.until(
                ExpectedConditions.elementToBeClickable(repeatPasswordInput)
        ).sendKeys(password);

        wait.until(
                ExpectedConditions.elementToBeClickable(registerButton)
        ).click();
    }

    public void clearLoginForm() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginInput)
        ).clear();

        driver.findElement(passwordInput)
                .clear();
    }

    public void waitForLoaderToDisappear() {

        try {

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(loadingOverlay)
            );

        } catch (Exception ignored) {

        }
    }

    public String getErrorMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }

    public boolean isTitlesPageDisplayed() {

        try {

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(titlesHeader)
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }
}