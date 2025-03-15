package com.example.formulaapi.circuitFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.R;

import java.util.Collections;
import java.util.List;

public class CircuitAdapter extends RecyclerView.Adapter<CircuitAdapter.ViewHolder> {
    private List<Circuit> circuits;
    private OnItemClickListener onItemClickListener;

    // Interfaz para el clic en un circuito
    public interface OnItemClickListener {
        void onItemClick(Circuit circui);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public CircuitAdapter(List<Circuit> circuits) {
        this.circuits = circuits != null ? circuits : Collections.emptyList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circuit_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Circuit circuit = circuits.get(position);
        holder.name.setText(circuit.getCircuitName());
        holder.location.setText(circuit.getCity()+", "+circuit.getCountry());

        // Configurar el clic
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(circuit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return circuits.size();
    }

    public void updateCircuits(List<Circuit> circuits) {
        this.circuits = circuits != null ? circuits : Collections.emptyList();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.circuitName);
            location = itemView.findViewById(R.id.circuitLocation);
        }
    }
}


