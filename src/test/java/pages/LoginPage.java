package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginInput = By.id("login");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-btn");
    private final By registerButton = By.id("register-btn");
    private final By errorMessage = By.className("alert__content");
    private final By repeatPasswordInput = By.id("password-repeat");
    private final By loadingOverlay = By.className("fog");
    private final By titlesHeader = By.xpath("//h2[contains(text(),'Titles catalog')]");

    public LoginPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getIntProperty("timeout.seconds")));
    }

    private void click(By locator) {

        wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        ).click();
    }

    private void type(By locator, String text) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).sendKeys(text);
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
    }

    public void clearLoginForm() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginInput)
        ).clear();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordInput)
        ).clear();
    }

    public void waitForLoaderToDisappear() {

        try {

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));

        } catch (TimeoutException ignored) {
        }
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