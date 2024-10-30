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
import com.example.hkscholarhub.faculty.FacultyHomePage;
import com.example.hkscholarhub.faculty.FacultyProfile;

public class Admin_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AdminProfile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Method to handle back navigation
    public void goBackToAdminHome(View view) {
        Intent intent = new Intent(Admin_Profile.this, Admin_Home.class);
        startActivity(intent);
        finish();
    }
    public void exitApp(View view) {
        // This will close all the activities and exit the app
        finishAffinity();
    }
}