package org.techtown.tab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HAdapter extends RecyclerView.Adapter<org.techtown.tab.HAdapter.ViewHolder> implements OnHItemClickListener, Filterable {



    ArrayList<HInfo> items = new ArrayList<HInfo>();
    ArrayList<HInfo> filteredList = items;

    OnHItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.h_item, viewGroup, false);


        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        HInfo item = filteredList.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void addItem(HInfo item) {
        filteredList.add(item);
    }

    public void setItems(ArrayList<HInfo> items,  ArrayList<HInfo> filteredList) {
        this.items = items;
        this.filteredList = filteredList;
    }

    public HInfo getItem(int position) {
        return filteredList.get(position);
    }

    public void setItem(int position, HInfo item) {
        items.set(position, item);
        filteredList.set(position, item);
    }

    public void setOnItemClickListener(OnHItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView15;
        TextView textView16;
        TextView textView17;
        TextView textView18;
        ImageView imageView2;

        public ViewHolder(View itemView, final OnHItemClickListener listener) {
            super(itemView);

            textView15 = itemView.findViewById(R.id.textView15);
            textView16 = itemView.findViewById(R.id.textView16);
            textView17 = itemView.findViewById(R.id.textView17);
            textView18 = itemView.findViewById(R.id.textView18);
            imageView2 = itemView.findViewById(R.id.imageView2);

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

        public void setItem(HInfo item) {
            textView15.setText(item.getName());
            textView16.setText(item.getLocation());
            textView17.setText(item.getMobile());
            textView18.setText(item.getCondition());
        }

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    filterResults.values = items ;

                } else {
                    ArrayList<HInfo> filteringList = new ArrayList<HInfo>();

                    for (HInfo item : items) {
                        if (item.getLocation().contains(constraint.toString()))
                        {
                            filteringList.add(item);
                        }
                    }
                    filterResults.values = filteringList;
                }


                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<HInfo>)results.values;


                notifyDataSetChanged() ;

            }
        };
    }

}
