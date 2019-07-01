package com.example.filmview;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private final int TYPE_ITEM1 = 1;
    private final int TYPE_ITEM2 = 2;
    private LayoutInflater inflater;
    private List<FilmItem> filmItemsYears;

    @Override
    public int getItemViewType(int position) {
        int type = filmItemsYears.get(position).getType();
        if (type == 1) return TYPE_ITEM1;
        else return TYPE_ITEM2;
    }

    DataAdapter(Context context, List<FilmItem> films) {
        this.filmItemsYears = films;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ITEM1 :
                view = inflater.inflate(R.layout.list_item, parent, false);
                return new ViewHolder(view);
            case TYPE_ITEM2:
                view = inflater.inflate(R.layout.year_item, parent, false);
                return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        final FilmItem film = filmItemsYears.get(position);
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_ITEM1:
                holder.arrayPosition.setText(String.valueOf(film.getArrayPosition()));
                holder.ruNameView.setText(film.getRuName());
                holder.enNmeView.setText(film.getEnName());
                holder.ratingView.setText(film.getRank());
                holder.yearView.setText(film.getYear());
                break;
            case TYPE_ITEM2:
                holder.yearItemView.setText(film.getYear());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return filmItemsYears.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout listener;
        TextView ruNameView, enNmeView, ratingView, yearView, yearItemView, arrayPosition;

        ViewHolder(View view) {
            super(view);
            listener = (LinearLayout) view.findViewById(R.id.linearItemListener);
            ruNameView = (TextView) view.findViewById(R.id.ruName);
            enNmeView = (TextView) view.findViewById(R.id.enName);
            ratingView = (TextView) view.findViewById(R.id.rating);
            yearView = (TextView) view.findViewById(R.id.year);
            yearItemView = (TextView) view.findViewById(R.id.year_view);
            arrayPosition = (TextView) view.findViewById(R.id.arrayPosition);
        }
    }
}

