package com.example.formulaapi.circuits;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.R;

import java.util.List;

/**
 * Proporciona la conexión entre Circuit y RecycleView para que se puedan mostrar los datos
 */
public class CircuitAdapter extends RecyclerView.Adapter<CircuitAdapter.ViewHolder> {
    private List<Circuit> circuits;
    private Context context;

    public CircuitAdapter(List<Circuit> circuits, Context context) {
        this.circuits = circuits;
        this.context = context;
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

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CircuitDetailActivity.class);
            intent.putExtra("name", circuit.getCircuitName());
            intent.putExtra("location", circuit.getCity() + ", " + circuit.getCountry());
            intent.putExtra("length", circuit.getCircuitLength());
            intent.putExtra("lapRecord", circuit.getLapRecord());
            intent.putExtra("firstParticipationYear", circuit.getFirstParticipationYear());
            intent.putExtra("numberOfCorners", circuit.getNumberOfCorners());
            intent.putExtra("fastestLapDriverId", circuit.getFastestLapDriverId());
            intent.putExtra("fastestLapTeamId", circuit.getFastestLapTeamId());
            intent.putExtra("fastestLapYear", circuit.getFastestLapYear());
            intent.putExtra("url", circuit.getUrl());
            context.startActivity(intent);
        });
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
