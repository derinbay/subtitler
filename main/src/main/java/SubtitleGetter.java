import com.altyazi.models.users.Visitor;
import pages.HomePage;
import pages.MoviePage;
import pages.SearchResultPage;

/**
 * Created by taylanderinbay on 18.04.15.
 */
class SubtitleGetter {

    public static void main(String args[]) throws Exception {
        System.setProperty("selenium_env", "prod");
        System.setProperty("browser", "firefox");

        String keyword = "Matrix";

        HomePage homePage = new HomePage();
        Visitor visitor = Visitor.aVisitor().open(homePage);
        SearchResultPage searchResultPage = homePage.searchMovie(keyword);
        MoviePage moviePage = searchResultPage.goToMovie(keyword);


    }
}
