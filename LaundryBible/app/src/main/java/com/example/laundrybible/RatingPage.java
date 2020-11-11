package com.example.laundrybible;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingPage extends AppCompatActivity {
    Intent intent;
    RatingBar rating; // 별로 평가하기
    Button resume; // 등록하기

    TextView lat, lon; // 위도, 경도, 연습용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 제거
        getSupportActionBar().setTitle("세탁의 바이블");
        getSupportActionBar().setSubtitle("상세정보");
        setContentView(R.layout.rating_page);

        intent = getIntent();

        rating = findViewById(R.id.rating);
        resume = findViewById(R.id.Enrollment);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);

        String latt = intent.getStringExtra("latitude");
        String lonn = intent.getStringExtra("longitude");
        lat.setText(latt);
        lon.setText(lonn);


    }
}