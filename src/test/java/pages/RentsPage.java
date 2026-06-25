package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class RentsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By addRentButton = By.id("add-rent-button");
    private final By customerNameInput = By.name("customer-name");
    private final By submitButton = By.cssSelector("button[name='submit-button']");
    private final By editButton = By.cssSelector(".edit-btn");
    private final By removeButton = By.cssSelector(".remove-btn");
    private final By rents = By.cssSelector("li.rents-list__rent");
    private final By customerNames = By.cssSelector(".rents-list__rent__customer-name");
    private final By validationError = By.cssSelector(".alert--error");
    private final By loader = By.cssSelector(".fog");

    public RentsPage(WebDriver driver) {

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

    private void waitForLoader() {

        try {
            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(loader)
            );
        } catch (TimeoutException ignored) {
        }
    }

    public int getRentsCount() {

        waitForLoader();

        return driver.findElements(rents).size();
    }

    public void addRent(String customerName) {

        waitForLoader();

        click(addRentButton);

        type(customerNameInput, customerName);

        click(submitButton);

        waitForLoader();
    }

    public void openAddRentForm() {

        waitForLoader();

        click(addRentButton);
    }

    public void submitEmptyForm() {

        click(submitButton);
    }

    public boolean isValidationErrorDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(validationError)
        ).isDisplayed();
    }

    public String getFirstCustomerName() {

        waitForLoader();

        return driver.findElements(customerNames)
                .get(0)
                .getText();
    }

    public void editFirstRent(String newCustomerName) {

        waitForLoader();

                click(editButton);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(customerNameInput)
        ).clear();

        driver.findElement(customerNameInput)
                .sendKeys(newCustomerName);

                click(submitButton);

        waitForLoader();
    }

    public void removeFirstRent() {

        waitForLoader();

                click(removeButton);

        waitForLoader();
    }
}