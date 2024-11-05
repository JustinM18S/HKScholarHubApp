package com.example.hkscholarhub.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hkscholarhub.R;
import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.FacultiesResponse;
import com.example.hkscholarhub.models.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Admin_faculty extends AppCompatActivity {

    private LinearLayout facultyContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_faculty);

        facultyContainer = findViewById(R.id.faculty_list_container);

        // Fetch faculty list
        fetchFacultyList();
    }

    private void fetchFacultyList() {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        APIService apiService = retrofit.create(APIService.class);

        Call<FacultiesResponse> call = apiService.getFaculties();
        call.enqueue(new Callback<FacultiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<FacultiesResponse> call, @NonNull Response<FacultiesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    facultyContainer.removeAllViews(); // Clear existing views before adding new ones
                    for (FacultiesResponse.Faculty faculty : response.body().getFaculties()) {
                        addFacultyToLayout(faculty);
                    }
                } else {
                    Toast.makeText(Admin_faculty.this, "Failed to fetch faculty list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FacultiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(Admin_faculty.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addFacultyToLayout(FacultiesResponse.Faculty faculty) {
        // Inflate user item layout
        View facultyView = LayoutInflater.from(this).inflate(R.layout.user_item_layout, facultyContainer, false);

        // Set the faculty details
        TextView facultyName = facultyView.findViewById(R.id.user_name);
        TextView facultyEmail = facultyView.findViewById(R.id.user_email);
        ImageView deleteUserIcon = facultyView.findViewById(R.id.delete_icon); // Assume there's a delete icon in user_item_layout

        facultyName.setText(faculty.getName());
        facultyEmail.setText(faculty.getEmail());

        // Add click listener to delete icon
        deleteUserIcon.setOnClickListener(v -> deleteUser(faculty.getId()));

        // Add the inflated view to the container
        facultyContainer.addView(facultyView);
    }

    private void deleteUser(long facultyId) {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        APIService apiService = retrofit.create(APIService.class);

        Call<Void> call = apiService.deleteUser(facultyId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Admin_faculty.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    fetchFacultyList(); // Refresh the list after deletion
                } else {
                    Toast.makeText(Admin_faculty.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(Admin_faculty.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to handle back navigation
    public void goBackToAdminHomePage(View view) {
        Intent intent = new Intent(Admin_faculty.this, Admin_Home.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to add faculty
    public void goToAdminAddFaculty(View view) {
        Intent intent = new Intent(Admin_faculty.this, Admin_add_faculty.class);
        startActivity(intent);
        finish();
    }
}
