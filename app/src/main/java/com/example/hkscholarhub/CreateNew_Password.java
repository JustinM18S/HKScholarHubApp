package com.example.hkscholarhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.ResetPasswordResponse;
import com.example.hkscholarhub.models.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNew_Password extends AppCompatActivity {

    private TextInputEditText newPasswordEditText;
    private TextInputEditText confirmPasswordEditText;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        // Initialize UI elements
        TextInputLayout newPasswordLayout = findViewById(R.id.newpassword);
        newPasswordEditText = (TextInputEditText) newPasswordLayout.getEditText();

        TextInputLayout confirmPasswordLayout = findViewById(R.id.confirmpass);
        confirmPasswordEditText = (TextInputEditText) confirmPasswordLayout.getEditText();

        Button saveButton = findViewById(R.id.sign_in_button);

        // Initialize APIService using Retrofit
        apiService = RetrofitClient.getInstance(this).create(APIService.class);

        // Set up onClick listener for the "Save" button
        saveButton.setOnClickListener(view -> {
            String newPassword = (newPasswordEditText != null && newPasswordEditText.getText() != null) ? newPasswordEditText.getText().toString().trim() : "";
            String confirmPassword = (confirmPasswordEditText != null && confirmPasswordEditText.getText() != null) ? confirmPasswordEditText.getText().toString().trim() : "";

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(CreateNew_Password.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(CreateNew_Password.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                String email = getIntent().getStringExtra("email"); // Assuming the email was passed from the previous activity
                String verificationCode = getIntent().getStringExtra("verification_code"); // Assuming verification code is also passed
                if (email != null && verificationCode != null) {
                    resetPassword(email, newPassword, verificationCode);
                } else {
                    Toast.makeText(CreateNew_Password.this, "Email or verification code missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetPassword(String email, String newPassword, String verificationCode) {
        Call<ResetPasswordResponse> call = apiService.resetPassword(email, newPassword, verificationCode);
        call.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResetPasswordResponse> call, @NonNull Response<ResetPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CreateNew_Password.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    // Navigate to SignIn screen after a successful password reset
                    Intent intent = new Intent(CreateNew_Password.this, SignIn.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CreateNew_Password.this, "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResetPasswordResponse> call, @NonNull Throwable t) {
                Toast.makeText(CreateNew_Password.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to handle back navigation
    public void goBackToVerifyEmail(View view) {
        Intent intent = new Intent(CreateNew_Password.this, Email_Verification.class);
        startActivity(intent);
        finish();
    }
}
