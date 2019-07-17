package com.example.filmview;

<<<<<<< Updated upstream
import android.app.Activity;
import android.app.ListActivity;
=======
>>>>>>> Stashed changes
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

<<<<<<< Updated upstream
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static java.net.URI.create;

public class FilmActivity extends Activity {
    private static final String TAG_FID = "id";
=======
public class FilmActivity extends AppCompatActivity {
    //private static final String TAG_FID = "id";
>>>>>>> Stashed changes
    private static final String TAG_YEAR = "year";
    private static final String TAG_RATING = "rating";
    private static final String TAG_RUNAME = "localized_name";
    private static final String TAG_ENNAME = "name";
    private static final String TAG_IMAGEURL = "image_url";
    private static final String TAG_DESCRIPTION = "description";

    ImageView imageView;

    TextView textEnName, textYear, textRating, textDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intentDetails = getIntent();
        String imageUrl = intentDetails.getStringExtra(TAG_IMAGEURL);
        String ruName = intentDetails.getStringExtra(TAG_RUNAME);
        String enName = intentDetails.getStringExtra(TAG_ENNAME);
        String year = intentDetails.getStringExtra(TAG_YEAR);
        String rating = intentDetails.getStringExtra(TAG_RATING);
        String description = intentDetails.getStringExtra(TAG_DESCRIPTION);

<<<<<<< Updated upstream
        imageView = (ImageView) findViewById(R.id.filmImage);
        textRuName = (TextView) findViewById(R.id.filmRuName);
        textEnName = (TextView) findViewById(R.id.filmEnName);
        textYear = (TextView) findViewById(R.id.filmYear);
        textRating = (TextView) findViewById(R.id.filmRating);
        textDescription = (TextView) findViewById(R.id.filmDescription);
=======
        imageView = findViewById(R.id.filmImage);
        textEnName = findViewById(R.id.filmEnName);
        textYear = findViewById(R.id.filmYear);
        textRating = findViewById(R.id.filmRating);
        textDescription = findViewById(R.id.filmDescription);
>>>>>>> Stashed changes

        Picasso.with(FilmActivity.this).load(imageUrl).into(imageView);
        actionBar.setTitle(ruName);
        textEnName.setText(enName);
        textYear.setText(year);
        textRating.setText(rating);
        textDescription.setText(description);
        Picasso.with(FilmActivity.this).load(imageUrl).into(imageView);

        /*
         Если постера нет или ссылка на него не рабочая, не работает
        if (imageUrl.equals("") || imageUrl == null || imageUrl.equals("Отсутствует")) {
            Picasso.with(FilmActivity.this).load(R.drawable.notitleimage).into(imageView);
            //imageView.setVisibility(View.GONE);
        } else {
            //imageView.setVisibility(View.VISIBLE);
            Picasso.with(FilmActivity.this).load(imageUrl).into(imageView);
        }
        Toast.makeText(getApplicationContext(), imageUrl + "", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
