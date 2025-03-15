package com.example.formulaapi.teamFiles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.formulaapi.ApiService;
import com.example.formulaapi.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeamDetailFragment extends Fragment {

    private static final String ARG_TEAM_ID = "team_id";
    private TeamRepository teamRepository;

    public static TeamDetailFragment newInstance(String teamId) {
        TeamDetailFragment fragment = new TeamDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEAM_ID, teamId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams_detail, container, false);

        String teamId = getArguments().getString(ARG_TEAM_ID);

        TextView nameTextView = view.findViewById(R.id.teamName);
        TextView teamNacionalityTextView = view.findViewById(R.id.teamNationality);
        TextView firstAppeareanceTextView = view.findViewById(R.id.firstAppeareance);
        TextView constructorsChampionshipsTextView = view.findViewById(R.id.constructorsChampionships);
        TextView driversChampionshipsTextView = view.findViewById(R.id.driversChampionships);
        TextView urlTextView = view.findViewById(R.id.teamUrl);

        // Configurar el repositorio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        teamRepository = TeamRepository.getInstance(apiService);

        // Obtener los datos del piloto
        teamRepository.getTeam(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(team -> {
                    // Mostrar los datos del piloto en la UI
                    nameTextView.setText(team.getTeamName());
                    teamNacionalityTextView.setText("Nacionalidad: " + team.getTeamNationality());
                    firstAppeareanceTextView.setText("Fecha de aparición: " + team.getFirstAppeareance());
                    constructorsChampionshipsTextView.setText("Campeonatos de constructores: "
                            + team.getConstructorsChampionships());
                    driversChampionshipsTextView.setText("Campeonatos de pilotos: "
                            + team.getDriversChampionships());
                    urlTextView.setText(team.getUrl());

                    // Configurar la URL como un enlace clickable
                    urlTextView.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(team.getUrl()));
                        startActivity(intent);
                    });
                }, error -> {
                    // Manejo de errores
                });

        return view;
    }
}
