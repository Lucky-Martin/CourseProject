package com.example.courseproject;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.example.courseproject.model.Location;
import com.example.courseproject.model.Storage;

public class MainActivity extends AppCompatActivity {
    private TextView quartersCountText;
    private TextView simulatorCountText;
    private TextView missionControlCountText;

    private final Storage storage = Storage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        quartersCountText = findViewById(R.id.quartersCountText);
        simulatorCountText = findViewById(R.id.simulatorCountText);
        missionControlCountText = findViewById(R.id.missionControlCountText);

        MaterialButton buttonRecruit = findViewById(R.id.buttonRecruit);
        MaterialButton buttonQuarters = findViewById(R.id.buttonQuarters);
        MaterialButton buttonSimulator = findViewById(R.id.buttonSimulator);
        MaterialButton buttonMissionControl = findViewById(R.id.buttonMissionControl);
        MaterialButton buttonStatistics = findViewById(R.id.buttonStatistics);

        refreshCounts();

        buttonRecruit.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RecruitActivity.class)));
        buttonQuarters.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, QuartersActivity.class)));
        buttonSimulator.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SimulatorActivity.class)));
        buttonMissionControl.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MissionControlActivity.class)));
        buttonStatistics.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StatisticsActivity.class)));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCounts();
    }

    private void refreshCounts() {
        quartersCountText.setText("Crew in Quarters: " + storage.countCrewByLocation(Location.QUARTERS));
        simulatorCountText.setText("Crew in Simulator: " + storage.countCrewByLocation(Location.SIMULATOR));
        missionControlCountText.setText("Crew in Mission Control: " + storage.countCrewByLocation(Location.MISSION_CONTROL));
    }
}