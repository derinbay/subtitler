package pages;

import com.altyazi.Config;
import com.altyazi.models.Movie;
import com.altyazi.models.Page;

/**
 * Created by taylanderinbay on 2.04.2016.
 */
public class MoviePage extends Page {

    public static final String PAGE_URL = Config.altyaziOrgUrl + "sub/";

    public MoviePage(Movie movie) {
        this.url = PAGE_URL + movie.getCategory() + "/" + movie.getId() + "/" + movie.getTitle() + ".html";
    }
}
