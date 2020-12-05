package com.example.laundrybible;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RatingPage extends AppCompatActivity{
    Intent intent;
    RatingBar rating; // 별로 평가하기
    Button resume; // 등록하기

    TextView lat, lon, lanName, addr; // 위도, 경도, 연습용
    TextView star5, star4, star3, star2, star1;

    private DatabaseReference mDatabase;


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
        lanName = findViewById(R.id.lanName);
        addr = findViewById(R.id.addr);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);

        star5 = findViewById(R.id.star5);
        star4 = findViewById(R.id.star4);
        star3 = findViewById(R.id.star3);
        star2 = findViewById(R.id.star2);
        star1 = findViewById(R.id.star1);

        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String latt = intent.getStringExtra("latitude");
        String lonn = intent.getStringExtra("longitude");
        lanName.setText("업체명: " + name);
        addr.setText("주소: " + address);
        lat.setText(latt + "");
        lon.setText(lonn + "");

        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    String getAddress = addr.getText().toString();
                    float getRating = rating.getRating();


                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("address", getAddress);
                result.put("rating", getRating);

                saveLaundry(getAddress,getRating);
            }
       });

    }

//데이터베이스에 주소, 레이팅 저장
    public void saveLaundry(String address, float rating) {

        Rating ratingObj = new Rating();

        mDatabase.child("users").child(address).setValue(ratingObj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(RatingPage.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(RatingPage.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

        if(rating == 5) {
            ratingObj.five = ratingObj.five + 1;
            star5.setText(Integer.toString(ratingObj.five) + "명");
        } else if(rating == 4) {
            ratingObj.four = ratingObj.four + 1;
            star4.setText(Integer.toString(ratingObj.four) + "명");
        } else if(rating == 3) {
            ratingObj.three = ratingObj.three + 1;
            star3.setText(Integer.toString(ratingObj.three) + "명");
        } else if(rating == 2) {
            ratingObj.two = ratingObj.two + 1;
            star2.setText(Integer.toString(ratingObj.two) + "명");
        } else if(rating == 1) {
            ratingObj.one = ratingObj.one + 1;
            star1.setText(Integer.toString(ratingObj.one) + "명");
        }

    };

}