package com.example.filmview;

import java.util.Comparator;

public class FilmItem {
    private String fid;
    private String year;
    private String rank;
    private String ruName;
    private String enName;
    private int arrayPosition;
    private int type;

    public static final Comparator<FilmItem> yearCompare =
            new Comparator<FilmItem>() {
                @Override
                public int compare(FilmItem o1, FilmItem o2) {
                    // возвращаем результат сравнения имен
                    return o1.getYear().compareTo(o2.getYear());
                }
            };

    public static final Comparator<FilmItem> yearRatinCompare =
            new Comparator<FilmItem>() {
                @Override
                public int compare(FilmItem o1, FilmItem o2) {
                    // сравним фамилии
                    int result = o1.getYear().compareTo(o2.getYear());
                    // если фамилии не одинаковы - вернем результат сравнения
                    if (result != 0)
                        return result;
                    // для одинаковых фамилий, результат сравнения - сравнение имен
                    return o2.getRank().compareTo(o1.getRank());
                }
            };

    public FilmItem(String fid, String year, String rank, String ruName, String enName, int arrayPosition, int type) {
        this.fid = fid;
        this.year = year;
        this.rank = rank;
        this.ruName = ruName;
        this.enName = enName;
        this.arrayPosition = arrayPosition;
        this.type = type;
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

    public int getArrayPosition() {
        return arrayPosition;
    }

    public void setArrayPosition(int arrayPosition) {
        this.arrayPosition = arrayPosition;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Comparator<FilmItem> getYearCompare() {
        return yearCompare;
    }

    public static Comparator<FilmItem> getYearRatinCompare() {
        return yearRatinCompare;
    }
}
