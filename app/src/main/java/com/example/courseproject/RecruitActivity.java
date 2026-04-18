package com.example.courseproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import com.example.courseproject.model.Location;
import com.example.courseproject.model.Specialization;
import com.example.courseproject.model.Storage;

public class RecruitActivity extends AppCompatActivity {
    private final Storage storage = Storage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruit);

        MaterialButton backButton = findViewById(R.id.backButton);
        MaterialButton buttonCreateCrew = findViewById(R.id.buttonCreateCrew);
        MaterialButton buttonCancel = findViewById(R.id.buttonCancel);

        EditText editCrewBaseName = findViewById(R.id.editCrewBaseName);
        Spinner crewTypeSpinner = findViewById(R.id.crewTypeSpinner);

        String[] crewTypeLabels = new String[]{
                "Pilot (Skill 5, Res 4, Max Energy 20)",
                "Engineer (Skill 6, Res 3, Max Energy 19)",
                "Medic (Skill 7, Res 2, Max Energy 18)",
                "Scientist (Skill 8, Res 1, Max Energy 17)",
                "Soldier (Skill 9, Res 0, Max Energy 16)"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                crewTypeLabels
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crewTypeSpinner.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());
        buttonCancel.setOnClickListener(v -> finish());

        buttonCreateCrew.setOnClickListener(v -> {
            String baseName = editCrewBaseName.getText().toString().trim();
            if (baseName.isEmpty()) {
                Toast.makeText(this, "Please enter a crew name.", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedIndex = crewTypeSpinner.getSelectedItemPosition();
            Specialization specialization = getSpecializationFromIndex(selectedIndex);
            storage.addCrewMember(baseName, specialization, Location.QUARTERS);

            Toast.makeText(this, "Crew recruited: " + baseName + " (" + specialization + ")", Toast.LENGTH_SHORT).show();
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private Specialization getSpecializationFromIndex(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                return Specialization.PILOT;
            case 1:
                return Specialization.ENGINEER;
            case 2:
                return Specialization.MEDIC;
            case 3:
                return Specialization.SCIENTIST;
            case 4:
                return Specialization.SOLDIER;
            default:
                return Specialization.PILOT;
        }
    }
}

