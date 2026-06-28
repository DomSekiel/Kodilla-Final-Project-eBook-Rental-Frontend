package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

public class ItemsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final int SHORT_TIMEOUT =
            ConfigReader.getIntProperty("short.timeout.seconds");

    private final By addButton = By.id("add-item-button");
    private final By purchaseDate = By.name("purchase-date");
    private final By submitButton = By.cssSelector("button[name='submit-button']");
    private final By items = By.cssSelector("li.items-list__item");
    private final By availableStatus = By.cssSelector(".items-list__item__status--available");
    private final By showHistoryButton = By.cssSelector(".show-rents-btn");
    private final By editButton = By.cssSelector(".edit-btn");
    private final By removeButton = By.cssSelector(".remove-btn");
    private final By rentsHeader = By.xpath("//h2[contains(text(),'Rents')]");
    private final By loaderOverlay = By.cssSelector(".fog, .lds-ripple");
    private final By firstItemPurchaseDate = By.cssSelector(".items-list__item:first-child .items-list__item__purchase-date");

    public ItemsPage(WebDriver driver) {

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

    private void waitForLoaderToDisappear() {
        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(SHORT_TIMEOUT)
            ).until(
                    ExpectedConditions.invisibilityOfElementLocated(loaderOverlay)
            );

        } catch (TimeoutException ignored) {
        }
    }

    public void addItem() {

        waitForLoaderToDisappear();

        click(addButton);

        click(purchaseDate);

        List<WebElement> days =
                driver.findElements(
                        By.cssSelector(".cell.day:not(.blank)")
                );

        if (!days.isEmpty()) {
            days.get(0).click();
        }

        new Actions(driver)
                .moveByOffset(10, 10)
                .click()
                .perform();

        click(submitButton);

        waitForLoaderToDisappear();
    }

    public void editFirstItem() {

        waitForLoaderToDisappear();

        click(editButton);

        WebElement dateInput =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                purchaseDate
                        )
                );

        dateInput.click();

        String currentDate =
                dateInput.getAttribute("value");

        String currentDay =
                currentDate.substring(
                        currentDate.length() - 2
                );

        List<WebElement> days =
                driver.findElements(
                        By.cssSelector(".cell.day:not(.blank)")
                );

        for (WebElement day : days) {

            if (!day.getText().equals(
                    String.valueOf(
                            Integer.parseInt(currentDay)
                    )
            )) {

                day.click();
                break;
            }
        }

        new Actions(driver)
                .moveByOffset(10, 10)
                .click()
                .perform();

        click(submitButton);

        waitForLoaderToDisappear();
    }

    public void clickShowHistoryForFirstItem() {

       waitForLoaderToDisappear();

        click(showHistoryButton);

        wait.until(
                ExpectedConditions.urlContains("/rents/")
        );
    }

    public int getItemsCount() {

        waitForLoaderToDisappear();

        return driver.findElements(items).size();
    }

    public String getFirstItemPurchaseDate() {

        waitForLoaderToDisappear();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstItemPurchaseDate
                )
        ).getText();
    }

    public boolean isAvailableStatusDisplayed() {

        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(SHORT_TIMEOUT)
            ).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            availableStatus
                    )
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    public boolean isRentsPageDisplayed() {

        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(SHORT_TIMEOUT)
            ).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            rentsHeader
                    )
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    public String getLastItemId() {

        waitForLoaderToDisappear();

        List<WebElement> allItems = driver.findElements(items);

        WebElement lastItem =
                allItems.get(allItems.size() - 1);

        return lastItem.getAttribute("id");
    }

    public void removeItemById(String itemId) {

        waitForLoaderToDisappear();

            WebElement item =
                    driver.findElement(By.id(itemId));

            WebElement button =
                    item.findElement(removeButton);

            System.out.println("Removing item id: " + itemId);

            wait.until(
                    ExpectedConditions.elementToBeClickable(button)
            ).click();

            wait.until(
                    ExpectedConditions.stalenessOf(item)
            );
    }

    public boolean isItemVisibleById(String itemId) {

        waitForLoaderToDisappear();

        return driver.findElements(By.id(itemId))
                .stream()
                .anyMatch(WebElement::isDisplayed);
    }
}