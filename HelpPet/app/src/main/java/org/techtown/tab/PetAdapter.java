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

public class PetAdapter extends RecyclerView.Adapter<org.techtown.tab.PetAdapter.ViewHolder> implements OnPetItemClickListener, Filterable {



    ArrayList<PetInfo> items = new ArrayList<PetInfo>();
    ArrayList<PetInfo> filteredList = items;

    OnPetItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.pet_item, viewGroup, false);


        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        PetInfo item = filteredList.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void addItem(PetInfo item) {

        items.add(item);
        filteredList.add(item);
    }

    public void setItems(ArrayList<PetInfo> items, ArrayList<PetInfo> filteredList) {
        this.filteredList = filteredList;
        this.items = items;
    }

    public PetInfo getItem(int position) {
        return filteredList.get(position);
    }

    public void setItem(int position, PetInfo item) {
        items.set(position, item);
        filteredList.set(position, item);
    }

    public void setOnItemClickListener(OnPetItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView11;
        TextView textView12;
        TextView textView13;
        TextView textView14;
        ImageView imageView;

        public ViewHolder(View itemView, final OnPetItemClickListener listener) {
            super(itemView);

            textView11 = itemView.findViewById(R.id.textView11);
            textView12 = itemView.findViewById(R.id.textView12);
            textView13 = itemView.findViewById(R.id.textView13);
            textView14 = itemView.findViewById(R.id.textView14);
            imageView = itemView.findViewById(R.id.imageView);

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

        public void setItem(PetInfo item) {
            textView11.setText(item.getName());
            textView12.setText(item.getLocation());
            textView13.setText(item.getMobile());
            textView14.setText(item.getCondition());
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
                    ArrayList<PetInfo> filteringList = new ArrayList<PetInfo>();

                    for (PetInfo item : items) {
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
                filteredList = (ArrayList<PetInfo>)results.values;


                notifyDataSetChanged() ;

            }
        };
    }

}
