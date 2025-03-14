package com.example.formulaapi.teamsAndDrivers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.ApiService;
import com.example.formulaapi.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private List<Team> teams;
    private Context context;
    private ApiService apiService;

    public TeamAdapter(Context context, List<Team> teams) {
        this.context = context;
        this.teams = teams;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Team team = teams.get(position);
        holder.name.setText(team.getTeamName());

        // Configura el RecyclerView de pilotos
        DriverAdapter driverAdapter = new DriverAdapter(new ArrayList<>());
        holder.driversRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.driversRecyclerView.setAdapter(driverAdapter);

        // Cargar los pilotos
        loadDrivers(team.getTeamId(), driverAdapter);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView driversRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            driversRecyclerView = itemView.findViewById(R.id.driversRecyclerView);
        }
    }

    private void loadDrivers(String teamId, DriverAdapter driverAdapter) {
        apiService.getDriversList(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        driversResponse -> {
                            List<Driver> drivers = driversResponse.getDrivers();
                            driverAdapter.updateDrivers(drivers);
                        },
                        error -> {
                            // Manejar el error
                        }
                );
    }
}
