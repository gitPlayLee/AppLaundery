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
    ArrayList<Fragment> frgList = new ArrayList<>();
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


        frgList.add(new TipFrag_1());
        frgList.add(new TipFrag_2());

        fragManager = getFragmentManager();
        transjeck = fragManager.beginTransaction();
        transjeck.replace(R.id.tipTool, frgList.get(0));
        transjeck.commit();
        // 첫 번째 프래그먼트 지정하기

        meunList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragManager = getFragmentManager(); // 매니져 재설정
                transjeck = fragManager.beginTransaction(); // 트랜잭션 재설정
                transjeck.addToBackStack(null);
                transjeck.replace(R.id.tipTool, frgList.get(position));
                transjeck.commit();

                meunList.setVisibility(View.INVISIBLE);
                menuBtn.setVisibility(View.VISIBLE);
            }
        }); // 리스트에서 선택하면 메뉴 바뀌기

        return view;
    }

}