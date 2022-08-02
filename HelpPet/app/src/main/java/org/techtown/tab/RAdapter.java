package org.techtown.tab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RAdapter extends RecyclerView.Adapter<org.techtown.tab.RAdapter.ViewHolder> implements OnRItemClickListener{



    ArrayList<RInfo> items = new ArrayList<RInfo>();

    OnRItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.r_item, viewGroup, false);


        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RInfo item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(RInfo item) {
        items.add(item);
    }

    public void setItems(ArrayList<RInfo> items) {
        this.items = items;
    }

    public RInfo getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, RInfo item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnRItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView30;
        TextView textView31;
        RatingBar ratingBar2;
        TextView textView41;


        public ViewHolder(View itemView, final OnRItemClickListener listener) {
            super(itemView);

            textView30 = itemView.findViewById(R.id.textView30);
            textView31 = itemView.findViewById(R.id.textView31);
            ratingBar2 = itemView.findViewById(R.id.ratingbarSmall2);
            textView41 = itemView.findViewById(R.id.textView41);


            ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    String rating_s = ""+rating;
                    textView41.setText(rating_s);
                }

            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);

                    }
                }
            });
        }

        public void setItem(RInfo item) {
            textView30.setText(item.getName());
            textView31.setText(item.getReview());
            ratingBar2.setRating(item.getRating());
        }

    }

}
