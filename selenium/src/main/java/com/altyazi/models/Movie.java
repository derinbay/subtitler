package com.altyazi.models;

import com.altyazi.Config;
import org.openqa.selenium.WebElement;

/**
 * Created by taylanderinbay on 2.04.2016.
 */
public class Movie {

    private String id;
    private String url;
    private String title;
    private String category;
    private WebElement container;
    private String image;
    private String imageUrl;

    public String getId() {
        if (id == null) {
            id = url.split("sub/")[1].split("/")[1].split("/")[0];
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        if (url != null) {
            return url;
        }
        return Config.altyaziOrgUrl+ "/sub/" + category + "/" + id + "/" + title + ".html";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        if (title == null) {
            title = url.split("sub/")[1].split("/")[2].split(".html")[0];
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        if (category == null) {
            category = url.split("sub/")[1].split("/")[0];
        }
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public WebElement getContainer() {
        return container;
    }

    public void setContainer(WebElement container) {
        this.container = container;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        if (imageUrl == null) {
            imageUrl = url.split("sub/")[1].split("/")[0];
        }
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Movie() {
    }

    private Movie(WebElement container) {
        this.container = container;
    }

    public Movie(String category, String id, String title) {
        this.category = category;
        this.id = id;
        this.title = title;
    }
}
