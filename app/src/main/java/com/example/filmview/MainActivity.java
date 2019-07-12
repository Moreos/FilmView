package com.example.filmview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnItemClick {
    public static final String myURL = "http://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json";

    private static final String TAG_FID = "id";
    private static final String TAG_YEAR = "year";
    private static final String TAG_RATING = "rating";
    private static final String TAG_RUNAME = "localized_name";
    private static final String TAG_ENNAME = "name";
    private static final String TAG_IMAGEURL = "image_url";
    private static final String TAG_DESCRIPTION = "description";

    JSONArray filmsArray = null;

    List<Film> films = new ArrayList<>();
    List<Object> filmsItemsYears = new ArrayList<>();

    private WeakReference<MainActivity> activityWeakReference;


    public static String doGet(String url)
            throws Exception {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();

//      print result
        Log.d(TAG, "Response string: " + response.toString());


        return response.toString();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityWeakReference = new WeakReference<>(MainActivity.this);
        mSwipeRefreshLayout = findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        new showAllFilms().execute();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                mSwipeRefreshLayout.setRefreshing(false);
                filmsItemsYears.clear();
                films.clear();
                new showAllFilms().execute();
            }
        }, 1000);
    }

    @SuppressLint("StaticFieldLeak")
    class showAllFilms extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String s = "";
            try {
                s = doGet(myURL);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(final String result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (result.equals("")) {
                        Toast.makeText(getApplicationContext(), "Ошибка с подключением. \n Повторите попытку позже или проверьте подключение.", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            filmsArray = jsonObject.getJSONArray("films");
                            for (int i = 0; i < filmsArray.length(); i++) {
                                JSONObject filmItem = filmsArray.getJSONObject(i);
/*
                                String filmId = filmItem.get(TAG_FID).toString();
                                String filmYear, filmRating, filmRuName, filmEnName, filmImageURL, filmDescription;

                                if (filmItem.get(TAG_YEAR) == null) filmYear = "0";
                                else filmYear = filmItem.get(TAG_YEAR).toString();
                                if (filmItem.get(TAG_RATING) == null) filmRating = "0";
                                else filmRating = filmItem.get(TAG_RATING).toString();
                                if (filmItem.get(TAG_RUNAME) == null) filmRuName = "-";
                                else filmRuName = filmItem.get(TAG_RUNAME).toString();
                                if (filmItem.get(TAG_ENNAME) == null) filmEnName = "-";
                                else filmEnName = filmItem.get(TAG_ENNAME).toString();
                                if (filmItem.get(TAG_IMAGEURL) == null) filmImageURL = "";
                                else filmImageURL = filmItem.get(TAG_IMAGEURL).toString();
                                if (filmItem.get(TAG_DESCRIPTION) == null) filmDescription = "Отсутствует";
                                else filmDescription = filmItem.get(TAG_DESCRIPTION).toString();

                                films.add(new Film(filmId,
                                        filmYear,
                                        filmRating,
                                        filmRuName,
                                        filmEnName,
                                        filmImageURL,
                                        filmDescription,
                                        String.valueOf(i)));
                                filmsItems.add(new FilmItem(filmId,
                                        filmYear,
                                        filmRating,
                                        filmRuName,
                                        filmEnName,
                                        i, 1));
*/

                                if (!filmItem.get(TAG_FID).toString().equals("512280") && !filmItem.get(TAG_FID).toString().equals("841340")) {
                                    films.add(new Film(filmItem.get(TAG_FID).toString(),
                                            filmItem.get(TAG_YEAR).toString(),
                                            filmItem.get(TAG_RATING).toString(),
                                            filmItem.get(TAG_RUNAME).toString(),
                                            filmItem.get(TAG_ENNAME).toString(),
                                            filmItem.get(TAG_IMAGEURL).toString(),
                                            filmItem.get(TAG_DESCRIPTION).toString()));
                                } else if (filmItem.get(TAG_FID).toString().equals("512280")) {
                                    films.add(new Film(filmItem.get(TAG_FID).toString(),
                                            filmItem.get(TAG_YEAR).toString(),
                                            "Не выставлена",
                                            filmItem.get(TAG_RUNAME).toString(),
                                            filmItem.get(TAG_ENNAME).toString(),
                                            "Отсутствует",
                                            "Отсутствует"));
                                } else if (filmItem.get(TAG_FID).toString().equals("841340")) {
                                    films.add(new Film(filmItem.get(TAG_FID).toString(),
                                            filmItem.get(TAG_YEAR).toString(),
                                            "0",
                                            filmItem.get(TAG_RUNAME).toString(),
                                            filmItem.get(TAG_ENNAME).toString(),
                                            filmItem.get(TAG_IMAGEURL).toString(),
                                            filmItem.get(TAG_DESCRIPTION).toString()));
                                }
                            }
                            Collections.sort(films, Film.yearRatinCompare);
                            filmsItemsYears.addAll(films);
                            int k = 0;
                            for (int i = 0; i < films.size(); i++) {
                                String year = films.get(i).getYear();
                                if (i == 0) {
                                    filmsItemsYears.add(i, year);
                                    k++;
                                } else {
                                    if (!films.get(i).getYear().equals(films.get(i - 1).getYear())) {
                                        filmsItemsYears.add(i + k, year);
                                        k++;
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (activityWeakReference != null) {
                        //setInitialData();
                        RecyclerView recyclerView = findViewById(R.id.list);
                        // создаем адаптер
                        DataAdapter adapter = new DataAdapter(MainActivity.this, filmsItemsYears, MainActivity.this);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(Film film) {
        Intent intent = new Intent(getApplicationContext(), FilmActivity.class);

        String imageUrl = film.getImageUrl();
        String ruName = film.getRuName();
        String enName = film.getEnName();
        String year = film.getYear();
        String rating = film.getRank();
        String description = film.getDescription();

        intent.putExtra(TAG_IMAGEURL, imageUrl);
        intent.putExtra(TAG_RUNAME, ruName);
        intent.putExtra(TAG_ENNAME, enName);
        intent.putExtra(TAG_YEAR, year);
        intent.putExtra(TAG_RATING, rating);
        intent.putExtra(TAG_DESCRIPTION, description);

        startActivity(intent);
    }
}