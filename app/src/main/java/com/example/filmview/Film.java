package com.example.filmview;

import java.util.Comparator;

public class Film {
    private String fid;
    private String year;
    private String rank;
    private String ruName;
    private String enName;
    private String imageUrl;
    private String description;

    static final Comparator<Film> yearRatinCompare =
            new Comparator<Film>() {
                @Override
                public int compare(Film o1, Film o2) {
                    // сравним фамилии
                    int result = o1.getYear().compareTo(o2.getYear());
                    // если фамилии не одинаковы - вернем результат сравнения
                    if (result != 0)
                        return result;
                    // для одинаковых фамилий, результат сравнения - сравнение имен
                    return o2.getRank().compareTo(o1.getRank());
                }
            };

    Film(String fid, String year, String rank, String ruName, String enName, String imageUrl, String description) {
        this.fid = fid;
        this.year = year;
        this.rank = rank;
        this.ruName = ruName;
        this.enName = enName;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
