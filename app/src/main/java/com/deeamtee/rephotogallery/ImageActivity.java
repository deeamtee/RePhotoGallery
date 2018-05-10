package com.deeamtee.rephotogallery;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class ImageActivity extends AppCompatActivity {
    private static String TAG = "ImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        int position = extras.getInt("position");
        int itemCount = extras.getInt("itemCount");

        String itemCounterToolbar = String.valueOf(position + 1) + " из " + String.valueOf(itemCount);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(itemCounterToolbar);

        ImageView imageView = findViewById(R.id.image_view);
        Glide.with(this)
                .load(url)
                .into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
