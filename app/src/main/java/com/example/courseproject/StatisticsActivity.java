package com.example.courseproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import android.widget.TextView;

import com.example.courseproject.model.Location;
import com.example.courseproject.model.Specialization;
import com.example.courseproject.model.Storage;

public class StatisticsActivity extends AppCompatActivity {
    private final Storage storage = Storage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        MaterialButton backButton = findViewById(R.id.backButton);
        TextView statsTotalsText = findViewById(R.id.statsTotalsText);
        TextView crewStatsText = findViewById(R.id.crewStatsText);

        backButton.setOnClickListener(v -> finish());

        statsTotalsText.setText(
                "Completed missions: " + storage.getCompletedMissions()
                        + "\nTotal crew recruited: " + storage.getTotalCrewRecruited()
                        + "\nTraining sessions: " + storage.getTrainingSessions()
        );

        crewStatsText.setText(
                "- Pilot: " + storage.countCrewBySpecialization(Specialization.PILOT) + " in colony\n"
                        + "- Engineer: " + storage.countCrewBySpecialization(Specialization.ENGINEER) + " in colony\n"
                        + "- Medic: " + storage.countCrewBySpecialization(Specialization.MEDIC) + " in colony\n"
                        + "- Scientist: " + storage.countCrewBySpecialization(Specialization.SCIENTIST) + " in colony\n"
                        + "- Soldier: " + storage.countCrewBySpecialization(Specialization.SOLDIER) + " in colony"
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

