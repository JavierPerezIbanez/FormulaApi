package com.example.formulaapi.driverFiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formulaapi.ApiService;
import com.example.formulaapi.R;
import com.example.formulaapi.teamFiles.TeamAdapter;
import com.example.formulaapi.teamFiles.TeamRepository;
import com.example.formulaapi.teamFiles.TeamsFragment;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeasonDriversFragment extends Fragment {

    private TeamRepository teamRepository;
    private TeamAdapter teamAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_season_drivers, container, false);

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewTeams);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        teamAdapter = new TeamAdapter(new ArrayList<>());
        recyclerView.setAdapter(teamAdapter);

        // Inicializar repositorio
        initRepository();

        // Cargar equipos de la temporada actual
        loadTeams2025();

        // Configurar botones
        Button buttonAllTeams = view.findViewById(R.id.buttonAllTeams);
        Button buttonAllDrivers = view.findViewById(R.id.buttonAllDrivers);

        buttonAllTeams.setOnClickListener(v -> {
            // Navegar a TeamsFragment
            replaceFragment(new TeamsFragment());
        });

        buttonAllDrivers.setOnClickListener(v -> {
            // Navegar a DriversFragment
            replaceFragment(new DriversFragment());
        });

        return view;
    }

    private void initRepository() {
        // Configurar Retrofit y el repositorio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        teamRepository = TeamRepository.getInstance(apiService);
    }

    private void loadTeams2025() {
        // Consultar API para obtener los equipos de la temporada actual
        teamRepository.getTeams2025()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(teams -> {
                    // Actualizar el adaptador con los equipos obtenidos
                    teamAdapter.updateTeams(teams);
                }, throwable -> {
                    // Mostrar error en caso de fallo
                    Toast.makeText(getContext(), "Error al cargar los equipos", Toast.LENGTH_SHORT).show();
                });
    }

    private void replaceFragment(Fragment fragment) {
        // Reemplazar el fragmento actual por el seleccionado
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
