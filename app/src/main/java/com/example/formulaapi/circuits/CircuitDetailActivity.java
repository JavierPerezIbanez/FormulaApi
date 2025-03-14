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
        nameTextView.setText(intent.getStringExtra("name"));
        locationTextView.setText(intent.getStringExtra("location"));
        lengthTextView.setText(String.valueOf(intent.getIntExtra("length", 0)));
        lapRecordTextView.setText(intent.getStringExtra("lapRecord"));
        participationYearTextView.setText(String.valueOf(intent.getIntExtra("firstParticipationYear", 0)));
        numberOfCornersTextView.setText(String.valueOf(intent.getIntExtra("numberOfCorners", 0)));
        fastestLapDriverTextView.setText(intent.getStringExtra("fastestLapDriverId"));
        fastestLapTeamTextView.setText(intent.getStringExtra("fastestLapTeamId"));
        fastestLapYearTextView.setText(String.valueOf(intent.getIntExtra("fastestLapYear", 0)));
        urlTextView.setText(intent.getStringExtra("url"));
    }
}
