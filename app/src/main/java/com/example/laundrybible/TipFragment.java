package com.example.laundrybible;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TipFragment extends Fragment {
    ArrayList<String> textArray = new ArrayList<>();
    ArrayList<Fragment> frgList = new ArrayList<>();

    FrameLayout tiptool;
    ListView meunList;
    FloatingActionButton menuBtn;
    ImageView tipImg;

    FragmentManager fragManager; // 프레그먼트 매니져
    FragmentTransaction transjeck; //트랜잭션

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tip, container, false);
        textArray.clear();
        textArray.add("상황별 세탁 방법"); //글 내용 tip_frag_1_1.xml에 작성
        textArray.add("옷감별 세탁 방법"); //글 내용 tip_frag_2_1.xml에 작성
        textArray.add("아크릴");
        textArray.add("앙고라");
        textArray.add("캐시미어");
        textArray.add("쉬폰");
        textArray.add("코튼");
        textArray.add("데님");
        textArray.add("퍼");
        textArray.add("기모");
        textArray.add("가죽");
        textArray.add("린넨");
        textArray.add("나일론");
        textArray.add("폴리에스테르");
        textArray.add("레이온");
        textArray.add("실크");
        textArray.add("스웨이드");
        textArray.add("텐셀");
        textArray.add("트위드");
        textArray.add("울");
        // 이미지 수 만큼 add로 항목 넣어주기

        tiptool = view.findViewById(R.id.tipTool);
        tipImg = view.findViewById(R.id.tipImg);
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
            //리스트 뷰 항목 클릭 이벤트 처리
            @SuppressLint("RestrictedApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 2){
                    tiptool.setVisibility(View.VISIBLE);
                    tipImg.setVisibility(View.INVISIBLE);
                    fragManager = getFragmentManager(); // 매니져 재설정
                    transjeck = fragManager.beginTransaction(); // 트랜잭션 재설정
                    transjeck.addToBackStack(null);
                    transjeck.replace(R.id.tipTool, frgList.get(position));
                    transjeck.commit();
                }else{ // 리스트 만들어서 이미지 작성하지 않을 경우..
                    //리스트 만들어서 이미지를 보관 및 관리하면 다른 방법으로 가능
                    tipImg.setVisibility(View.VISIBLE);
                    tiptool.setVisibility(View.INVISIBLE);
                    //세탁 항목만큼 case 추가 및 이미지 작업 필요
                    switch (position){
                        case 2:
                            tipImg.setImageResource(R.drawable.acrylic);
                            //tipImg.setImageResource();
                            break;
                        case 3:
                            tipImg.setImageResource(R.drawable.angora);
                            break;
                        case 4:
                            tipImg.setImageResource(R.drawable.cashmere);
                            break;
                        case 5:
                            tipImg.setImageResource(R.drawable.chiffon);
                            break;
                        case 6:
                            tipImg.setImageResource(R.drawable.cotton);
                            break;
                        case 7:
                            tipImg.setImageResource(R.drawable.denim);
                            break;
                        case 8:
                            tipImg.setImageResource(R.drawable.fur);
                            break;
                        case 9:
                            tipImg.setImageResource(R.drawable.gimo);
                            break;
                        case 10:
                            tipImg.setImageResource(R.drawable.leather);
                            break;
                        case 11:
                            tipImg.setImageResource(R.drawable.linen);
                            break;
                        case 12:
                            tipImg.setImageResource(R.drawable.nylon);
                            break;
                        case 13:
                            tipImg.setImageResource(R.drawable.polyester);
                            break;
                        case 14:
                            tipImg.setImageResource(R.drawable.rayon);
                            break;
                        case 15:
                            tipImg.setImageResource(R.drawable.silk);
                            break;
                        case 16:
                            tipImg.setImageResource(R.drawable.suede);
                            break;
                        case 17:
                            tipImg.setImageResource(R.drawable.tencel);
                            break;
                        case 18:
                            tipImg.setImageResource(R.drawable.tweed);
                            break;
                        case 19:
                            tipImg.setImageResource(R.drawable.wool);
                            break;
                    }
                }

                meunList.setVisibility(View.INVISIBLE);
                menuBtn.setVisibility(View.VISIBLE);
            }
        }); // 리스트에서 선택하면 메뉴 바뀌기

        return view;
    }

}