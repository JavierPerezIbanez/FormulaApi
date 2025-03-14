package com.example.formulaapi.teamsAndDrivers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.R;

import java.util.Collections;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    private List<Driver> drivers;

    public DriverAdapter(List<Driver> drivers) {
        this.drivers = drivers != null ? drivers : Collections.emptyList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Driver driver = drivers.get(position);
        holder.name.setText(driver.getFullName());
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
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.driverName);
        }
    }
}
