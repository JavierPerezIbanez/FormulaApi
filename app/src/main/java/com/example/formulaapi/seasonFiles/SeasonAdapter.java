package com.example.formulaapi.seasonFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.R;

import java.util.Collections;
import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {
    private List<Season> seasons;
    private OnItemClickListener onItemClickListener;

    // Interfaz para el clic en una temporada
    public interface OnItemClickListener {
        void onItemClick(Season season);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public SeasonAdapter(List<Season> seasons) {
        this.seasons = seasons != null ? seasons : Collections.emptyList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Season season = seasons.get(position);
        holder.year.setText(season.getYear());

        // Configurar el clic
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(season);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }

    public void updateSeasons(List<Season> seasons) {
        System.out.println("Actualizando adaptador con " + seasons.size() + " equipos.");
        this.seasons = seasons != null ? seasons : Collections.emptyList();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView year;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.championshipYear);
        }
    }
}


