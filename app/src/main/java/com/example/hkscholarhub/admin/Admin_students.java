package com.example.hkscholarhub.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hkscholarhub.R;
import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.RetrofitClient;
import com.example.hkscholarhub.models.StudentsResponse;
import com.example.hkscholarhub.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

public class Admin_students extends AppCompatActivity {

    private LinearLayout userContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students);

        userContainer = findViewById(R.id.user_list_container);

        // Fetch and display students
        fetchStudents();
    }

    private void fetchStudents() {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        APIService apiService = retrofit.create(APIService.class);

        Call<StudentsResponse> call = apiService.getStudents();
        call.enqueue(new Callback<StudentsResponse>() {
            @Override
            public void onResponse(@NonNull Call<StudentsResponse> call, @NonNull Response<StudentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> students = response.body().getStudents();
                    for (User student : students) {
                        addUserToLayout(student);
                    }
                } else {
                    Toast.makeText(Admin_students.this, "Failed to fetch students", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StudentsResponse> call, @NonNull Throwable t) {
                Toast.makeText(Admin_students.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserToLayout(User user) {
        // Inflate user item layout
        View userView = LayoutInflater.from(this).inflate(R.layout.user_item_layout, userContainer, false);

        // Set the user details
        TextView userName = userView.findViewById(R.id.user_name);
        TextView userEmail = userView.findViewById(R.id.user_email);

        userName.setText(user.getName());
        userEmail.setText(user.getEmail());

        // Add the inflated view to the container
        userContainer.addView(userView);
    }

    // Method to handle back navigation
    public void goBackToAdminHomePage(View view) {
        Intent intent = new Intent(Admin_students.this, Admin_Home.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to add students
    public void goBackToAdminAddStudents(View view) {
        Intent intent = new Intent(Admin_students.this, Admin_add_students.class);
        startActivity(intent);
        finish();
    }
}
