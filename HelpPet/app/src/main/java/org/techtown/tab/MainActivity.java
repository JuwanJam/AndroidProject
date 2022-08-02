package org.techtown.tab;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  OnHDatabaseCallback, OnDatabaseCallback{
    private static final String TAG = "MainActivity";

    PetDatabase database;
    HDatabase hdatabase;


    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    Fragment5 fragment5;
    Fragment6 fragment6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();
        fragment6 = new Fragment6();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment1).commit();

                        return true;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment2).commit();

                        return true;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment3).commit();
                        return true;
                    case R.id.tab4:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment4).commit();

                        return true;
                }

                return false;
            }
        });

        if (database != null) {
            database.close();
            database = null;
        }

        database = PetDatabase.getInstance(this);
        database.open();


        if (hdatabase != null) {
            hdatabase.close();
            hdatabase = null;
        }

        hdatabase = HDatabase.getInstance(this);
         hdatabase.open();


        Button button20 = findViewById(R.id.button20);
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment5).commit();

            }
        });

        Button button21 = findViewById(R.id.button21);
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment6).commit();

            }
        });




    }

    @Override public void insert (String name, String location, String mobile, String condition)
    {
        database.insertRecord(name, location, mobile, condition);
        Toast.makeText(getApplicationContext(), "펫 정보 추가", Toast.LENGTH_LONG).show();
    }


    @Override
    public ArrayList<PetInfo> selectAll () {
        ArrayList<PetInfo> result = database.selectAll();

        return result;
    }
    @Override
    public void insert_h (String name, String location, String mobile, String condition)
    {
        hdatabase.insertRecord(name, location, mobile, condition);
        Toast.makeText(getApplicationContext(), "병원 등록 완료", Toast.LENGTH_LONG).show();
    }


    @Override
    public ArrayList<HInfo> selectAll_h () {
        ArrayList<HInfo> result2 = hdatabase.selectAll_h();

        return result2;
    }


}
