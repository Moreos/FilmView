package com.example.filmview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnItemClick {
    public static final String URL = "http://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json";

    public static final String TAG = "testStart";

    private static final String TAG_FID = "id";
    private static final String TAG_YEAR = "year";
    private static final String TAG_RATING = "rating";
    private static final String TAG_RUNAME = "localized_name";
    private static final String TAG_ENNAME = "name";
    private static final String TAG_IMAGEURL = "image_url";
    private static final String TAG_DESCRIPTION = "description";

    private List<Object> filmsItemsYears = new ArrayList<>();

    private WeakReference<MainActivity> activityWeakReference;

    private List<FilmItemGson> films = new ArrayList<>();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = findViewById(R.id.swipeContainer);

        /*
        FCM - потом
         */

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.abs_layout);
        actionBar.setTitle(R.string.titleMain);

        activityWeakReference = new WeakReference<>(MainActivity.this);
        mSwipeRefreshLayout = findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        FireMessage f = null;
        try {
            f = new FireMessage("MY TITLE", "TEST MESSAGE");
            //TO SINGLE DEVICE
    /*  String fireBaseToken="c2N_8u1leLY:APA91bFBNFYDARLWC74QmCwziX-YQ68dKLNRyVjE6_sg3zs-dPQRdl1QU9X6p8SkYNN4Zl7y-yxBX5uU0KEKJlam7t7MiKkPErH39iyiHcgBvazffnm6BsKjRCsKf70DE5tS9rIp_HCk";
       f.sendToToken(fireBaseToken); */

            // TO MULTIPLE DEVICE
    /*  JSONArray tokens = new JSONArray();
      tokens.put("c2N_8u1leLY:APA91bFBNFYDARLWC74QmCwziX-YQ68dKLNRyVjE6_sg3zs-dPQRdl1QU9X6p8SkYNN4Zl7y-yxBX5uU0KEKJlam7t7MiKkPErH39iyiHcgBvazffnm6BsKjRCsKf70DE5tS9rIp_HCk");
      tokens.put("c2R_8u1leLY:APA91bFBNFYDARLWC74QmCwziX-YQ68dKLNRyVjE6_sg3zs-dPQRdl1QU9X6p8SkYNN4Zl7y-yxBX5uU0KEKJlam7t7MiKkPErH39iyiHcgBvazffnm6BsKjRCsKf70DE5tS9rIp_HCk");
       f.sendToGroup(tokens);  */

            //TO TOPIC
            String topic="yourTopicName";
            f.sendToTopic(topic);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        startRecyler();

        showAllFilms();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                activityWeakReference = new WeakReference<>(MainActivity.this);
                mSwipeRefreshLayout.setRefreshing(false);
                filmsItemsYears.clear();
                films.clear();
                showAllFilms();
            }
        }, 1000);
    }

    public void showAllFilms() {
        RetrofitService retrofitService = Controller.getApi();

        retrofitService.getFilms().enqueue(new Callback<FilmsItems>() {
            @Override
            public void onResponse(@NonNull Call<FilmsItems> call, @NonNull Response<FilmsItems> response) {
                if (response.body() != null) {
                    films.addAll(response.body().getFilms());

                    Collections.sort(films, FilmItemGson.yearRatingCompare);
                    filmsItemsYears.addAll(films);
                    int k = 0;
                    for (int i = 0; i < films.size(); i++) {
                        String year = String.valueOf(films.get(i).getYear());
                        if (i == 0) {
                            filmsItemsYears.add(i, year);
                            k++;
                        } else {
                            if (films.get(i).getYear() != (films.get(i - 1).getYear())) {
                                filmsItemsYears.add(i + k, year);
                                k++;
                            }
                        }
                    }

                    startRecyler();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Ошибка с подключением. \nПовторите попытку позже или проверьте подключение.",
                            Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "Response string: " + films.toString());
            }

            @Override
            public void onFailure(@NonNull Call<FilmsItems> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Ошибка с подключением. \nПовторите попытку позже или проверьте подключение.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startRecyler() {
        if (activityWeakReference != null) {
            //setInitialData();
            RecyclerView recyclerView = findViewById(R.id.list);
            // создаем адаптер
            DataAdapter adapter = new DataAdapter(MainActivity.this, filmsItemsYears, MainActivity.this);

            // устанавливаем для списка адаптер
            recyclerView.setAdapter(adapter);
            Log.d(TAG, "proshlo");
        }
    }
    
    @Override
    public void onClick(FilmItemGson filmItemGson) {
        Intent intent = new Intent(getApplicationContext(), FilmActivity.class);

        String imageUrl = filmItemGson.getImage_url();
        String ruName = filmItemGson.getLocalized_name();
        String enName = filmItemGson.getName();
        String year = String.valueOf(filmItemGson.getYear());
        String rating = String.valueOf(filmItemGson.getRating());
        String description = filmItemGson.getDescription();

        intent.putExtra(TAG_IMAGEURL, imageUrl);
        intent.putExtra(TAG_RUNAME, ruName);
        intent.putExtra(TAG_ENNAME, enName);
        intent.putExtra(TAG_YEAR, year);
        intent.putExtra(TAG_RATING, rating);
        intent.putExtra(TAG_DESCRIPTION, description);

        startActivity(intent);
    }
}