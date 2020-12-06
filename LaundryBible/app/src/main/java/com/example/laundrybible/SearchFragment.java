package com.example.laundrybible;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchFragment extends Fragment {
    Intent intent;
    EditText searchTxt;
    Button searchBtn, companyBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchTxt = view.findViewById(R.id.searchtxt);
        searchBtn = view.findViewById(R.id.mySearchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), MapPage.class);
                intent.putExtra("val", "0");
                startActivity(intent);
            }
        });

        companyBtn = view.findViewById(R.id.gpsSearchBtn);
        companyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = searchTxt.getText().toString();
                if (msg.equals("")){
                    Toast.makeText(getContext(), "검색할 업체를 선정해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    intent = new Intent(getActivity(), MapPage.class);
                    intent.putExtra("val", "1");
                    intent.putExtra("search", msg);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}