package com.example.hkscholarhub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.ForgotPasswordResponse;
import com.example.hkscholarhub.models.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot_Password extends AppCompatActivity {

    private TextInputEditText emailEditText;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize UI elements
        emailEditText = findViewById(R.id.tvpassword);  // Updated to use the correct ID
        Button sendEmailButton = findViewById(R.id.send_email_button);

        // Initialize APIService using Retrofit
        apiService = RetrofitClient.getInstance(this).create(APIService.class);

        // Set onClick listener for the "Send" button
        sendEmailButton.setOnClickListener(view -> {
            String email = (emailEditText != null && emailEditText.getText() != null) ? emailEditText.getText().toString().trim() : "";
            if (!email.isEmpty()) {
                sendForgotPasswordRequest(email);
            } else {
                Toast.makeText(Forgot_Password.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendForgotPasswordRequest(String email) {
        Call<ForgotPasswordResponse> call = apiService.forgotPassword(email);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForgotPasswordResponse> call, @NonNull Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Use the getMessage() method to get the server response message
                    String serverMessage = response.body().getMessage();
                    if (serverMessage != null && !serverMessage.isEmpty()) {
                        Toast.makeText(Forgot_Password.this, serverMessage, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Forgot_Password.this, "Check your email for further instructions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Forgot_Password.this, "Failed to send email, please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ForgotPasswordResponse> call, @NonNull Throwable t) {
                Toast.makeText(Forgot_Password.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
