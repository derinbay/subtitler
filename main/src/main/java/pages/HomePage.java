package pages;

import com.altyazi.Config;
import com.altyazi.models.FormFiller;
import com.altyazi.models.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by taylanderinbay on 2.04.2016.
 */
public class HomePage extends Page {

    public static final String PAGE_URL = Config.altyaziOrgUrl;

    @FindBy(className = "main_site_input")
    private WebElement searchBox;

    @FindBy(xpath = "//*[@class='cxbutton' and@ value='ARA']")
    private WebElement searchButton;

    public HomePage() {
        this.url = PAGE_URL;
    }

    public SearchResultPage searchMovie(String movieName) {
        FormFiller.by().type("searchBox", movieName)
                .click("searchButton")
                .fill(this);
        SearchResultPage searchResultPage = new SearchResultPage();
        browser.changePage(searchResultPage);

        return searchResultPage;
    }
}
