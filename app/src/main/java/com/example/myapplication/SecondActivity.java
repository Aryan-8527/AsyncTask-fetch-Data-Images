package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SecondActivity extends AppCompatActivity {
    TextView text_view_title;
    TextView text_view_details;
    ImageView image_views;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        text_view_details = findViewById(R.id.text_view_detailss);
        text_view_title = findViewById(R.id.text_view_titles);
        image_views = findViewById(R.id.image_views);

        Bundle bundle = getIntent().getExtras();

                              //for text Show //
        String tvtitle = getIntent().getStringExtra("title");
        String tvdetails = getIntent().getStringExtra("details");

                                 //for Image View show//
        String imagev = bundle.getString("image");

        Glide
                .with(this)
                .load(imagev)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(image_views);

        text_view_title.setText(tvtitle);
        text_view_details.setText(tvdetails);
    }
}