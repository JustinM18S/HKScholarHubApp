package com.example.hkscholarhub.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hkscholarhub.R;
import com.example.hkscholarhub.faculty.FacultyAddEntry;
import com.example.hkscholarhub.faculty.FacultyLogStudent;

public class Admin_add_faculty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_faculty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admin_add_faculty), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Method to handle back navigation
    public void goBackToAdminFaculty(View view) {
        Intent intent = new Intent(Admin_add_faculty.this, Admin_faculty.class);
        startActivity(intent);
        finish();
    }
}