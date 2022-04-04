import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CreditForAnyPurpose;
import pages.Credits;
import pages.HomePage;
import pages.Individuals;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class RshbTests {

    HomePage homePage = new HomePage();
    Individuals individuals = new Individuals();
    Credits credits = new Credits();
    CreditForAnyPurpose creditForAnyPurpose = new CreditForAnyPurpose();

    // variables
    private int
            loanAmount = 2010000;

    @BeforeAll
    static void beforeAll() {
        baseUrl = "https://rshb.ru";
        Configuration.browserPosition = ("0x0");
        Configuration.browserSize = "1920x1080";
    }

    @AfterAll
    static void afterAll() {
        closeWebDriver();
    }

    @Test
    @DisplayName("CreditCalculationTest")
    void creditCalculationTest() {

        // data entry
        step("- Зайти на сайт rshb.ru", () -> {
            open(baseUrl);
        });

        step("- Кликнуть «Частным лицам»", () -> {
            homePage.clickIndividuals();
        });

        step("- Кликнуть «Кредиты»", () -> {
            individuals.clickCredits();
        });

        step("- Кликнуть «Кредит на любые цели»", () -> {
            credits.clickCreditForAnyPurpose();
        });

        step("- Закрыть окно cookies", () -> {
            creditForAnyPurpose.closeCookiesWindow();
        });

        step("- тип платежа «Аннуитентный»", () -> {
            creditForAnyPurpose.chooseAnnuityPayment();
        });

        step("- выбор суммы кредита", () -> {
            creditForAnyPurpose.choosingLoanAmount(loanAmount);
        });


        sleep(5000);
        // checking the correctness of the entered value


    }

}
