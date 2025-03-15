package com.example.formulaapi.circuits;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.formulaapi.R;

public class CircuitDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuit_detail);

        // Inicializar las vistas
        TextView nameTextView = findViewById(R.id.circuitName);
        TextView locationTextView = findViewById(R.id.circuitLocation);
        TextView lengthTextView = findViewById(R.id.circuitLength);
        TextView cornersTextView = findViewById(R.id.numberOfCorners);
        TextView participationYearTextView = findViewById(R.id.firstParticipationYear);
        TextView urlTextView = findViewById(R.id.url);
        TextView lapRecordTextView = findViewById(R.id.lapRecord);
        TextView fastestLapDriverTextView = findViewById(R.id.fastestLapDriver);
        TextView fastestLapYearTextView = findViewById(R.id.fastestLapYear);
        TextView fastestLapTeamTextView = findViewById(R.id.fastestLapTeam);

        // Obtener datos del Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");
        String length = "Longitud: " + intent.getIntExtra("length", 0) + "m";
        String corners = "Número de curvas: " + intent.getIntExtra("numberOfCorners", 0);
        String firstParticipationYear = "Año de primera participación: " + intent.getIntExtra("firstParticipationYear", 0);
        String url = intent.getStringExtra("url");
        String lapRecord = "Récord de vuelta: " + intent.getStringExtra("lapRecord");
        String fastestLapDriver = "Piloto con récord: " + intent.getStringExtra("fastestLapDriverName");
        String fastestLapYear = "Año del récord: " + intent.getIntExtra("fastestLapYear", 0);
        String fastestLapTeam = "Equipo con récord: " + intent.getStringExtra("fastestLapTeamId");

        // Configurar las vistas
        nameTextView.setText(name);
        locationTextView.setText(location);
        lengthTextView.setText(length);
        cornersTextView.setText(corners);
        participationYearTextView.setText(firstParticipationYear);
        urlTextView.setText(url);
        lapRecordTextView.setText(lapRecord);
        fastestLapDriverTextView.setText(fastestLapDriver);
        fastestLapYearTextView.setText(fastestLapYear);
        fastestLapTeamTextView.setText(fastestLapTeam);
    }


}
