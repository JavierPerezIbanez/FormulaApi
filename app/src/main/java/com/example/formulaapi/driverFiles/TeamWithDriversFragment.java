package com.example.formulaapi.driverFiles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.ApiService;
import com.example.formulaapi.R;
import com.example.formulaapi.teamFiles.TeamRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeamWithDriversFragment extends Fragment {

    private static final String ARG_TEAM_ID = "team_id";
    private TeamRepository teamRepository;
    private DriverRepository driverRepository;
    private DriverAdapter driverAdapter;

    public static TeamWithDriversFragment newInstance(String teamId) {
        TeamWithDriversFragment fragment = new TeamWithDriversFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEAM_ID, teamId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_with_drivers, container, false);

        // Recuperar el ID del equipo
        String teamId = getArguments().getString(ARG_TEAM_ID);

        // Configuración de UI
        TextView nameTextView = view.findViewById(R.id.teamName);
        TextView teamNationalityTextView = view.findViewById(R.id.teamNationality);
        TextView firstAppearanceTextView = view.findViewById(R.id.firstAppearance);
        TextView constructorsChampionshipsTextView = view.findViewById(R.id.constructorsChampionships);
        TextView driversChampionshipsTextView = view.findViewById(R.id.driversChampionships);
        TextView urlTextView = view.findViewById(R.id.teamUrl);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewDrivers);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        driverAdapter = new DriverAdapter(new ArrayList<>());
        recyclerView.setAdapter(driverAdapter);

        initRepositories();

        // Cargar datos del equipo
        loadTeamData(teamId, nameTextView, teamNationalityTextView, firstAppearanceTextView,
                constructorsChampionshipsTextView, driversChampionshipsTextView, urlTextView);

        // Cargar pilotos
        loadDrivers(teamId);

        // Configurar clic en un piloto
        driverAdapter.setOnItemClickListener(driver -> {
            replaceFragment(DriverDetailFragment.newInstance(driver.getId()));
        });

        return view;
    }

    private void initRepositories() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        teamRepository = TeamRepository.getInstance(apiService);
        driverRepository = DriverRepository.getInstance(apiService);
    }

    private void loadTeamData(String teamId, TextView nameTextView, TextView teamNationalityTextView,
                              TextView firstAppearanceTextView, TextView constructorsChampionshipsTextView,
                              TextView driversChampionshipsTextView, TextView urlTextView) {
        teamRepository.getTeam(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(team -> {
                    nameTextView.setText(team.getTeamName());
                    teamNationalityTextView.setText("Nacionalidad: " + team.getTeamNationality());
                    firstAppearanceTextView.setText("Primera aparición: " + team.getFirstAppeareance());
                    constructorsChampionshipsTextView.setText("Títulos de constructores: " + team.getConstructorsChampionships());
                    driversChampionshipsTextView.setText("Títulos de pilotos: " + team.getDriversChampionships());
                    urlTextView.setText(team.getUrl());

                    // Hacer el enlace clicable
                    urlTextView.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(team.getUrl()));
                        startActivity(intent);
                    });
                }, error -> {
                    Toast.makeText(getContext(), "Error al cargar datos del equipo", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadDrivers(String teamId) {
        driverRepository.getTeamDrivers2025(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(drivers -> {
                    driverAdapter.updateDrivers(drivers); // Actualizar el adaptador con los pilotos
                }, error -> {
                    Toast.makeText(getContext(), "Error al cargar pilotos", Toast.LENGTH_SHORT).show();
                });
    }




    private void replaceFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
