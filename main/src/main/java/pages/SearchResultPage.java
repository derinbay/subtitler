package pages;

import com.altyazi.Config;
import com.altyazi.models.Movie;
import com.altyazi.models.Page;
import org.openqa.selenium.By;

/**
 * Created by taylanderinbay on 2.04.2016.
 */
public class SearchResultPage extends Page {

    public static final String PAGE_URL = Config.altyaziOrgUrl + "/index.php?page=arama";

    public SearchResultPage() {
        this.url = PAGE_URL;
    }

    public SearchResultDataTable getResults() {
        return new SearchResultDataTable(this);
    }

    public MoviePage goToMovie(String keyword) {
        SearchResultDataTable searchResultDataTable = getResults();
        Movie foundedMovie = searchResultDataTable.findMovie(keyword);
        browser.clickTo(foundedMovie.getContainer().findElement(By.tagName("a")));

        MoviePage moviePage = new MoviePage(foundedMovie);
        browser.changePage(moviePage);
        return moviePage;
    }
}
