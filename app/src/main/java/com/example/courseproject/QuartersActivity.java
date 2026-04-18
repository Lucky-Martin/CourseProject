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

public class QuartersActivity extends AppCompatActivity {
    private final Storage storage = Storage.getInstance();

    private CrewSelectAdapter adapter;
    private final Set<Integer> selectedCrewIds = new HashSet<>();
    private TextView crewAtQuartersText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quarters);

        MaterialButton backButton = findViewById(R.id.backButton);
        MaterialButton buttonMoveToSimulator = findViewById(R.id.buttonMoveToSimulator);
        MaterialButton buttonMoveToMissionControl = findViewById(R.id.buttonMoveToMissionControl);
        crewAtQuartersText = findViewById(R.id.crewAtQuartersText);

        RecyclerView recyclerView = findViewById(R.id.recyclerQuarters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CrewSelectAdapter(selectedCrewIds, 0);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

        buttonMoveToSimulator.setOnClickListener(v -> {
            List<Integer> selection = new ArrayList<>(selectedCrewIds);
            if (selection.isEmpty()) {
                Toast.makeText(this, "Select at least one crew member.", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int crewId : selection) {
                storage.moveCrewMember(crewId, Location.SIMULATOR);
            }
            adapter.clearSelection();
            refresh();
        });

        buttonMoveToMissionControl.setOnClickListener(v -> {
            List<Integer> selection = new ArrayList<>(selectedCrewIds);
            if (selection.isEmpty()) {
                Toast.makeText(this, "Select at least one crew member.", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int crewId : selection) {
                storage.moveCrewMember(crewId, Location.MISSION_CONTROL);
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
        int count = storage.countCrewByLocation(Location.QUARTERS);
        crewAtQuartersText.setText("Crew at home (" + count + ")");
        List<CrewMember> quartersCrew = storage.listCrewByLocation(Location.QUARTERS);
        adapter.setItems(quartersCrew);
    }
}

