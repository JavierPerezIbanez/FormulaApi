package com.example.formulaapi.driverFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.R;

import java.util.Collections;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    private List<Driver> drivers;
    private OnItemClickListener onItemClickListener;

    // Interfaz para el clic en un piloto
    public interface OnItemClickListener {
        void onItemClick(Driver driver);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public DriverAdapter(List<Driver> drivers) {
        this.drivers = drivers != null ? drivers : Collections.emptyList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Driver driver = drivers.get(position);
        holder.number.setText(String.valueOf(driver.getNumber() != 0 ? driver.getNumber() : "Sin número"));
        holder.name.setText(driver.getFullName());

        // Configurar el clic
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(driver);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    public void updateDrivers(List<Driver> drivers) {
        this.drivers = drivers != null ? drivers : Collections.emptyList();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView number, name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.driverNumber);
            name = itemView.findViewById(R.id.driverName);
        }
    }
}


