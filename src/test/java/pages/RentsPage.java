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
    private final By loadingOverlay = By.cssSelector(".fog, .lds-ripple");

    public RentsPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getIntProperty("timeout.seconds")));
    }

    private void click(By locator) {

        waitForLoaderToDisappear();

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );

        waitForLoaderToDisappear();

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    element
            );
        }

        waitForLoaderToDisappear();
    }

    private void type(By locator, String text) {

        waitForLoaderToDisappear();

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        element.clear();
        element.sendKeys(text);
    }

    public void waitForLoaderToDisappear() {

        try {

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));

        } catch (TimeoutException ignored) {
        }
    }

    public int getRentsCount() {

        waitForLoaderToDisappear();

        return driver.findElements(rents).size();
    }

    public void addRent(String customerName) {

        waitForLoaderToDisappear();

        click(addRentButton);

        type(customerNameInput, customerName);

        click(submitButton);

        waitForLoaderToDisappear();
    }

    public void openAddRentForm() {

        waitForLoaderToDisappear();

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

        waitForLoaderToDisappear();

        return driver.findElements(customerNames)
                .get(0)
                .getText();
    }

    public void editFirstRent(String newCustomerName) {

        waitForLoaderToDisappear();

                click(editButton);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(customerNameInput)
        ).clear();

        driver.findElement(customerNameInput)
                .sendKeys(newCustomerName);

                click(submitButton);

        waitForLoaderToDisappear();
    }

    public void removeFirstRent() {

        waitForLoaderToDisappear();

                click(removeButton);

        waitForLoaderToDisappear();
    }
}