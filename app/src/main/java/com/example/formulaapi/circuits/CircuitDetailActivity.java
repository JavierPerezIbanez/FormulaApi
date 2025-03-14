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

        TextView nameTextView = findViewById(R.id.circuitName);
        TextView locationTextView = findViewById(R.id.circuitLocation);
        TextView lengthTextView = findViewById(R.id.circuitLength);
        TextView lapRecordTextView = findViewById(R.id.lapRecord);
        TextView participationYearTextView = findViewById(R.id.firstParticipationYear);
        TextView numberOfCornersTextView = findViewById(R.id.numberOfCorners);
        TextView fastestLapDriverTextView = findViewById(R.id.fastestLapDriver);
        TextView fastestLapTeamTextView = findViewById(R.id.fastestLapTeam);
        TextView fastestLapYearTextView = findViewById(R.id.fastestLapYear);
        TextView urlTextView = findViewById(R.id.url);



        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String location = "Ubicacion: " + intent.getStringExtra("location");
        String length = "Longitud(m): " + String.valueOf(intent.getIntExtra("length", 0));
        String lapRecord = "Record de vuelta: " + intent.getStringExtra("lapRecord");
        String firstParticipationYear = "Año de primera participación: " + String.valueOf(intent.getIntExtra("firstParticipationYear", 0));
        String numberOfCorners = "Número de curvas: " + String.valueOf(intent.getIntExtra("numberOfCorners", 0));
        String fastestLapDriverId = "Piloto con el record de vuelta: "  + intent.getStringExtra("fastestLapDriverId");
        String fastestLapTeamId = "Equipo con el record de vuelta: " + intent.getStringExtra("fastestLapTeamId");
        String fastestLapYear = "Año del record de vuelta: " + String.valueOf(intent.getIntExtra("fastestLapYear", 0));
        String url = "Url: " + intent.getStringExtra("url");


        nameTextView.setText(name);
        locationTextView.setText(location);
        lengthTextView.setText(length);
        lapRecordTextView.setText(lapRecord);
        participationYearTextView.setText(firstParticipationYear);
        numberOfCornersTextView.setText(numberOfCorners);
        fastestLapDriverTextView.setText(fastestLapDriverId);
        fastestLapTeamTextView.setText(fastestLapTeamId);
        fastestLapYearTextView.setText(fastestLapYear);
        urlTextView.setText(url);
    }
}
