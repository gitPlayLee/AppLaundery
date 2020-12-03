package com.example.laundrybible;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TipFrag_1 extends Fragment {

    TextView textView1 , textView2 , textView3 , textView4 , textView5 , textView6, textView7 , textView8
            , textView9 , textView10 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tip_frag_1_1, container, false);
        return view;
    }
}