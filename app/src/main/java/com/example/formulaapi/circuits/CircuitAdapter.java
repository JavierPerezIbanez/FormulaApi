package com.example.formulaapi.circuits;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.R;

import java.util.List;

public class CircuitAdapter extends RecyclerView.Adapter<CircuitAdapter.ViewHolder> {
    private List<Circuit> circuits;

    public CircuitAdapter(List<Circuit> circuits) {
        this.circuits = circuits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circuit_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Circuit circuit = circuits.get(position);
        holder.name.setText(circuit.getCircuitName());
        holder.location.setText(circuit.getCity() + ", " + circuit.getCountry());
    }

    @Override
    public int getItemCount() {
        return circuits.size();
    }

    public void addCircuits(List<Circuit> newCircuits) {
        int startPosition = circuits.size();
        circuits.addAll(newCircuits);
        notifyItemRangeInserted(startPosition, newCircuits.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView location;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.circuitName);
            location = itemView.findViewById(R.id.circuitLocation);
        }
    }
}
