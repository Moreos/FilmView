package com.example.filmview;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String myURL = "http://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json";

    private static final String TAG_FID = "id";
    private static final String TAG_YEAR = "year";
    private static final String TAG_RATING = "rating";
    private static final String TAG_RUNAME = "localized_name";
    private static final String TAG_ENNAME = "name";
    private static final String TAG_IMAGEURL = "image_url";
    private static final String TAG_DESCRIPTION = "description";

    JSONArray filmsArray = null;

    TreeMap<Integer, String> treeFilmsId = new TreeMap<>();
    TreeMap<Integer, String> treeFilmsYear = new TreeMap<>();
    TreeMap<Integer, String> treeFilmsRating = new TreeMap<>();
    TreeMap<Integer, String> treeFilmsRuName = new TreeMap<>();
    TreeMap<Integer, String> treeFilmsEnName = new TreeMap<>();

    List<Film> films = new ArrayList<>();
    List<FilmItem> filmsItems = new ArrayList<>();
    List<FilmItem> filmsItemsYears = new ArrayList<>();

    ArrayList<String> years = new ArrayList<>();

    public static String doGet(String url)
            throws Exception {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0" );
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();

//      print result
        Log.d(TAG,"Response string: " + response.toString());


        return response.toString();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream
        setContentView(R.layout.test_layout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
=======
        setContentView(R.layout.activity_main);

        /*
        Не сделанны пункты: 4, 5?, 6?, 13
        GSON Petrofit
        FCM - потом
         */

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.abs_layout);
        actionBar.setTitle(R.string.titleMain);

        activityWeakReference = new WeakReference<>(MainActivity.this);
        mSwipeRefreshLayout = findViewById(R.id.swipeContainer);
>>>>>>> Stashed changes
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
                filmsItems.clear();
                films.clear();
                new showAllFilms().execute();
                //Random random = new Random();
                //mCatTextView.setText("Котика пора кормить. Его не кормили уже "
                  //      + (1 + random.nextInt(10)) + " мин.");
            }
        }, 1000);
    }

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
                                years.add(filmItem.get(TAG_YEAR).toString());
                            }
                            Set<String> set = new HashSet<>(years);
                            years.clear();
                            years.addAll(set);
                            Collections.sort(years);
                            for (int i = 0; i < filmsArray.length(); i++) {
                                JSONObject filmItem = filmsArray.getJSONObject(i);
/*
                                String filmId = filmItem.get(TAG_FID).toString();
                                String filmYear = filmItem.get(TAG_YEAR).toString();
                                String filmRating = filmItem.get(TAG_RATING).toString();
                                String filmRuName = filmItem.get(TAG_RUNAME).toString();
                                String filmEnName = filmItem.get(TAG_ENNAME).toString();
                                String filmImageURL = filmItem.get(TAG_IMAGEURL).toString();
                                String filmDescription = filmItem.get(TAG_DESCRIPTION).toString();

                                if (filmYear.isEmpty()) filmYear = "0";
                                if (filmRating.isEmpty()) filmRating = "0";
                                if (filmRuName.isEmpty()) filmRuName = "-";
                                if (filmEnName.isEmpty()) filmEnName = "-";
                                if (filmImageURL.isEmpty()) filmImageURL = "";
                                if (filmDescription.isEmpty()) filmDescription = "Отсутствует";

                                treeFilmsId.put(i, filmItem.get(TAG_FID).toString());
                                treeFilmsYear.put(i, filmItem.get(TAG_YEAR).toString());
                                treeFilmsRating.put(i, filmItem.get(TAG_RATING).toString());
                                treeFilmsRuName.put(i, filmItem.get(TAG_RUNAME).toString());
                                treeFilmsEnName.put(i, filmItem.get(TAG_ENNAME).toString());

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
                                    treeFilmsId.put(i, filmItem.get(TAG_FID).toString());
                                    treeFilmsYear.put(i, filmItem.get(TAG_YEAR).toString());
                                    treeFilmsRating.put(i, filmItem.get(TAG_RATING).toString());
                                    treeFilmsRuName.put(i, filmItem.get(TAG_RUNAME).toString());
                                    treeFilmsEnName.put(i, filmItem.get(TAG_ENNAME).toString());

                                    films.add(new Film(filmItem.get(TAG_FID).toString(),
                                            filmItem.get(TAG_YEAR).toString(),
                                            filmItem.get(TAG_RATING).toString(),
                                            filmItem.get(TAG_RUNAME).toString(),
                                            filmItem.get(TAG_ENNAME).toString(),
                                            filmItem.get(TAG_IMAGEURL).toString(),
                                            filmItem.get(TAG_DESCRIPTION).toString(),
                                            String.valueOf(i)));
                                    filmsItems.add(new FilmItem(filmItem.get(TAG_FID).toString(),
                                            filmItem.get(TAG_YEAR).toString(),
                                            filmItem.get(TAG_RATING).toString(),
                                            filmItem.get(TAG_RUNAME).toString(),
                                            filmItem.get(TAG_ENNAME).toString(),
                                            i, 1));
                                } else if (filmItem.get(TAG_FID).toString().equals("512280")) {
                                    treeFilmsId.put(i, filmItem.get(TAG_FID).toString());
                                    treeFilmsYear.put(i, filmItem.get(TAG_YEAR).toString());
                                    treeFilmsRating.put(i, "0");
                                    treeFilmsRuName.put(i, filmItem.get(TAG_RUNAME).toString());
                                    treeFilmsEnName.put(i, filmItem.get(TAG_ENNAME).toString());

                                    films.add(new Film(filmItem.get(TAG_FID).toString(),
                                            filmItem.get(TAG_YEAR).toString(),
                                            "Не выставлена",
                                            filmItem.get(TAG_RUNAME).toString(),
                                            filmItem.get(TAG_ENNAME).toString(),
                                            "Отсутствует",
                                            "Отсутствует",
                                            String.valueOf(i)));
                                    filmsItems.add(new FilmItem(filmItem.get(TAG_FID).toString(),
                                            filmItem.get(TAG_YEAR).toString(),
                                            "0",
                                            filmItem.get(TAG_RUNAME).toString(),
                                            filmItem.get(TAG_ENNAME).toString(),
                                            i, 1));
                                } else if (filmItem.get(TAG_FID).toString().equals("841340")) {
                                    treeFilmsId.put(i, filmItem.get(TAG_FID).toString());
                                    treeFilmsYear.put(i, filmItem.get(TAG_YEAR).toString());
                                    treeFilmsRating.put(i, "0");
                                    treeFilmsRuName.put(i, filmItem.get(TAG_RUNAME).toString());
                                    treeFilmsEnName.put(i, filmItem.get(TAG_ENNAME).toString());

                                    films.add(new Film(filmItem.get(TAG_FID).toString(),
                                            filmItem.get(TAG_YEAR).toString(),
                                            "0",
                                            filmItem.get(TAG_RUNAME).toString(),
                                            filmItem.get(TAG_ENNAME).toString(),
                                            filmItem.get(TAG_IMAGEURL).toString(),
                                            filmItem.get(TAG_DESCRIPTION).toString(),
                                            String.valueOf(i)));
                                    filmsItems.add(new FilmItem(filmItem.get(TAG_FID).toString(),
                                            filmItem.get(TAG_YEAR).toString(),
                                            "0",
                                            filmItem.get(TAG_RUNAME).toString(),
                                            filmItem.get(TAG_ENNAME).toString(),
                                            i, 1));
                                }
                            }

                            Collections.sort(filmsItems, FilmItem.yearRatinCompare);
                            filmsItemsYears.addAll(filmsItems);
                            int k = 0;
                            for (int i = 0; i < filmsItems.size(); i++) {
                                String year = filmsItems.get(i).getYear();
                                if (i == 0) {
                                    filmsItemsYears.add(i, new FilmItem(year, year, year, year, year, -1, 2));
                                    k++;
                                } else {
                                    if (!filmsItems.get(i).getYear().equals(filmsItems.get(i - 1).getYear())) {
                                        filmsItemsYears.add(i + k, new FilmItem(year, year, year, year, year, -1, 2));
                                        k++;
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //setInitialData();
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
                    // создаем адаптер
                    DataAdapter adapter = new DataAdapter(MainActivity.this, filmsItemsYears);
                    // устанавливаем для списка адаптер
                    recyclerView.setAdapter(adapter);
                }
            });
        }
    }

    public void TestListener(View view) {
        String arrayPosition = ((TextView) view.findViewById(R.id.arrayPosition)).getText().toString();

        int nowId = Integer.parseInt(arrayPosition);

        String imageUrl = films.get(nowId).getImageUrl();
        String ruName = films.get(nowId).getRuName();
        String enName = films.get(nowId).getEnName();
        String year = films.get(nowId).getYear();
        String rating = films.get(nowId).getRank();
        String description = films.get(nowId).getDescription();

        Intent intent = new Intent(getApplicationContext(), FilmActivity.class);

        intent.putExtra(TAG_IMAGEURL, imageUrl);
        intent.putExtra(TAG_RUNAME, ruName);
        intent.putExtra(TAG_ENNAME, enName);
        intent.putExtra(TAG_YEAR, year);
        intent.putExtra(TAG_RATING, rating);
        intent.putExtra(TAG_DESCRIPTION, description);
        startActivityForResult(intent, 100);
    }
}
