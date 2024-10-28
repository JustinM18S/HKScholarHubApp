package com.example.hkscholarhub.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hkscholarhub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        // Adjust padding for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Admin_Home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // BottomNavigationView setup
        BottomNavigationView adminbottomNavigationView = findViewById(R.id.adminbottomNavigationView);

        adminbottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    // Navigate to the Home page (current page)
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    // Navigate to the Admin Profile page
                    startActivity(new Intent(Admin_Home.this, Admin_Profile.class));
                    return true;
                } else if (itemId == R.id.nav_dtr) {
                    // Show a popup for choosing between Student and Professor
                    showUserOptions();
                    return true;
                }
                return false;
            }
        });
    }

    // Method to show popup options for Users
    private void showUserOptions() {
        PopupMenu popupMenu = new PopupMenu(Admin_Home.this, findViewById(R.id.nav_dtr));
        popupMenu.getMenuInflater().inflate(R.menu.users_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int menuItemId = menuItem.getItemId();
                if (menuItemId == R.id.add_student) {
                    // Navigate to Add Student Page
                    startActivity(new Intent(Admin_Home.this, Admin_students.class));
                    return true;
                } else if (menuItemId == R.id.add_professor) {
                    // Navigate to Add Professor Page
                    startActivity(new Intent(Admin_Home.this, Admin_faculty.class));
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
