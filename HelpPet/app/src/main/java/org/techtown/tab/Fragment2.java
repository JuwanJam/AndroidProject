package org.techtown.tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Fragment2 extends Fragment implements TextWatcher {
    RecyclerView recyclerView;
    PetAdapter adapter;
    EditText editText50;


    OnDatabaseCallback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (OnDatabaseCallback) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PetAdapter();
        recyclerView.setAdapter(adapter);

        ArrayList<PetInfo> result = callback.selectAll();
        adapter.setItems(result, result);

        adapter.setOnItemClickListener(new OnPetItemClickListener() {
            @Override
            public void onItemClick(PetAdapter.ViewHolder holder, View view, int position) {
                PetInfo item = adapter.getItem(position);

                Intent intent = new Intent(getContext(), PetMax.class);

                intent.putExtra("name", item.getName());
                intent.putExtra("location", item.getLocation());
                intent.putExtra("mobile", item.getMobile());
                intent.putExtra("condition", item.getCondition());

                startActivity(intent);



            }


        });

        editText50 = rootView.findViewById(R.id.editText50);
        editText50.addTextChangedListener(this);

        Button button10 = rootView.findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<PetInfo> result = callback.selectAll();
                adapter.setItems(result, result);
                adapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


}
