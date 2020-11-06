package com.example.laundrybible;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

public class RatingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 제거
        getSupportActionBar().setTitle("세탁의 바이블");
        getSupportActionBar().setSubtitle("상세정보");
        setContentView(R.layout.rating_page);
    }
}