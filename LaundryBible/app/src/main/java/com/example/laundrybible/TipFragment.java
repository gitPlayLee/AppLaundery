package com.example.laundrybible;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TipFragment extends Fragment {
    ArrayList<String> textArray = new ArrayList<>();
    ListView meunList;
    FloatingActionButton menuBtn;

    FragmentManager fragManager; // 프레그먼트 매니져
    FragmentTransaction transjeck; //트랜잭션

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tip, container, false);
        textArray.clear();
        textArray.add("첫 번쨰");
        textArray.add("두 번쨰");
        textArray.add("세 번쨰");

        menuBtn = view.findViewById(R.id.meunBtn);
        meunList = view.findViewById(R.id.menuList);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, textArray);
        meunList.setAdapter(adapter);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                meunList.setVisibility(View.VISIBLE);
                menuBtn.setVisibility(View.INVISIBLE);
            }
        });

        meunList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        System.out.println("첫 페이지");
                        break;
                    case 1:
                        System.out.println("두 번째 페이지");
                        break;
                    case 2:
                        System.out.println("세 번째 페이지");
                        break;
                }
                meunList.setVisibility(View.INVISIBLE);
                menuBtn.setVisibility(View.VISIBLE);
            }
        });

        fragManager = getFragmentManager();
        transjeck = fragManager.beginTransaction();
        transjeck.replace(R.id.frgScreen, new TipFrag_1());
        transjeck.commit();
        // 첫 번째 프래그먼트 지정하기

        return view;
    }

}