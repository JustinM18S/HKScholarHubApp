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

public class Admin_students extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_students);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Admin_students), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onAddClick(View view) {
    }
    // Method to handle back navigation
    public void goBackToAdminHomePage(View view) {
        Intent intent = new Intent(Admin_students.this, Admin_Home.class);
        startActivity(intent);
        finish();
    }
    // Method to handle navigation
    public void goBackToAdminAddStudents(View view) {
        Intent intent = new Intent(Admin_students.this, Admin_add_students.class);
        startActivity(intent);
        finish();
    }
}