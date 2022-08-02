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


public class Fragment3 extends Fragment implements TextWatcher {
    RecyclerView recyclerView2;
    HAdapter adapter;
    EditText editText51;

    OnHDatabaseCallback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (OnHDatabaseCallback) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        recyclerView2 = rootView.findViewById(R.id.recyclerView2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(layoutManager);

        adapter = new HAdapter();
        recyclerView2.setAdapter(adapter);

        ArrayList<HInfo> result = callback.selectAll_h();
        adapter.setItems(result, result);

        adapter.setOnItemClickListener(new OnHItemClickListener() {
            @Override
            public void onItemClick(HAdapter.ViewHolder holder, View view, int position) {
                HInfo item = adapter.getItem(position);

                Intent intent = new Intent(getContext(), HMax.class);

                intent.putExtra("_id", item.getId());
                intent.putExtra("name", item.getName());
                intent.putExtra("location", item.getLocation());
                intent.putExtra("mobile", item.getMobile());
                intent.putExtra("condition", item.getCondition());

                startActivity(intent);



            }


        });

        editText51 = rootView.findViewById(R.id.editText51);
        editText51.addTextChangedListener(this);

        Button button12 = rootView.findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HInfo> result = callback.selectAll_h();
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
