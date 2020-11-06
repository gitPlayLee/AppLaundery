package com.example.laundrybible;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
                meunList.setVisibility(View.INVISIBLE);
                menuBtn.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

}