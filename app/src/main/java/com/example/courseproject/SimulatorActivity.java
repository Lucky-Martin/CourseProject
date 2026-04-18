package com.example.courseproject;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import com.example.courseproject.model.CrewMember;
import com.example.courseproject.model.Location;
import com.example.courseproject.model.Storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimulatorActivity extends AppCompatActivity {
    private final Storage storage = Storage.getInstance();

    private CrewSelectAdapter adapter;
    private final Set<Integer> selectedCrewIds = new HashSet<>();
    private TextView crewInSimulatorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_simulator);

        MaterialButton backButton = findViewById(R.id.backButton);
        MaterialButton buttonTrainSelected = findViewById(R.id.buttonTrainSelected);
        MaterialButton buttonMoveToQuarters = findViewById(R.id.buttonMoveToQuarters);
        crewInSimulatorText = findViewById(R.id.crewInSimulatorText);

        RecyclerView recyclerView = findViewById(R.id.recyclerSimulator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CrewSelectAdapter(selectedCrewIds, 0);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

        buttonTrainSelected.setOnClickListener(v -> {
            List<Integer> selection = new ArrayList<>(selectedCrewIds);
            if (selection.isEmpty()) {
                Toast.makeText(this, "Select at least one crew member to train.", Toast.LENGTH_SHORT).show();
                return;
            }

            // One training awards xp to all
            storage.incrementTrainingSessions();
            for (int crewId : selection) {
                CrewMember crew = storage.getCrewMember(crewId);
                if (crew != null && crew.isAlive()) {
                    crew.addExperience(1);
                }
            }

            adapter.clearSelection();
            refresh();
        });

        buttonMoveToQuarters.setOnClickListener(v -> {
            List<Integer> selection = new ArrayList<>(selectedCrewIds);
            if (selection.isEmpty()) {
                Toast.makeText(this, "Select at least one crew member.", Toast.LENGTH_SHORT).show();
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
        int count = storage.countCrewByLocation(Location.SIMULATOR);
        crewInSimulatorText.setText("Crew in Simulator (" + count + ")");
        adapter.setItems(storage.listCrewByLocation(Location.SIMULATOR));
    }
}

