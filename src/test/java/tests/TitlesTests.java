package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.TitlesPage;
import utils.ConfigReader;
import utils.TestDataGenerator;

import static org.assertj.core.api.Assertions.assertThat;

public class TitlesTests extends BaseTest {

    @BeforeEach
    void login() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("valid.login"),
                ConfigReader.getProperty("valid.password")
        );
    }

    @Test
    void shouldDisplayTitlesPage() {        // #6 Wyświetlenie listy tytułów

        TitlesPage titlesPage = new TitlesPage(driver);

        assertThat(
                titlesPage.isTitlesPageDisplayed()
        ).isTrue();
    }

    @Test
    void shouldAddNewTitle() {      // #7 Dodanie nowego tytułu

        TitlesPage titlesPage = new TitlesPage(driver);

        String title = TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                title,
                TestDataGenerator.generateAuthor(),
                "2025"
        );

        assertThat(
                titlesPage.isTitleVisible(title)
        ).isTrue();
    }

    @Test
    void shouldNotAddEmptyTitle() {        // #8 Walidacja pustego formularza

        TitlesPage titlesPage = new TitlesPage(driver);

        titlesPage.submitEmptyTitleForm();

        assertThat(
                titlesPage.getErrorMessage()
        ).contains("shouldn't be empty");
    }

    @Test
    void shouldEditTitle() {        // #9 Edycja tytułu

        TitlesPage titlesPage = new TitlesPage(driver);

        String oldTitle =
                TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                oldTitle,
                TestDataGenerator.generateAuthor(),
                "2025"
        );

        String newTitle = oldTitle + "_edited";

        titlesPage.editTitle(
                oldTitle,
                newTitle
        );

        assertThat(
                titlesPage.isTitleVisible(newTitle)
        ).isTrue();
    }

    @Test
    void shouldRemoveTitle() {      // #10 Usunięcie tytułu

        TitlesPage titlesPage = new TitlesPage(driver);

        String title = TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                title,
                TestDataGenerator.generateAuthor(),
                "2025"
        );

        titlesPage.removeTitle(title);

        assertThat(
                titlesPage.isTitleVisible(title)
        ).isFalse();
    }

    @Test
    void shouldNavigateToItemsPage() {      // #11 Przejście do listy egzemplarzy

        TitlesPage titlesPage = new TitlesPage(driver);

        titlesPage.clickShowCopiesForFirstTitle();

        System.out.println(driver.getCurrentUrl());

        assertThat(
                titlesPage.isItemsPageDisplayed()
        ).isTrue();
    }

    @Test
    void shouldKeepEmptyFormOpened() {       // #12 Blokada zapisu pustego formularza

        TitlesPage titlesPage = new TitlesPage(driver);

        titlesPage.clickAddNewButton();

        titlesPage.submitEmptyTitleForm();

        assertThat(
                titlesPage.isTitleFormDisplayed()
        ).isTrue();
    }

    @Test
    void shouldKeepTitleAfterRelogin() {        // #13 Zachowanie tytułu po ponownym zalogowaniu

        TitlesPage titlesPage = new TitlesPage(driver);

        String title = TestDataGenerator.generateTitle();

        titlesPage.addTitle(
                title,
                TestDataGenerator.generateAuthor(),
                "2025"
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
    void shouldRedirectToLoginAfterRefresh() {      // #13 Przekierowanie do ekranu logowania po odświeżeniu strony

        driver.navigate().refresh();

        assertThat(
                driver.getCurrentUrl()
        ).contains("/login");
    }

    @Test
    void shouldNotRemoveTitleWithCopies() {     // #14 Usunięcie tytułu posiadającego egzemplarze

        TitlesPage titlesPage = new TitlesPage(driver);

        titlesPage.clickRemoveTitle(
                "The Fellowship of the Ring"
        );

        assertThat(
                titlesPage.getErrorMessage()
        ).contains(
                "You can't remove titles with copies"
        );

        assertThat(
                titlesPage.isTitleVisible(
                        "The Fellowship of the Ring"
                )
        ).isTrue();
    }
}