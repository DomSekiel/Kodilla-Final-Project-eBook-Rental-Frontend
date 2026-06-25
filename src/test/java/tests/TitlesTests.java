package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ItemsPage;
import pages.TitlesPage;
import utils.ConfigReader;
import utils.TestDataGenerator;

import static org.assertj.core.api.Assertions.assertThat;

public class TitlesTests extends BaseTest {

    private TitlesPage titlesPage;

    @BeforeEach
    void login() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("valid.login"),
                ConfigReader.getProperty("valid.password")
        );

        titlesPage = new TitlesPage(driver);
    }

    @Test
    void shouldDisplayTitlesPage() {     // TC #6 Wyświetlenie listy tytułów

        assertThat(
                titlesPage.isTitlesPageDisplayed()
        ).isTrue();
    }

    @Test
    void shouldAddNewTitle() {       // TC #7 Dodanie nowego tytułu

        String title =
                TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                title,
                TestDataGenerator.generateAuthor(),
                TestDataGenerator.generateYear()
        );

        assertThat(
                titlesPage.isTitleVisible(title)
        ).isTrue();
    }

    @Test
    void shouldNotAddEmptyTitle() {      // TC #8 Walidacja pustego formularza

        titlesPage.submitEmptyTitleForm();

        assertThat(
                titlesPage.getErrorMessage()
        ).contains("shouldn't be empty");
    }

    @Test
    void shouldEditTitle() {     // TC #9 Edycja tytułu

        String oldTitle =
                TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                oldTitle,
                TestDataGenerator.generateAuthor(),
                TestDataGenerator.generateYear()
        );

        String newTitle =
                oldTitle + "_edited";

        titlesPage.editTitle(
                oldTitle,
                newTitle
        );

        assertThat(
                titlesPage.isTitleVisible(newTitle)
        ).isTrue();
    }

    @Test
    void shouldRemoveTitle() {      // TC #10 Usunięcie tytułu

        String title =
                TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                title,
                TestDataGenerator.generateAuthor(),
                TestDataGenerator.generateYear()
        );

        titlesPage.removeTitle(title);

        assertThat(
                titlesPage.isTitleVisible(title)
        ).isFalse();
    }

    @Test
    void shouldNavigateToItemsPage() {      // TC #11 Przejście do listy egzemplarzy

        String title =
                TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                title,
                TestDataGenerator.generateAuthor(),
                TestDataGenerator.generateYear()
        );

        titlesPage.openItemsForTitle(title);

        assertThat(
                titlesPage.isItemsPageDisplayed()
        ).isTrue();
    }

    @Test
    void shouldKeepEmptyFormOpened() {      // TC #12 Blokada zapisu pustego formularza

        titlesPage.submitEmptyTitleForm();

        assertThat(
                titlesPage.isTitleFormDisplayed()
        ).isTrue();
    }

    @Test
    void shouldKeepTitleAfterRelogin() {        // #13.1 Zachowanie tytułu po ponownym zalogowaniu

        TitlesPage titlesPage = new TitlesPage(driver);

        String title = TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                title,
                TestDataGenerator.generateAuthor(),
                TestDataGenerator.generateYear()
        );

        driver.navigate().refresh();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("valid.login"),
                ConfigReader.getProperty("valid.password")
        );

        TitlesPage titlesPageAfterLogin = new TitlesPage(driver);

        assertThat(
                titlesPageAfterLogin.isTitleVisible(title)
        ).isTrue();
    }

    @Test
    void shouldRedirectToLoginAfterRefresh() {      // #13.2 Przekierowanie do ekranu logowania po odświeżeniu strony

        driver.navigate().refresh();

        assertThat(
                driver.getCurrentUrl()
        ).contains("/login");
    }

    @Test
    void shouldNotRemoveTitleWithCopies() {     // TC #14 Usunięcie tytułu posiadającego egzemplarze

        String title = TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                title,
                TestDataGenerator.generateAuthor(),
                TestDataGenerator.generateYear()
        );

        titlesPage.openItemsForTitle(title);

        ItemsPage itemsPage = new ItemsPage(driver);

        itemsPage.addItem();

        driver.navigate().back();

        assertThat(titlesPage.isTitlesPageDisplayed())
                .isTrue();

        titlesPage.clickRemoveTitle(title);

        assertThat(titlesPage.getErrorMessage())
                .contains("You can't remove titles with copies");
    }
}