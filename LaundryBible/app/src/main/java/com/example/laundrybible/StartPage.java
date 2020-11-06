package com.example.laundrybible;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class StartPage extends AppCompatActivity {
    static ArrayList<String> textArray = new ArrayList<>();

    FragmentManager fManager; // 프레그먼트 매니져
    FragmentTransaction trans; //트랜잭션
    ArrayList<Fragment> tabSelct; // 프레그먼트 저장소
    TabLayout selTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 제거
        getSupportActionBar().setTitle("세탁의 바이블");
        getSupportActionBar().setSubtitle("빨래방 검색");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu);
        setContentView(R.layout.start_page);

        tabSelct = new ArrayList<>(); // 프레그먼트 보관소
        selTab = findViewById(R.id.selectTab);

        selTab.addTab(selTab.newTab().setText("찾기")); // 가장 처음 = 메인 화면
        selTab.addTab(selTab.newTab().setText("세탁Tip")); // 캐릭터 스테이터스

        tabSelct.add(new SearchFragment());
        tabSelct.add(new TipFragment());

        fManager = getSupportFragmentManager();
        trans = fManager.beginTransaction();
        trans.replace(R.id.frgScreen, tabSelct.get(0));
        trans.commit();
        // 첫 번째 프래그먼트 지정하기

        selTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fManager = getSupportFragmentManager(); // 매니져 재설정
                trans = fManager.beginTransaction(); // 트랜잭션 재설정
                trans.addToBackStack(null);
                trans.replace(R.id.frgScreen, tabSelct.get(tab.getPosition()));
                trans.commit();
                switch (tab.getPosition()){
                    case 0:
                        getSupportActionBar().setSubtitle("빨래방 찾기");
                        break;
                    case 1:
                        getSupportActionBar().setSubtitle("세탁Tip");
                        break;
                    default:
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

    }
}