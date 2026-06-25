package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ItemsPage;
import pages.LoginPage;
import pages.RentsPage;
import pages.TitlesPage;
import utils.ConfigReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RentsTests extends BaseTest {

    private RentsPage rentsPage;

    @BeforeEach
    void loginAndOpenRentsPage() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("valid.login"),
                ConfigReader.getProperty("valid.password")
        );

        TitlesPage titlesPage =
                new TitlesPage(driver);

        titlesPage.clickShowCopiesForFirstTitle();

        ItemsPage itemsPage = new ItemsPage(driver);

        itemsPage.clickShowHistoryForFirstItem();

        rentsPage =
                new RentsPage(driver);
    }

    @Test
    void shouldAddRent() {      // TC #19 Dodanie wypożyczenia

        int before =
                rentsPage.getRentsCount();

        rentsPage.addRent("George Clooney");

        assertThat(
                rentsPage.getRentsCount()
        ).isGreaterThan(before);
    }

    @Test
    void shouldValidateEmptyRentForm() {        // TC #20 Walidacja pustych danych

        rentsPage.openAddRentForm();

        rentsPage.submitEmptyForm();

        assertThat(
                rentsPage.isValidationErrorDisplayed()
        ).isTrue();
    }

    @Test
    void shouldEditRent() {     // TC #21 Edycja wypożyczenia

        String before =
                rentsPage.getFirstCustomerName();

        rentsPage.editFirstRent(
                "Brad Pitt"
        );

        String after =
                rentsPage.getFirstCustomerName();

        assertThat(after)
                .isNotEqualTo(before);
    }

    @Test
    void shouldRemoveRent() {       // TC #23 Usunięcie wypożyczenia

            rentsPage.addRent("George Clooney");

            int before = rentsPage.getRentsCount();

            rentsPage.removeFirstRent();

            assertThat(rentsPage.getRentsCount())
                    .isEqualTo(before - 1);
        }
}