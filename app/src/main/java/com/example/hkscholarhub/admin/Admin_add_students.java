package com.example.hkscholarhub.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hkscholarhub.R;
import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.CreateUserRequest;
import com.example.hkscholarhub.models.RetrofitClient;
import com.example.hkscholarhub.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Admin_add_students extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextReEnterPassword, editTextStudentId;
    private Spinner spinnerHKDutyType;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_students);

        // Initialize UI components
        editTextName = findViewById(R.id.editTextFirstName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.enterpasswordstudent);
        editTextReEnterPassword = findViewById(R.id.enter_confirmedpass);
        editTextStudentId = findViewById(R.id.studentid);
        spinnerHKDutyType = findViewById(R.id.spinnerHKDutyType);
        btnSubmit = findViewById(R.id.btnSubmit);


        // Set up HK Duty Type Spinner
        String[] hkDutyTypes = {"HK25", "HK50", "HK75"};
        ArrayAdapter<String> hkDutyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hkDutyTypes);
        hkDutyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHKDutyType.setAdapter(hkDutyAdapter);

        // Handle the submit button click
        btnSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextReEnterPassword.getText().toString().trim();
            String userType = "student"; // Force the user type to "student"
            String studentId = editTextStudentId.getText().toString().trim();
            String hkType = spinnerHKDutyType.getSelectedItem().toString();

            // Validate passwords
            if (!password.equals(confirmPassword)) {
                Toast.makeText(Admin_add_students.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            createUserAccount(name, email, password, confirmPassword, userType, studentId, null, hkType);
        });
    }

    private void createUserAccount(String name, String email, String password, String confirmPassword, String userType, String studentId, String facultyId, String hkType) {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        APIService apiService = retrofit.create(APIService.class);

        CreateUserRequest request = new CreateUserRequest(name, email, password, confirmPassword, userType, studentId, facultyId, hkType);
        Call<UserResponse> call = apiService.createUser(request);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Admin_add_students.this, "User created successfully: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    // Redirect back to Admin_students activity
                    Intent intent = new Intent(Admin_add_students.this, Admin_students.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(Admin_add_students.this, "Failed to create student: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Toast.makeText(Admin_add_students.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to handle back navigation
    public void goBackToAdminStudents(View view) {
        Intent intent = new Intent(Admin_add_students.this, Admin_students.class);
        startActivity(intent);
        finish();
    }
}
