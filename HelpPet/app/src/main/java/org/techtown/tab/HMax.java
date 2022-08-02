package org.techtown.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HMax extends AppCompatActivity implements OnRDatabaseCallback{
    RDatabase rdatabase;

    Fragment7 fragment7;
    int num;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_max);

        fragment7 = new Fragment7();
        getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragment7).commit();


        Button button_chat = findViewById(R.id.button_chat);

        button_chat = findViewById(R.id.button_chat);

        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( HMax.this, ChatMainActivity.class );
                startActivity(intent);

            }
        });





        String name = "";
        String location = "";
        String mobile = "";
        String condition = "";

        Bundle extras = getIntent().getExtras();

        num = extras.getInt("_id");
        name = extras.getString("name");
        location = extras.getString("location");
        mobile = extras.getString("mobile");
        condition = extras.getString("condition");


        TextView textView20 = findViewById(R.id.textView20);
        TextView textView21 = findViewById(R.id.textView21);
        TextView textView22 = findViewById(R.id.textView22);
        TextView textView23 = findViewById(R.id.textView23);

        textView20.setText(name);
        textView21.setText("병원 위치 :\t" + location);
        textView22.setText("병원 번호 :\t" + mobile);
        textView23.setText("병원 소개 :\n" + condition);

        Bundle bundle1 = new Bundle();
        bundle1.putInt("_id", num);
        fragment7.setArguments(bundle1);


        if (rdatabase != null) {
            rdatabase.close();
            rdatabase = null;
        }

        rdatabase = RDatabase.getInstance(this);
        rdatabase.open();



    }
    @Override public void insert_r (String name, String review, float rating, int hid)
    {
        rdatabase.insertRecord(name, review, rating, hid);
    }


    @Override
    public ArrayList<RInfo> selectAll_r () {
        ArrayList<RInfo> result = rdatabase.selectAll_r(num);

        return result;
    }
}
