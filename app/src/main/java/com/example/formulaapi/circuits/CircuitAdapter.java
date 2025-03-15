package com.example.formulaapi.circuits;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.ApiService;
import com.example.formulaapi.R;
import com.example.formulaapi.teamsAndDrivers.Driver;
import com.example.formulaapi.teamsAndDrivers.DriverAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Proporciona la conexión entre Circuit y RecycleView para que se puedan mostrar los datos
 */
public class CircuitAdapter extends RecyclerView.Adapter<CircuitAdapter.ViewHolder> {
    private List<Circuit> circuits;
    private Context context;
    private ApiService apiService;
    private final HashMap<String, Driver> driverCache = new HashMap<>();


    public CircuitAdapter(List<Circuit> circuits, Context context) {
        this.circuits = circuits;
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
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

        DriverAdapter driverAdapter = new DriverAdapter(new ArrayList<>());
        holder.driversRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.driversRecyclerView.setAdapter(driverAdapter);

        String driverId = circuit.getFastestLapDriverId();

        if (driverCache.containsKey(driverId)) {
            // Si el piloto ya está en la cache, usamos los datos
            Driver driver = driverCache.get(driverId);
            setupClickListener(holder, circuit, driver);
        } else {
            // Si el piloto no está en la cache, se hace la llamada a la API
            apiService.getDriver(driverId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            driversResponse -> {
                                if (driversResponse != null && driversResponse.getDrivers() != null && !driversResponse.getDrivers().isEmpty()) {
                                    Driver driver = driversResponse.getDrivers().get(0);
                                    driverCache.put(driverId, driver);
                                    setupClickListener(holder, circuit, driver);
                                } else {
                                    setupClickListener(holder, circuit, null);
                                }
                            },
                            error -> {
                                Log.e("API_ERROR", "Error al obtener los datos del piloto", error);
                                setupClickListener(holder, circuit, null);
                            }
                    );



        }
    }

    private void setupClickListener(ViewHolder holder, Circuit circuit, @Nullable Driver driver) {
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CircuitDetailActivity.class);
            intent.putExtra("name", circuit.getCircuitName());
            intent.putExtra("location", circuit.getCity() + ", " + circuit.getCountry());
            intent.putExtra("length", circuit.getCircuitLength());
            intent.putExtra("lapRecord", circuit.getLapRecord());
            intent.putExtra("firstParticipationYear", circuit.getFirstParticipationYear());
            intent.putExtra("numberOfCorners", circuit.getNumberOfCorners());
            intent.putExtra("fastestLapTeamId", circuit.getFastestLapTeamId());
            intent.putExtra("fastestLapYear", circuit.getFastestLapYear());
            intent.putExtra("url", circuit.getUrl());

            // Añade los datos del piloto si están disponibles
            if (driver != null) {
                intent.putExtra("fastestLapDriverName", driver.getFullName());
                intent.putExtra("fastestLapDriverNumber", driver.getNumber());
            } else {
                intent.putExtra("fastestLapDriverName", "No disponible");
                intent.putExtra("fastestLapDriverNumber", "No disponible");
            }

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
        RecyclerView driversRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.circuitName);
            location = itemView.findViewById(R.id.circuitLocation);
            driversRecyclerView = itemView.findViewById(R.id.driversRecyclerView);
        }
    }

    private void loadDrivers(String driverId, DriverAdapter driverAdapter) {
        apiService.getDriver(driverId)
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
