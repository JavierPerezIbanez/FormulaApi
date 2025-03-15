package com.example.formulaapi.seasonFiles;

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

public class SeasonDetailFragment extends Fragment {

    private static final String ARG_DRIVER_ID = "season_id";
    private SeasonRepository seasonRepository;

    public static SeasonDetailFragment newInstance(String seasonId) {
        SeasonDetailFragment fragment = new SeasonDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DRIVER_ID, seasonId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seasons_detail, container, false);

        String seasonId = getArguments().getString(ARG_DRIVER_ID);

        TextView nameTextView = view.findViewById(R.id.championshipName);
        TextView yearTextView = view.findViewById(R.id.year);
        TextView urlTextView = view.findViewById(R.id.seasonUrl);

        // Configurar el repositorio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        seasonRepository = SeasonRepository.getInstance(apiService);

        // Obtener los datos de la temporada
        seasonRepository.getSeason(seasonId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(season -> {
                    // Mostrar los datos de la temporada en la UI
                    nameTextView.setText(season.getName());
                    yearTextView.setText("Año: " + season.getYear());
                    urlTextView.setText(season.getUrl());

                    // Configurar la URL como un enlace clickable
                    urlTextView.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(season.getUrl()));
                        startActivity(intent);
                    });
                }, error -> {
                    // Manejo de errores
                });

        return view;
    }
}
