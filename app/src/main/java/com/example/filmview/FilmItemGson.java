package com.example.filmview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilmItemGson {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("localized_name")
    @Expose
    private String localized_name;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("image_url")
    @Expose
    private String image_url;
    @SerializedName("description")
    @Expose
    private String description;

    public FilmItemGson(String id, String localized_name, String name, String year, String rating, String image_url, String description) {
        this.id = id;
        this.localized_name = localized_name;
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.image_url = image_url;
        this.description = description;
    }


    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The localized_name
     */
    public String getLocalized_name() {
        return localized_name;
    }

    /**
     *
     * @param localized_name
     * The localized_name
     */
    public void setLocalized_name(String localized_name) {
        this.localized_name = localized_name;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The year
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @param year
     * The year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     *
     * @return
     * The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The image_url
     */
    public String getImage_url() {
        return image_url;
    }

    /**
     *
     * @param image_url
     * The image_url
     */
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}