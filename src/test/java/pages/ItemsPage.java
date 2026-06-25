package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    private final By loader = By.cssSelector(".fog");
    private final By firstItemPurchaseDate = By.cssSelector(".items-list__item:first-child .items-list__item__purchase-date");

    public ItemsPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getIntProperty("timeout.seconds")));
    }

    private void click(By locator) {

        wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        ).click();
    }

    private void waitForLoader() {

        try {

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(loader)
            );

        } catch (TimeoutException ignored) {

        }
    }

    public void addItem() {

        waitForLoader();

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

        waitForLoader();
    }

    public void editFirstItem() {

        waitForLoader();

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

        waitForLoader();
    }

    public void clickShowHistoryForFirstItem() {

        waitForLoader();

        click(showHistoryButton);

        wait.until(
                ExpectedConditions.urlContains("/rents/")
        );
    }

    public int getItemsCount() {

        waitForLoader();

        return driver.findElements(items).size();
    }

    public String getFirstItemPurchaseDate() {

        waitForLoader();

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

        waitForLoader();

        List<WebElement> allItems = driver.findElements(items);

        WebElement lastItem =
                allItems.get(allItems.size() - 1);

        return lastItem.getAttribute("id");
    }

    public void removeItemById(String itemId) {

            waitForLoader();

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

        waitForLoader();

        return driver.findElements(By.id(itemId))
                .stream()
                .anyMatch(WebElement::isDisplayed);
    }
}