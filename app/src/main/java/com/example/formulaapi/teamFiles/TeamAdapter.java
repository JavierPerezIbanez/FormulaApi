package com.example.formulaapi.teamFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.R;

import java.util.Collections;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private List<Team> teams;
    private OnItemClickListener onItemClickListener;

    // Interfaz para el clic en un equipo
    public interface OnItemClickListener {
        void onItemClick(Team team);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public TeamAdapter(List<Team> teams) {
        this.teams = teams != null ? teams : Collections.emptyList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team team = teams.get(position);
        holder.name.setText(team.getTeamName());

        // Configurar el clic
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(team);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void updateTeams(List<Team> teams) {
        this.teams = teams != null ? teams : Collections.emptyList();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView number, name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teamName);
        }
    }
}


