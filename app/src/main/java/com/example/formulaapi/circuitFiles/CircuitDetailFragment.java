package com.example.formulaapi.circuitFiles;

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

import org.w3c.dom.Text;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CircuitDetailFragment extends Fragment {

    private static final String ARG_CIRCUIT_ID = "circuit_id";
    private CircuitRepository circuitRepository;

    public static CircuitDetailFragment newInstance(String circuitId) {
        CircuitDetailFragment fragment = new CircuitDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CIRCUIT_ID, circuitId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circuits_detail, container, false);

        String circuitId = getArguments().getString(ARG_CIRCUIT_ID);

        TextView nameTextView = view.findViewById(R.id.circuitName);
        TextView locationTextView = view.findViewById(R.id.circuitLocation);
        TextView lengthTextView = view.findViewById(R.id.circuitLength);
        TextView recordTextView = view.findViewById(R.id.circuitRecord);
        TextView firstYearTextView = view.findViewById(R.id.circuitFirstYear);
        TextView numberCornersTextView = view.findViewById(R.id.circuitNumberCorners);
        TextView fastestDriverIdTextView = view.findViewById(R.id.circuitFastestDriverId);
        TextView fastestTeamIdTextView = view.findViewById(R.id.circuitFastestTeamId);
        TextView fastestLapYearTextView = view.findViewById(R.id.circuitFastestLapYear);
        TextView urlTextView = view.findViewById(R.id.circuitUrl);

        // Configurar el repositorio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        circuitRepository = CircuitRepository.getInstance(apiService);

        // Obtener los datos del circuito
        circuitRepository.getCircuit(circuitId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(circuit -> {
                    // Mostrar los datos del circuito en la UI
                    nameTextView.setText(circuit.getCircuitName());
                    locationTextView.setText(circuit.getCity() + ", " + circuit.getCountry());
                    lengthTextView.setText("Longitud: " + circuit.getCircuitLength());
                    recordTextView.setText("Record: " + circuit.getLapRecord());
                    firstYearTextView.setText("Año de inicio: " + circuit.getFirstParticipationYear());
                    numberCornersTextView.setText("Número de CORNER: " + circuit.getNumberOfCorners());
                    fastestDriverIdTextView.setText("Piloto más rápido: " + circuit.getFastestLapDriverId());
                    fastestTeamIdTextView.setText("Equipo más rápido: " + circuit.getFastestLapTeamId());
                    fastestLapYearTextView.setText("Año del mejor vuelta: " + circuit.getFastestLapYear());
                    urlTextView.setText(circuit.getUrl());

                    // Configurar la URL como un enlace clickable
                    urlTextView.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(circuit.getUrl()));
                        startActivity(intent);
                    });
                }, error -> {
                    // Manejo de errores
                });

        return view;
    }
}
