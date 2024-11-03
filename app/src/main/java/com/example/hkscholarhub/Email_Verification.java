package com.example.hkscholarhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.RetrofitClient;
import com.example.hkscholarhub.models.VerifyCodeResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Email_Verification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_verification);

        // Handle insets for proper layout (keeping the existing logic)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.emailverify), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        TextInputLayout codeInputLayout = findViewById(R.id.tvcode);
        TextInputEditText codeEditText = (TextInputEditText) codeInputLayout.getEditText(); // Get the EditText inside the TextInputLayout
        Button verifyButton = findViewById(R.id.verify_button);

        // Initialize API service using Retrofit
        APIService apiService = RetrofitClient.getInstance(this).create(APIService.class);

        // Set up onClickListener for the verify button
        verifyButton.setOnClickListener(view -> {
            String code = (codeEditText != null && codeEditText.getText() != null) ? codeEditText.getText().toString().trim() : "";

            if (code.isEmpty()) {
                Toast.makeText(Email_Verification.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
            } else {
                String email = getIntent().getStringExtra("email"); // Assuming email is passed from previous activity
                if (email == null) {
                    Toast.makeText(Email_Verification.this, "Email information missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Call the verify code API
                verifyEmailCode(apiService, email, code);
            }
        });
    }

    // Method to verify the email code
    private void verifyEmailCode(APIService apiService, String email, String code) {
        Call<VerifyCodeResponse> call = apiService.verifyCode(email, code);
        call.enqueue(new Callback<VerifyCodeResponse>() {
            @Override
            public void onResponse(@NonNull Call<VerifyCodeResponse> call, @NonNull Response<VerifyCodeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Email_Verification.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    // Proceed to Create New Password Screen
                    Intent intent = new Intent(Email_Verification.this, CreateNew_Password.class);
                    intent.putExtra("email", email); // Pass the email to the next activity
                    startActivity(intent);
                } else {
                    Toast.makeText(Email_Verification.this, "Invalid code. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<VerifyCodeResponse> call, @NonNull Throwable t) {
                Toast.makeText(Email_Verification.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to handle back navigation to Forgot Password page
    public void goBackToForgotPassword(View view) {
        Intent intent = new Intent(Email_Verification.this, Forgot_Password.class);
        startActivity(intent);
        finish();
    }
}
