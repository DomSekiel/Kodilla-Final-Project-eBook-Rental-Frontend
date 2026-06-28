package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class TitlesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final int SHORT_TIMEOUT =
            ConfigReader.getIntProperty("short.timeout.seconds");

    private final By addNewButton = By.id("add-title-button");
    private final By titleInput = By.name("title");
    private final By authorInput = By.name("author");
    private final By yearInput = By.name("year");
    private final By submitButton = By.name("submit-button");
    private final By errorMessage = By.className("alert__content");
    private final By loadingOverlay = By.cssSelector(".fog, .lds-ripple");

    public TitlesPage(WebDriver driver) {

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

    private void waitForLoaderToDisappear() {

        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(SHORT_TIMEOUT)
            ).until(
                    ExpectedConditions.invisibilityOfElementLocated(loadingOverlay)
            );

        } catch (TimeoutException ignored) {
        }
    }

    public boolean isTitlesPageDisplayed() {

        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(SHORT_TIMEOUT)
            ).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            addNewButton
                    )
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    public void clickAddNewButton() {

        click(addNewButton);
    }

    public void addTitle(String title, String author, String year) {

        clickAddNewButton();

        type(titleInput, title);
        type(authorInput, author);
        type(yearInput, year);

        click(submitButton);

        waitForLoaderToDisappear();
    }

    public void submitEmptyTitleForm() {

        clickAddNewButton();

        click(submitButton);
    }

    public String getErrorMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        errorMessage
                )
        ).getText();
    }

    public boolean isTitleVisible(String title) {

        By titleElement =
                By.xpath("//li[contains(.,'" + title + "')]");

        try {

            new WebDriverWait(driver, Duration.ofSeconds(SHORT_TIMEOUT)
            ).until(
                    ExpectedConditions.visibilityOfElementLocated(titleElement)
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    public void editTitle(String oldTitle, String newTitle) {

        By editButton = By.xpath("//li[contains(.,'" + oldTitle + "')]//button[contains(@class,'edit-btn')]");

        click(editButton);

        WebElement titleField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                titleInput
                        )
                );

        titleField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        titleField.sendKeys(Keys.DELETE);
        titleField.sendKeys(newTitle);

        click(submitButton);

        waitForLoaderToDisappear();
    }

    public void removeTitle(String title) {

        By removeButton = By.xpath("//li[contains(.,'" + title + "')]//button[contains(@class,'remove-btn')]");

        click(removeButton);

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath(
                                "//li[contains(.,'" + title + "')]"
                        )
                )
        );
    }

    public void clickRemoveTitle(String title) {

        By removeButton = By.xpath("//li[contains(.,'" + title + "')]//button[contains(@class,'remove-btn')]");

        click(removeButton);
    }

    public boolean isTitleFormDisplayed() {

        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(SHORT_TIMEOUT)
            ).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            titleInput
                    )
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    public void clickShowCopiesForFirstTitle() {

        By firstLink =
                By.cssSelector("a[href*='/items/']");

        click(firstLink);

        wait.until(
                ExpectedConditions.urlContains("/items/")
        );
    }

    public void openItemsForTitle(String title) {

        By titleLink =
                By.xpath(
                        "//li[contains(.,'" + title + "')]//a[contains(@href,'/items/')]"
                );

        click (titleLink);

        wait.until(
                ExpectedConditions.urlContains("/items/")
        );
    }

    public boolean isItemsPageDisplayed() {

        return driver.getCurrentUrl()
                .contains("/items/");
    }
}