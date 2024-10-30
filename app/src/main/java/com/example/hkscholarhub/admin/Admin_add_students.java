package com.example.hkscholarhub.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hkscholarhub.R;

public class Admin_add_students extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_students);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Admin_add_students), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the Assigned To Spinner
        Spinner spinnerAssignedTo = findViewById(R.id.spinnerAssignedTo);
        String[] assignedToOptions = {"Juan Dela Cruz", "Segunda Katigbak", "Angela Devera"};
        ArrayAdapter<String> assignedToAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assignedToOptions);
        assignedToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAssignedTo.setAdapter(assignedToAdapter);

        spinnerAssignedTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selected item here
                String selectedItem = parent.getItemAtPosition(position).toString();
                // You can perform any action with the selected item here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where no item is selected if needed
            }
        });

        // Set up the HK Duty Type Spinner
        Spinner spinnerHKDutyType = findViewById(R.id.spinnerHKDutyType);
        String[] hkDutyTypeOptions = {"HK 25", "HK 50", "HK 75"};
        ArrayAdapter<String> hkDutyTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hkDutyTypeOptions);
        hkDutyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHKDutyType.setAdapter(hkDutyTypeAdapter);

        spinnerHKDutyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selected item here
                String selectedItem = parent.getItemAtPosition(position).toString();
                // You can perform any action with the selected item here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where no item is selected if needed
            }
        });
    }
    // Method to handle navigation
    public void goBackToAdminStudents(View view) {
        Intent intent = new Intent(Admin_add_students.this, Admin_students.class);
        startActivity(intent);
        finish();
    }
}
