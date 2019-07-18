package com.example.filmview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private OnItemClick mCallback;

    private final int TYPE_ITEM_FILM = 1;
    private final int TYPE_ITEM_YEAR = 2;
    private LayoutInflater inflater;
    private Film film;
    private String year;
    private List<Object> filmItemsYears;

    @Override
    public int getItemViewType(int position) {
        if (filmItemsYears.get(position) instanceof Film) {
            film = (Film) filmItemsYears.get(position);
            return TYPE_ITEM_FILM;
        } else {
            year = (String) filmItemsYears.get(position);
            return  TYPE_ITEM_YEAR;
        }
    }

    DataAdapter(Context context, List<Object> films, OnItemClick listener) {
        this.filmItemsYears = films;
        this.inflater = LayoutInflater.from(context);
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ITEM_FILM:
                view = inflater.inflate(R.layout.list_item, parent, false);
                return new ViewHolder(view);
            case TYPE_ITEM_YEAR:
                view = inflater.inflate(R.layout.year_item, parent, false);
                return new ViewHolder(view);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull final DataAdapter.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        switch (type) {

            case TYPE_ITEM_FILM:
                holder.ruNameView.setText(film.getRuName());
                holder.enNmeView.setText(film.getEnName());
                holder.ratingView.setText(film.getRank());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCallback.onClick((Film) filmItemsYears.get(position));
                    }
                });
                break;
            case TYPE_ITEM_YEAR:
                holder.yearItemView.setText(year);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return filmItemsYears.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView ruNameView, enNmeView, ratingView, yearItemView;
        ViewHolder(View view) {
            super(view);
            ruNameView = view.findViewById(R.id.ruName);
            enNmeView = view.findViewById(R.id.enName);
            ratingView = view.findViewById(R.id.rating);
            yearItemView = view.findViewById(R.id.year_view);
        }
    }
}