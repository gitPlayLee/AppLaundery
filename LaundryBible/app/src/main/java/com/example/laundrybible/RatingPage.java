package com.example.laundrybible;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;

public class RatingPage extends AppCompatActivity {

    RatingBar rating; // 별로 평가하기
    Button resume; // 등록하기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 제거
        getSupportActionBar().setTitle("세탁의 바이블");
        getSupportActionBar().setSubtitle("상세정보");
        setContentView(R.layout.rating_page);

        rating = findViewById(R.id.rating);
        resume = findViewById(R.id.Enrollment);
    }
}