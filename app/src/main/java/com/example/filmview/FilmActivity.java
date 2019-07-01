package com.example.filmview;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static java.net.URI.create;

public class FilmActivity extends Activity {
    private static final String TAG_FID = "id";
    private static final String TAG_YEAR = "year";
    private static final String TAG_RATING = "rating";
    private static final String TAG_RUNAME = "localized_name";
    private static final String TAG_ENNAME = "name";
    private static final String TAG_IMAGEURL = "image_url";
    private static final String TAG_DESCRIPTION = "description";

    ImageView imageView;

    TextView textRuName, textEnName, textYear, textRating, textDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        Intent intentDetails = getIntent();
        String imageUrl = intentDetails.getStringExtra(TAG_IMAGEURL);
        String ruName = intentDetails.getStringExtra(TAG_RUNAME);
        String enName = intentDetails.getStringExtra(TAG_ENNAME);
        String year = intentDetails.getStringExtra(TAG_YEAR);
        String rating = intentDetails.getStringExtra(TAG_RATING);
        String description = intentDetails.getStringExtra(TAG_DESCRIPTION);

        imageView = (ImageView) findViewById(R.id.filmImage);
        textRuName = (TextView) findViewById(R.id.filmRuName);
        textEnName = (TextView) findViewById(R.id.filmEnName);
        textYear = (TextView) findViewById(R.id.filmYear);
        textRating = (TextView) findViewById(R.id.filmRating);
        textDescription = (TextView) findViewById(R.id.filmDescription);

        Picasso.with(FilmActivity.this).load(imageUrl).into(imageView);
        //imageView.setImageURI(URI.create(imageUrl));
        textRuName.setText(ruName);
        textEnName.setText(enName);
        textYear.setText(year);
        textRating.setText(rating);
        textDescription.setText(description);
    }
}
