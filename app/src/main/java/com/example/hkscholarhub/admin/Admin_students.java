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
import com.example.hkscholarhub.models.RetrofitClient;
import com.example.hkscholarhub.models.StudentsResponse;
import com.example.hkscholarhub.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Admin_students extends AppCompatActivity {

    private LinearLayout studentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students);

        studentContainer = findViewById(R.id.user_list_container);

        // Fetch student list
        fetchStudentList();
    }

    private void fetchStudentList() {
        // Initialize Retrofit client
        Retrofit retrofit = RetrofitClient.getInstance(this);
        APIService apiService = retrofit.create(APIService.class);

        // Make the API call to fetch students
        Call<StudentsResponse> call = apiService.getStudents();
        call.enqueue(new Callback<StudentsResponse>() {
            @Override
            public void onResponse(@NonNull Call<StudentsResponse> call, @NonNull Response<StudentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> students = response.body().getStudents(); // Assuming `getStudents()` returns List<User>
                    if (students != null && !students.isEmpty()) {
                        for (User student : students) {
                            addStudentToLayout(student);
                        }
                    } else {
                        Toast.makeText(Admin_students.this, "No students found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Admin_students.this, "Failed to fetch student list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StudentsResponse> call, @NonNull Throwable t) {
                Toast.makeText(Admin_students.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addStudentToLayout(User student) {
        // Inflate student item layout
        View studentView = LayoutInflater.from(this).inflate(R.layout.user_item_layout, studentContainer, false);

        // Set the student details
        TextView studentName = studentView.findViewById(R.id.user_name);
        TextView studentEmail = studentView.findViewById(R.id.user_email);
        TextView studentIdTextView = studentView.findViewById(R.id.user_id);

        studentName.setText(student.getName());
        studentEmail.setText(student.getEmail());
        studentIdTextView.setText(String.valueOf(student.getId()));  // Set the hidden user ID properly

        // Delete button click listener
        ImageView deleteUserIcon = studentView.findViewById(R.id.delete_icon);
        deleteUserIcon.setOnClickListener(v -> {
            // Parse the student ID and delete user
            try {
                long studentId = Long.parseLong(studentIdTextView.getText().toString().trim());
                deleteUser(studentId, studentView);
            } catch (NumberFormatException e) {
                Toast.makeText(Admin_students.this, "Invalid user ID", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the inflated view to the container
        studentContainer.addView(studentView);
    }

    private void deleteUser(long studentId, View studentView) {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        APIService apiService = retrofit.create(APIService.class);

        Call<Void> call = apiService.deleteUser(studentId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Admin_students.this, "User deleted successfully", Toast.LENGTH_SHORT).show();

                    // Remove the student view immediately from the UI
                    studentContainer.removeView(studentView);
                } else {
                    Toast.makeText(Admin_students.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(Admin_students.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to handle back navigation
    public void goBackToAdminHomePage(View view) {
        Intent intent = new Intent(Admin_students.this, Admin_Home.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to add student
    public void goToAdminAddStudents(View view) {
        Intent intent = new Intent(Admin_students.this, Admin_add_students.class);
        startActivity(intent);
        finish();
    }
}
