//https://webnautes.tistory.com/1249 에서 지도 사용법 참고함

package com.example.laundrybible;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ActionMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MapPage extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
 PlacesListener{
    private GoogleMap mMap; // 맵에 대한 것
    Intent intent; // 화면 바꿀 때 필요
    Button retBtn; // 뒤로 가기 버튼

    // 나의 현재 위치 저장 장소
    double nowLatitude;  //위도
    double nowLongitude; //경도
    LatLng MYLOCATION;

    ArrayList<Marker> previous_marker = new ArrayList<>();
    // 주변 위치 저장용

    RelativeLayout datalayout;
    TextView nameTxt, addrTxt;
    Button downBtn;

    String val = "0";
    String txt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 제거
        //getSupportActionBar().setTitle("세탁의 바이블");
        //getSupportActionBar().setSubtitle("지도보기");
        setContentView(R.layout.map_page);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        intent = getIntent();
        val = intent.getStringExtra("val");
        if(val.equals("1")){
            txt = intent.getStringExtra("search");
        }

        datalayout = findViewById(R.id.makerData);
        nameTxt = findViewById(R.id.getName);
        addrTxt = findViewById(R.id.getAddr);
        downBtn = findViewById(R.id.makerDown);

        retBtn = findViewById(R.id.retBtn);
        retBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datalayout.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationManager lcManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener lcListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateMap(location);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                alertProvider(provider);
            }
            @Override
            public void onProviderEnabled(String provider) {
                alertStatus(provider);
            }
            @Override
            public void onProviderDisabled(String provider) {
                checkProvider(provider);
            }
        };

        // 권한 승인에 대한 검사 및 처리 부분
        int permission1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if(permission1== PackageManager.PERMISSION_GRANTED && permission2==PackageManager.PERMISSION_GRANTED){
            lcManager.requestLocationUpdates(lcManager.NETWORK_PROVIDER, 1000, 0, lcListener);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }

    }

    private void checkProvider(String provider){
        Toast.makeText(this, provider + "에 의한 위치 서비스가 꺼져있습니다.", Toast.LENGTH_SHORT).show();
        intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    private void alertProvider(String provider){
        Toast.makeText(this, provider + "서비스가 켜졌습니다.", Toast.LENGTH_SHORT).show();
    }

    private void alertStatus(String provider){
        Toast.makeText(this, "위치서비스가 " + provider + "로 변경되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void updateMap(Location location){
        nowLatitude = location.getLatitude();   //위도
        nowLongitude = location.getLongitude(); //경도

        System.out.println("위도: " + nowLatitude + " 경도: " + nowLongitude);

        MYLOCATION = new LatLng(37.56, 126.97);

        showPlaceInformation(MYLOCATION);

        //markerOption = new MarkerOptions();
        //markerOption.position(MYLOCATION);
        //markerOption.title("현재위치");
        //mMap.addMarker(markerOption);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MYLOCATION, 15));
        mMap.setOnMarkerClickListener(this);

    }

    public void showPlaceInformation(LatLng location)
    {
        mMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(MapPage.this)
                .key("AIzaSyC6D5JSjdjMdb7Dwba95zG7yR2FAdNbl4I")
                .latlng(location.latitude, location.longitude)//현재 위치
                .radius(500) //500 미터 내에서 검색
                .type(PlaceType.LAUNDRY) //세탁소
                .language("ko", "KR")
                .build()
                .execute();
    }

    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

    }

    @Override
    public void onPlacesSuccess(final List<Place> places) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (noman.googleplaces.Place place : places) {

                    LatLng latLng
                            = new LatLng(place.getLatitude()
                            , place.getLongitude());

                    String markerSnippet = getCurrentAddress(latLng);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(place.getName());
                    markerOptions.snippet(markerSnippet);

                    if(val.equals("1")){
                        if(markerOptions.getTitle().contains(txt)){
                            Marker item = mMap.addMarker(markerOptions);
                            previous_marker.add(item);
                        }else {}
                    }else{
                        Marker item = mMap.addMarker(markerOptions);
                        previous_marker.add(item);
                    }
                }

                //중복 마커 제거
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(previous_marker);
                previous_marker.clear();
                previous_marker.addAll(hashSet);

            }
        });
    }

    @Override
    public void onPlacesFinished() {

    }

    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }
    } // 경도,위도를 주소로 변환

    @Override
    public boolean onMarkerClick(final Marker marker) {
        nameTxt.setText(marker.getTitle()+"");
        addrTxt.setText(marker.getSnippet()+"");
        datalayout.setVisibility(View.VISIBLE);
        datalayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplication(), RatingPage.class);
                intent.putExtra("latitude", marker.getPosition().latitude);
                System.out.println(marker.getPosition().latitude);
                intent.putExtra("longitude", marker.getPosition().longitude);
                intent.putExtra("name", marker.getTitle());
                intent.putExtra("address", marker.getSnippet());
                startActivity(intent);
            }
        });
        return false;
    } // 마커 클릭 이벤트

}