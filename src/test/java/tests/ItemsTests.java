package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ItemsPage;
import pages.LoginPage;
import pages.TitlesPage;
import utils.ConfigReader;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemsTests extends BaseTest {

    @BeforeEach
    void loginAndOpenItemsPage() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("valid.login"),
                ConfigReader.getProperty("valid.password")
        );

        TitlesPage titlesPage =
                new TitlesPage(driver);

        titlesPage.clickShowCopiesForFirstTitle();

        assertThat(
                titlesPage.isItemsPageDisplayed()
        ).isTrue();
    }

    @Test
    void shouldAddItem() {      // TC #15 Dodanie egzemplarza

        ItemsPage itemsPage =
                new ItemsPage(driver);

        itemsPage.addItem();

        assertThat(
                itemsPage.isAvailableStatusDisplayed()
        ).isTrue();
    }

    @Test
    void shouldEditItem() {     // TC #16 Edycja egzemplarza

        ItemsPage itemsPage =
                new ItemsPage(driver);

        String purchaseDateBefore =
                itemsPage.getFirstItemPurchaseDate();

        itemsPage.editFirstItem();

        String purchaseDateAfter =
                itemsPage.getFirstItemPurchaseDate();

        assertThat(purchaseDateAfter)
                .isNotEqualTo(purchaseDateBefore);
    }

    @Test
    void shouldRemoveSelectedItem() {       // TC #17 Usunięcie egzemplarza

        ItemsPage itemsPage = new ItemsPage(driver);

        int itemsCountBefore = itemsPage.getItemsCount();

        String itemIdToRemove = itemsPage.getLastItemId();

        System.out.println("Item selected to remove: " + itemIdToRemove);

        itemsPage.removeItemById(itemIdToRemove);

        int itemsCountAfter = itemsPage.getItemsCount();

        assertThat(itemsPage.isItemVisibleById(itemIdToRemove))
                .isFalse();

        assertThat(itemsCountAfter)
                .isLessThan(itemsCountBefore);
    }

    @Test
    void shouldNavigateToRentsPage() {      // TC #18 Przejście do listy wypożyczeń

        ItemsPage itemsPage =
                new ItemsPage(driver);

        itemsPage.clickShowHistoryForFirstItem();

        assertThat(
                itemsPage.isRentsPageDisplayed()
        ).isTrue();
    }
}