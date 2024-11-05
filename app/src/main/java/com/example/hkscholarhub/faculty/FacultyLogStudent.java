package com.example.hkscholarhub.faculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hkscholarhub.R;

public class FacultyLogStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_log_student);

        // Initialize the container where students will be displayed
        LinearLayout studentListContainer = findViewById(R.id.student_list);

        // Initialize individual student layouts
        LinearLayout studentLayout1 = findViewById(R.id.studentLayout1);
        LinearLayout studentLayout2 = findViewById(R.id.studentLayout2);
        LinearLayout studentLayout3 = findViewById(R.id.studentLayout3);
        LinearLayout studentLayout4 = findViewById(R.id.studentLayout4);
        LinearLayout studentLayout5 = findViewById(R.id.studentLayout5);

        // Set click listeners for each student layout
        studentLayout1.setOnClickListener(view -> openFacultyAddEntry());
        studentLayout2.setOnClickListener(view -> openFacultyAddEntry());
        studentLayout3.setOnClickListener(view -> openFacultyAddEntry());
        studentLayout4.setOnClickListener(view -> openFacultyAddEntry());
        studentLayout5.setOnClickListener(view -> openFacultyAddEntry());
    }

    // Method to open the FacultyAddEntry activity
    private void openFacultyAddEntry() {
        Intent intent = new Intent(FacultyLogStudent.this, FacultyAddEntry.class);
        startActivity(intent);
    }

    // Method to handle back navigation
    public void goBackToHomePage(View view) {
        Intent intent = new Intent(FacultyLogStudent.this, FacultyHomePage.class);
        startActivity(intent);
        finish();
    }
}
