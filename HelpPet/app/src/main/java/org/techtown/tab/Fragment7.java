package org.techtown.tab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Fragment7 extends Fragment {
    RecyclerView recyclerView3;
    RAdapter adapter;

    EditText editText30;
    EditText editText31;
    RatingBar ratingBar;
    int hid;
    TextView textView40;


    OnRDatabaseCallback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (OnRDatabaseCallback) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment7, container2, false);

        Bundle bundle = getArguments();


        editText30 = (EditText) rootView.findViewById(R.id.editText30);
        editText31 = (EditText) rootView.findViewById(R.id.editText31);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingbarSmall);
        textView40 = (TextView) rootView.findViewById(R.id.textView40);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String rating_s = ""+rating;
                textView40.setText(rating_s);
            }

        });


        Button button30 = (Button) rootView.findViewById(R.id.button30);
        button30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                hid = bundle.getInt("_id");
                String name = editText30.getText().toString();
                String review = editText31.getText().toString();
                float rating = ratingBar.getRating();

                callback.insert_r(name, review, rating, hid);

                ArrayList<RInfo> result = callback.selectAll_r();
                adapter.setItems(result);
                adapter.notifyDataSetChanged();
            }
        });



        recyclerView3 = rootView.findViewById(R.id.recyclerView3);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView3.setLayoutManager(layoutManager);

        adapter = new RAdapter();
        recyclerView3.setAdapter(adapter);

        ArrayList<RInfo> result = callback.selectAll_r();
        adapter.setItems(result);

        adapter.setOnItemClickListener(new OnRItemClickListener() {
            @Override
            public void onItemClick(RAdapter.ViewHolder holder, View view, int position) {
                RInfo item = adapter.getItem(position);


            }

        });

        return rootView;
    }


}
