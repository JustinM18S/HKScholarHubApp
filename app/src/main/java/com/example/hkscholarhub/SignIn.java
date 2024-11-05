package com.example.hkscholarhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hkscholarhub.admin.Admin_Home;
import com.example.hkscholarhub.faculty.FacultyHomePage;
import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.LoginResponse;
import com.example.hkscholarhub.models.RetrofitClient;
import com.example.hkscholarhub.student.StudentHomePage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    private EditText email, password;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        apiService = RetrofitClient.getInstance(this).create(APIService.class);
    }

    public void Signin(View view) {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
            Call<LoginResponse> call = apiService.login(userEmail, userPassword);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse loginResponse = response.body();
                        String message = loginResponse.getMessage();

                        if (message.trim().equalsIgnoreCase("invalid credentials")) {
                            Toast.makeText(SignIn.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (message.trim().equalsIgnoreCase("email not verified")) {
                            Toast.makeText(SignIn.this, "Please verify your email before logging in", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (loginResponse.getUser() == null) {
                            Toast.makeText(SignIn.this, "User not found in response", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String userType = loginResponse.getUser().getUser_type();
                        String token = loginResponse.getToken();  // Retrieve the token

                        // Store the token in SharedPreferences for authenticated requests later
                        saveToken(token);

                        // Redirect to respective homepages
                        handleUserRedirection(userType);
                    } else {
                        Toast.makeText(SignIn.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    Toast.makeText(SignIn.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleUserRedirection(String userType) {
        Toast.makeText(SignIn.this, "User Type: " + userType, Toast.LENGTH_SHORT).show();
        Intent intent;

        switch (userType) {
            case "student":
                intent = new Intent(SignIn.this, StudentHomePage.class);
                break;
            case "faculty":
                intent = new Intent(SignIn.this, FacultyHomePage.class);
                break;
            case "admin":
                intent = new Intent(SignIn.this, Admin_Home.class);
                break;
            default:
                Toast.makeText(this, "Unknown user type", Toast.LENGTH_SHORT).show();
                return;
        }

        startActivity(intent);
        finish();
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TOKEN", token);
        editor.apply();
    }

    public void goToForgotPassword(View view) {
        Intent intent = new Intent(SignIn.this, Forgot_Password.class);
        startActivity(intent);
        finish();
    }
}
