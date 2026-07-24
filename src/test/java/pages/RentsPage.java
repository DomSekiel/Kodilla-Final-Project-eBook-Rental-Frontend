package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RentsPage extends BasePage {

    private final By addRentButton = By.id("add-rent-button");
    private final By customerNameInput = By.name("customer-name");
    private final By submitButton = By.cssSelector("button[name='submit-button']");
    private final By editButton = By.cssSelector(".edit-btn");
    private final By removeButton = By.cssSelector(".remove-btn");
    private final By rents = By.cssSelector("li.rents-list__rent");
    private final By customerNames = By.cssSelector(".rents-list__rent__customer-name");
    private final By validationError = By.cssSelector(".alert--error");

    public RentsPage(WebDriver driver) {

        super(driver);
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