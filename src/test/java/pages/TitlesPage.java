package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TitlesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public TitlesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By addNewButton = By.id("add-title-button");
    private final By titleInput = By.name("title");
    private final By authorInput = By.name("author");
    private final By yearInput = By.name("year");
    private final By submitButton = By.name("submit-button");
    private final By errorMessage = By.className("alert__content");

    public boolean isTitlesPageDisplayed() {

        try {

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(addNewButton)
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    public void clickAddNewButton() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(addNewButton)
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        driver.findElement(addNewButton)
                );
    }

    public void addTitle(String title, String author, String year) {

        clickAddNewButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(titleInput))
                .sendKeys(title);

        driver.findElement(authorInput)
                .sendKeys(author);

        driver.findElement(yearInput)
                .sendKeys(year);

        driver.findElement(submitButton)
                .click();

        By titleElement = By.xpath(
                "//li[contains(.,'" + title + "')]"
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(titleElement)
        );
    }

    public void submitEmptyTitleForm() {

        clickAddNewButton();

        wait.until(
                ExpectedConditions.elementToBeClickable(submitButton)
        ).click();
    }

    public String getErrorMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }

    public boolean isTitleVisible(String title) {

        By titleElement = By.xpath(
                "//li[contains(.,'" + title + "')]"
        );

        try {

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(titleElement)
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    public void editTitle(String oldTitle, String newTitle) {

        By editButton = By.xpath(
                "//li[contains(.,'" + oldTitle + "')]//button[contains(@class,'edit-btn')]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(editButton)
        ).click();

        WebElement titleField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(titleInput)
                );

        titleField.sendKeys(Keys.CONTROL + "a");
        titleField.sendKeys(Keys.DELETE);
        titleField.sendKeys(newTitle);

        wait.until(
                ExpectedConditions.elementToBeClickable(submitButton)
        ).click();
    }

    public void removeTitle(String title) {

        By removeButton = By.xpath(
                "//li[contains(.,'" + title + "')]//button[contains(@class,'remove-btn')]"
        );

        wait.until(ExpectedConditions.elementToBeClickable(removeButton))
                .click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//li[contains(.,'" + title + "')]")
                )
        );
    }

    public void clickRemoveTitle (String title) {

        By removeButton = By.xpath("//li[contains(.,'" + title + "')]//button[contains(@class,'remove-btn')]");

        wait.until(
                ExpectedConditions.elementToBeClickable(removeButton)
        ).click();
    }

    public boolean isTitleFormDisplayed() {

        try {

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(titleInput)
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    public void clickShowCopiesForFirstTitle() {

        By firstLink =
                By.cssSelector("a[href*='/items/']");

        WebElement link =
                wait.until(
                        ExpectedConditions.elementToBeClickable(firstLink)
                );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", link);

        wait.until(
                ExpectedConditions.urlContains("/items/")
        );
    }

    public boolean isItemsPageDisplayed() {

        return driver.getCurrentUrl().contains("/items/");
    }
}