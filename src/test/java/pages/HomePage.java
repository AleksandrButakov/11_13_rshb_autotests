package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    // locators
    private static SelenideElement
            linkIndividuals = $(".header__section-navigation").$(byText("Частным лицам"));

    public void clickIndividuals() {
        linkIndividuals.click();
    }

}
