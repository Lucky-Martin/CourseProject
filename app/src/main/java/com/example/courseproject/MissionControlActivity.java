package com.example.courseproject;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.example.courseproject.model.Location;
import com.example.courseproject.model.MissionEngine;
import com.example.courseproject.model.MissionResult;
import com.example.courseproject.model.Storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MissionControlActivity extends AppCompatActivity {
    private final Storage storage = Storage.getInstance();
    private final MissionEngine missionEngine = new MissionEngine(storage);

    private CrewSelectAdapter adapter;
    private final Set<Integer> selectedCrewIds = new HashSet<>();

    private TextView missionLogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mission_control);

        MaterialButton backButton = findViewById(R.id.backButton);
        MaterialButton buttonLaunchMission = findViewById(R.id.buttonLaunchMission);
        MaterialButton buttonReturnSurvivorsToQuarters = findViewById(R.id.buttonReturnSurvivorsToQuarters);
        missionLogText = findViewById(R.id.missionLogText);

        RecyclerView recyclerView = findViewById(R.id.recyclerMissionControl);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CrewSelectAdapter(selectedCrewIds, 0);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

        buttonLaunchMission.setOnClickListener(v -> {
            List<Integer> selection = new ArrayList<>(selectedCrewIds);
            if (selection.isEmpty()) {
                Toast.makeText(this, "Select at least 1 crew member for the mission.", Toast.LENGTH_SHORT).show();
                return;
            }

            MissionResult result = missionEngine.launchMission(selection);
            missionLogText.setText(result.getLog());

            adapter.clearSelection();
            refresh();
        });

        buttonReturnSurvivorsToQuarters.setOnClickListener(v -> {
            List<Integer> selection = new ArrayList<>(selectedCrewIds);
            if (selection.isEmpty()) {
                Toast.makeText(this, "Select survivors to return to Quarters.", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int crewId : selection) {
                storage.moveCrewMember(crewId, Location.QUARTERS);
            }
            adapter.clearSelection();
            refresh();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        adapter.setItems(storage.listCrewByLocation(Location.MISSION_CONTROL));
    }
}

