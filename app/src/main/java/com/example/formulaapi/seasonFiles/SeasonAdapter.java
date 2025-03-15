package com.example.formulaapi.seasonFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.R;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {
    private List<Season> seasons;


    public SeasonAdapter(List<Season> seasons){
        this.seasons = seasons;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_row, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Season season = seasons.get(position);
        holder.name.setText(season.getName());
        holder.year.setText(String.valueOf(season.getYear()));

    }
    public void addSeasons(List<Season> newSeasons){
        int starPosition = seasons.size();
        seasons.addAll(newSeasons);
        notifyItemRangeInserted(starPosition, newSeasons.size());
    }

    @Override
    public int getItemCount(){
        return seasons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView year;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.seasonName);
            year = itemView.findViewById(R.id.seasonYear);
        }
    }
}
