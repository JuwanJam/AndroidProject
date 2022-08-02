package org.techtown.tab;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.LOCATION_SERVICE;

public class Fragment4 extends Fragment {

    String[] permission_list = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    LocationManager locationManager;
    GoogleMap map;

    ArrayList<Double> lat_list = new ArrayList<Double>();
    ArrayList<Double> lng_list = new ArrayList<Double>();
    ArrayList<String> name_list = new ArrayList<String>();
    ArrayList<String> vicinity_list = new ArrayList<String>();
    ArrayList<Marker> marker_list = new ArrayList<Marker>();
    private SupportMapFragment supportMapFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        } else {
            init();
        }
        return rootView;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        init();
    }

    public void init() {


        FragmentManager fm = getActivity().getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();
        }
         MapReadyCallback callback1 = new MapReadyCallback();
        supportMapFragment.getMapAsync(callback1);


    }

    // 구글 지도 사용 준비가 완료되면 동작하는 콜백
    class MapReadyCallback implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            //Log.d("test123", "구글 지도 사용 준비 완료");
            getMyLocation();
        }
    }

    // 현재 위치를 측정하는 메서드
    public void getMyLocation() {
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        // 권한 확인 작업
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }

        // 이전에 측정했던 값을 가져온다.
        Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(location1 != null){
            setMyLocation(location1);
        } else {
            if(location2 != null){
                setMyLocation(location2);
            }
        }
        // 새롭게 측정한다.
             GetMyLocationListener listener = new GetMyLocationListener();

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, listener);
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, listener);
        }


    }

    public void setMyLocation(Location location){
        Log.d("test123", "위도 : " + location.getLatitude());
        Log.d("test123", "경도 : " + location.getLongitude());

        // 위도와 경도값을 관리하는 객체
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

        CameraUpdate update1 = CameraUpdateFactory.newLatLng(position);
        CameraUpdate update2 = CameraUpdateFactory.zoomTo(15f);

        map.moveCamera(update1);
        map.animateCamera(update2);

        // 권한 확인 작업
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return;
            }
        }
        // 현재 위치 표시
        map.setMyLocationEnabled(true);

        NetworkThread thread = new NetworkThread(location.getLatitude(), location.getLongitude());
        thread.start();
    }

    // 현재 위치 측정이 성공하면 반응하는 리스너
    class GetMyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            setMyLocation(location);
            locationManager.removeUpdates(this);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

    // 구글 서버에서 주변 정보를 받아오기 위한 쓰래드
    class NetworkThread extends Thread{

        double lat, lng;

        NetworkThread(double lat, double lng){
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        public void run() {
            super.run();

            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();

            String site= "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                    + "?key=AIzaSyBomvv14AyLslGsqRq4QOczD0ghZBvkFPA"
                    + "&location=" + lat + "," + lng
                    + "&radius=1000"
                    + "&language=ko"
                    + "&type=veterinary_care";



            Log.d("test123", "주소 : " + site);


            builder = builder.url(site);
            Request request = builder.build();

            Callback1 callback1 = new Callback1();
            Call call = client.newCall(request);
            call.enqueue(callback1);
        }
    }

    class Callback1 implements Callback {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) {
            try{
                String result = response.body().string();

                JSONObject obj = new JSONObject(result);

                String status = obj.getString("status");

                if(status.equals("OK")){
                    JSONArray results = obj.getJSONArray("results");

                    lat_list.clear();
                    lng_list.clear();
                    name_list.clear();
                    vicinity_list.clear();

                    for(int i = 0 ; i < results.length() ; i++){
                        JSONObject obj2 = results.getJSONObject(i);

                        JSONObject geometry = obj2.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        double lat2 = location.getDouble("lat");
                        double lng2 = location.getDouble("lng");

                        String name = obj2.getString("name");
                        String vicinity = obj2.getString("vicinity");

                        lat_list.add(lat2);
                        lng_list.add(lng2);
                        name_list.add(name);
                        vicinity_list.add(vicinity);
                    }
                    /*
                    for(int i = 0 ; i < lat_list.size() ; i++){
                        double a1 = lat_list.get(i);
                        double a2 = lng_list.get(i);
                        String a3 = name_list.get(i);
                        String a4 = vicinity_list.get(i);

                        Log.d("test123", a1 + "," + a2 + "," + a3 + "," + a4);
                    }
                    */

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 지도에 표시되어 있는 마커를 제거한다.
                            for(Marker marker : marker_list){
                                marker.remove();
                            }
                            marker_list.clear();

                            for(int i = 0 ; i < lat_list.size() ; i++){
                                double lat3 = lat_list.get(i);
                                double lng3 = lng_list.get(i);
                                String name3 = name_list.get(i);
                                String vicinity3 = vicinity_list.get(i);

                                LatLng position = new LatLng(lat3, lng3);

                                MarkerOptions option = new MarkerOptions();
                                option.position(position);

                                option.title(name3);
                                option.snippet(vicinity3);

                                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_mylocation);
                                option.icon(bitmap);

                                Marker marker = map.addMarker(option);
                                marker_list.add(marker);
                            }
                        }
                    });
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
