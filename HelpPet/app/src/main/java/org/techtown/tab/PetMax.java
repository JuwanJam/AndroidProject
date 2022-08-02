package org.techtown.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PetMax extends AppCompatActivity {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_max);
        Button button_chat = findViewById(R.id.button_chat);

        button_chat = findViewById(R.id.button_chat);

        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PetMax.this,ChatMainActivity.class);
                startActivity(intent);

            }
        });

        String name = "";
        String location = "";
        String mobile = "";
        String condition = "";

        Bundle extras = getIntent().getExtras();

        name = extras.getString("name");
        location = extras.getString("location");
        mobile = extras.getString("mobile");
        condition = extras.getString("condition");


        TextView textView5 = findViewById(R.id.textView5);
        TextView textView6 = findViewById(R.id.textView6);
        TextView textView7 = findViewById(R.id.textView7);
        TextView textView8 = findViewById(R.id.textView8);

        textView5.setText("반려동물 종 :\t" +name);
        textView6.setText("\t" +location);
        textView7.setText("\t" +mobile);
        textView8.setText("상태 :\n" +condition);



    }
}
