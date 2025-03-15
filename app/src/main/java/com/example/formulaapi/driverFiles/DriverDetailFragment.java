package com.example.formulaapi.driverFiles;

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

public class DriverDetailFragment extends Fragment {

    private static final String ARG_DRIVER_ID = "driver_id";
    private DriverRepository driverRepository;

    public static DriverDetailFragment newInstance(String driverId) {
        DriverDetailFragment fragment = new DriverDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DRIVER_ID, driverId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_detail, container, false);

        String driverId = getArguments().getString(ARG_DRIVER_ID);

        TextView nameTextView = view.findViewById(R.id.driverName);
        TextView numberTextView = view.findViewById(R.id.driverNumber);
        TextView nationalityTextView = view.findViewById(R.id.driverNationality);
        TextView birthdayTextView = view.findViewById(R.id.driverBirthday);
        TextView shortNameTextView = view.findViewById(R.id.driverShortName);
        TextView urlTextView = view.findViewById(R.id.driverUrl);

        // Configurar el repositorio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f1api.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        driverRepository = DriverRepository.getInstance(apiService);

        // Obtener los datos del piloto
        driverRepository.getDriver(driverId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(driver -> {
                    // Mostrar los datos del piloto en la UI
                    nameTextView.setText(driver.getFullName());
                    numberTextView.setText(driver.getNumber() == 0 ? "Sin número" : "Número: " + driver.getNumber());
                    nationalityTextView.setText("Nacionalidad: " + driver.getNationality());
                    birthdayTextView.setText("Fecha de nacimiento: " + driver.getBirthday());
                    shortNameTextView.setText("Apodo: " + driver.getShortName());
                    urlTextView.setText(driver.getUrl());

                    // Configurar la URL como un enlace clickable
                    urlTextView.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(driver.getUrl()));
                        startActivity(intent);
                    });
                }, error -> {
                    // Manejo de errores
                });

        return view;
    }
}
