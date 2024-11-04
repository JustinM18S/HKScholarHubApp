package com.example.hkscholarhub.faculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hkscholarhub.R;
import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.DutiesResponse;
import com.example.hkscholarhub.models.Duty;
import com.example.hkscholarhub.models.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FacultyAddEntry extends AppCompatActivity {

    private LinearLayout dtrDetailsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faculty_add_entry);

        // Adjust insets for the layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FacultyAddEntry), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Set up navigation for "Add Entry" button
        Button addEntryButton = findViewById(R.id.add_entry_btn);
        addEntryButton.setOnClickListener(view -> {
            // Navigate to FacultyLogHours activity
            Intent intent = new Intent(FacultyAddEntry.this, FacultyLogHours.class);
            startActivity(intent);
        });

        dtrDetailsLayout = findViewById(R.id.dtrDetailsLayout);

        fetchDuties();
    }

    private void fetchDuties() {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        APIService apiService = retrofit.create(APIService.class);

        long studentId = 1; // Replace with actual student ID

        Call<DutiesResponse> call = apiService.getAllDuties(studentId);
        call.enqueue(new Callback<DutiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<DutiesResponse> call, @NonNull Response<DutiesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Duty> duties = response.body().getTasks();
                    displayDuties(duties);
                } else {
                    Toast.makeText(FacultyAddEntry.this, "Failed to load duties.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DutiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(FacultyAddEntry.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void displayDuties(List<Duty> duties) {
        dtrDetailsLayout.removeAllViews(); // Clear existing views

        for (Duty duty : duties) {
            View dutyView = getLayoutInflater().inflate(R.layout.item_duty, null);

            TextView timeText = dutyView.findViewById(R.id.timeTextView);
            TextView dateText = dutyView.findViewById(R.id.dateTextView);
            TextView taskText = dutyView.findViewById(R.id.taskTextView);
            TextView totalHoursText = dutyView.findViewById(R.id.totalHoursTextView);

            timeText.setText(duty.getDutyStart() + " - " + duty.getDutyEnd());
            dateText.setText(duty.getDutyDate());
            taskText.setText(duty.getTask());
            totalHoursText.setText(duty.getStatus());

            dtrDetailsLayout.addView(dutyView);
        }
    }

    // Method to handle back navigation
    public void goBackToFacultyLogStudent(View view) {
        Intent intent = new Intent(FacultyAddEntry.this, FacultyLogStudent.class);
        startActivity(intent);
        finish();
    }
}

