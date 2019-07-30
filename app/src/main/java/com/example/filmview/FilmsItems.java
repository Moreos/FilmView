package com.example.filmview;

import java.util.List;

public class FilmsItems {

    List<FilmItemGson> films;

    public FilmsItems(List<FilmItemGson> films) {
        this.films = films;
    }

    public List<FilmItemGson> getFilms() {
        return films;
    }

    public void setFilms(List<FilmItemGson> films) {
        this.films = films;
    }
}
