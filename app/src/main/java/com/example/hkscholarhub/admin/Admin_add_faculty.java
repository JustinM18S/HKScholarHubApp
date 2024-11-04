package com.example.hkscholarhub.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Admin_add_faculty extends AppCompatActivity {

    private EditText editTextFullName, editTextEmail, editTextPassword, editTextReEnterPassword, editTextFacultyId;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_faculty);

        // Initialize the UI components
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.enterpasswordfaculty);
        editTextReEnterPassword = findViewById(R.id.enter_confirmedpass);
        editTextFacultyId = findViewById(R.id.facultyid);
        btnSubmit = findViewById(R.id.btnSubmit);


        // Set up the Submit button click listener
        btnSubmit.setOnClickListener(v -> {
            // Get the input values from UI fields
            String name = editTextFullName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextReEnterPassword.getText().toString().trim();
            String facultyId = editTextFacultyId.getText().toString().trim();

            // Validate that password and confirm password match
            if (!password.equals(confirmPassword)) {
                Toast.makeText(Admin_add_faculty.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }


            // Call the method to create user account for faculty
            createUserAccount(name, email, password, confirmPassword, "faculty", facultyId);
        });
    }

    private void createUserAccount(String name, String email, String password,String confirmPassword, String userType, String facultyId) {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        APIService apiService = retrofit.create(APIService.class);

        // Create the request object with the faculty data
        CreateUserRequest request = new CreateUserRequest(name, email, password, confirmPassword, userType, null, facultyId, null);
        Call<UserResponse> call = apiService.createUser(request);

        // Execute the API call asynchronously
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call,@NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Admin_add_faculty.this, "Faculty created successfully: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(Admin_add_faculty.this, "Failed to create faculty: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call,@NonNull Throwable t) {
                Toast.makeText(Admin_add_faculty.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Method to handle back navigation
    public void goBackToAdminFaculty(View view) {
        Intent intent = new Intent(Admin_add_faculty.this, Admin_faculty.class);
        startActivity(intent);
        finish();
    }
}
