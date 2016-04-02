package pages;

import com.altyazi.models.DataTable;
import com.altyazi.models.Movie;
import com.altyazi.models.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by taylanderinbay on 2.04.2016.
 */
public class SearchResultDataTable extends DataTable {

    public SearchResultDataTable(WebComponent parent) {
        this.xpath = "//*[@id='Table_01']/following-sibling::table//tr[@bgcolor='white']";
        this.browser = parent.browser();
        this.parent = parent;
    }

    public Movie findMovie(String movie) {
        List<WebElement> rows = getRows();
        Movie foundedMovie = new Movie();
        for (WebElement row : rows) {
            if (row.getText().contains(movie)) {
                return getMovieFrom(row);
            }
        }
        return foundedMovie;
    }

    private Movie getMovieFrom(WebElement row) {
        Movie movie = new Movie();
        movie.setUrl(row.findElement(By.tagName("a")).getAttribute("href"));
        movie.setId(movie.getId());
        movie.setCategory(movie.getCategory());
        movie.setImageUrl(row.findElement(By.tagName("img")).getAttribute("src"));
        movie.setTitle(movie.getTitle());
        movie.setContainer(row);

        return movie;
    }
}
