import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.CreditForAnyPurpose;
import pages.Credits;
import pages.HomePage;
import pages.Individuals;

import static com.codeborne.selenide.Configuration.baseUrl;
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

    private String
            loanPeriod = "60",
            preliminaryCalculation = "55 400 ₽";

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        baseUrl = "https://rshb.ru";
        Configuration.browserPosition = ("0x0");
        Configuration.browserSize = "1920x1080";

        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

        /* Jenkins не имеет графического интерфейса поэтому для тестирования web интерфейса необходимо
           подключить selenoid
         */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
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

        step("- Срок кредита 60 м", () -> {
            creditForAnyPurpose.choosingLoanPeriod(loanPeriod);
        });

        // checking the correctness of the entered value
        step("- проверить что значение поля 'Предварительный расчет' составляет 55 400 ₽", () -> {
            creditForAnyPurpose.checkValuePreliminaryCalculationField(preliminaryCalculation);
        });
    }

}