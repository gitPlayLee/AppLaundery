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
    Button resume, cancel; // 등록하기

    TextView lat, lon, lanName, addr; // 위도, 경도, 연습용
    TextView star5, star4, star3, star2, star1, starAvg;

    private DatabaseReference mDatabase;

    Rating ratingObj = new Rating();

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
        cancel = findViewById(R.id.Cancel);
        lanName = findViewById(R.id.lanName);
        addr = findViewById(R.id.addr);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);

        star5 = findViewById(R.id.star5);
        star4 = findViewById(R.id.star4);
        star3 = findViewById(R.id.star3);
        star2 = findViewById(R.id.star2);
        star1 = findViewById(R.id.star1);
        starAvg = findViewById(R.id.starAvg);

        String name = intent.getStringExtra("name");
        final String address = intent.getStringExtra("address");
        String latt = intent.getStringExtra("latitude");
        String lonn = intent.getStringExtra("longitude");
        lanName.setText("업체명: " + name);
        addr.setText("주소: " + address);
        lat.setText(latt + "");
        lon.setText(lonn + "");

        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String addr = address;
                System.out.println(addr);
                ratingObj = snapshot.child("users").child(addr).getValue(Rating.class);

                try {
                    star5.setText(ratingObj.getFive()+"명");
                    star4.setText(ratingObj.getFour()+"명");
                    star3.setText(ratingObj.getThree()+"명");
                    star2.setText(ratingObj.getTwo()+"명");
                    star1.setText(ratingObj.getOne()+"명");
                    calcularAvg();
                }catch (Exception e){ // 등록 정보가 없을 경우, 오류가 생김
                    ratingObj = new Rating();
                    star5.setText("0명");
                    star4.setText("0명");
                    star3.setText("0명");
                    star2.setText("0명");
                    star1.setText("0명");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // 등록 - 저장하기 버튼 클릭 이벤트
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    //String getAddress = addr.getText().toString();
                    String getAddress = address;
                    float getRating = rating.getRating();


                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("address", getAddress);
                result.put("rating", getRating);

                saveLaundry(getAddress,getRating);
                finish();
            }
       });
        //취소하기
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void calcularAvg(){
        float avg = 0; //평균
        int member = 0; // 평가 수
        member = ratingObj.five + ratingObj.four + ratingObj.three + ratingObj.two + ratingObj.one;
        avg = (ratingObj.five * 5) + (ratingObj.four * 4)
                + (ratingObj.three * 3) + (ratingObj.two * 2)
                + (ratingObj.one * 1);
        avg = avg / member;
        starAvg.setText(avg+"점");
    }


//데이터베이스에 주소, 레이팅 저장
    public void saveLaundry(String address, float rating) {

        //Rating ratingObj = new Rating();

        if(rating == 5) {
            ratingObj.five = ratingObj.five + 1;
        } else if(rating == 4) {
            ratingObj.four = ratingObj.four + 1;
        } else if(rating == 3) {
            ratingObj.three = ratingObj.three + 1;
        } else if(rating == 2) {
            ratingObj.two = ratingObj.two + 1;
        } else if(rating == 1) {
            ratingObj.one = ratingObj.one + 1;
        }

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

    };

}