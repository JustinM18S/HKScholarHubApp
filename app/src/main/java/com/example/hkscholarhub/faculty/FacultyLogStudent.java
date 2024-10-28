package com.example.hkscholarhub.faculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hkscholarhub.R;

public class FacultyLogStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_log_student);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FacultyLogStudent), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Handle student list item clicks
        LinearLayout student1 = findViewById(R.id.studentLayout1);  // First student
        student1.setOnClickListener(v -> {
            // Navigate to a new page for Student 1
            Intent intent = new Intent(FacultyLogStudent.this, FacultyAddEntry.class);
            intent.putExtra("studentName", "Angela De Vera"); // Pass data if needed
            startActivity(intent);
        });

        // Repeat for other students
        LinearLayout student2 = findViewById(R.id.studentLayout2);
        student2.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyLogStudent.this, FacultyAddEntry.class);
            intent.putExtra("studentName", "Justin Kurt Munar");
            startActivity(intent);
        });

        LinearLayout student3 = findViewById(R.id.studentLayout3);
        student3.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyLogStudent.this, FacultyAddEntry.class);
            intent.putExtra("studentName", "Krysta Orallo");
            startActivity(intent);
        });
        LinearLayout student4 = findViewById(R.id.studentLayout4);
        student4.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyLogStudent.this, FacultyAddEntry.class);
            intent.putExtra("studentName", "Kim Philip");
            startActivity(intent);
        });
        LinearLayout student5 = findViewById(R.id.studentLayout5);
        student5.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyLogStudent.this, FacultyAddEntry.class);
            intent.putExtra("studentName", "Kurt Aguilar");
            startActivity(intent);
        });
    }
    


    // Method to handle back navigation
    public void goBackToHomePage(View view) {
        Intent intent = new Intent(FacultyLogStudent.this, FacultyHomePage.class);
        startActivity(intent);
        finish();
    }

}
