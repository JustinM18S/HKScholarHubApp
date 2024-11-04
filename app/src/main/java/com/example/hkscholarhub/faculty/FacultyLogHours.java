package com.example.hkscholarhub.faculty;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hkscholarhub.R;
import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.AddTaskRequest;
import com.example.hkscholarhub.models.AddTaskResponse;
import com.example.hkscholarhub.models.RetrofitClient;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FacultyLogHours extends AppCompatActivity {

    private Spinner spinnerDutyTask;
    private EditText editTextStudentId, editTextDutyDate, editTextDutyStart, editTextDutyEnd;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faculty_log_hours);

        // Adjust padding for edge-to-edge content
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FacultyLogHours), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        spinnerDutyTask = findViewById(R.id.spinnerDutyTask);
        editTextStudentId = findViewById(R.id.editTextStudentId);
        editTextDutyDate = findViewById(R.id.editTextDutyDate);
        editTextDutyStart = findViewById(R.id.editTextDutyStart);
        editTextDutyEnd = findViewById(R.id.editTextDutyEnd);
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        // Set up the spinner with sample tasks
        String[] tasks = {"Select Task", "Checking of Activity", "Attendance Monitoring", "Records Score", "Facilitate Classroom", "Announcement"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tasks);
        spinnerDutyTask.setAdapter(adapter);

        // Initialize Calendar for Date and Time Pickers
        calendar = Calendar.getInstance();

        // Date Picker
        editTextDutyDate.setOnClickListener(v -> showDatePicker());

        // Time Picker for Start Time
        editTextDutyStart.setOnClickListener(v -> showTimePicker(editTextDutyStart));

        // Time Picker for End Time
        editTextDutyEnd.setOnClickListener(v -> showTimePicker(editTextDutyEnd));

        // Save Button action
        saveButton.setOnClickListener(v -> {
            String studentIdString = editTextStudentId.getText().toString();
            String task = spinnerDutyTask.getSelectedItem().toString();
            String date = editTextDutyDate.getText().toString();
            String startTime = editTextDutyStart.getText().toString();
            String endTime = editTextDutyEnd.getText().toString();

            if (validateInputs(studentIdString, task, date, startTime, endTime)) {
                long studentId = Long.parseLong(studentIdString);
                // Save logic to backend
                saveDutyToServer(studentId, task, date, startTime, endTime);
            } else {
                Toast.makeText(FacultyLogHours.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });

        // Cancel Button action
        cancelButton.setOnClickListener(v -> {
            // Clear all inputs
            spinnerDutyTask.setSelection(0);
            editTextStudentId.setText("");
            editTextDutyDate.setText("");
            editTextDutyStart.setText("");
            editTextDutyEnd.setText("");
        });
    }

    // Show DatePickerDialog
    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                FacultyLogHours.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, (selectedMonth + 1), selectedDay);
                    editTextDutyDate.setText(date);

                },
                year, month, day);
        datePickerDialog.show();
    }

    // Show TimePickerDialog
    private void showTimePicker(EditText editText) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                FacultyLogHours.this,
                (view, selectedHour, selectedMinute) -> {
                    String amPm = "AM";
                    int hourForDisplay = selectedHour;

                    if (selectedHour >= 12) {
                        amPm = "PM";
                        if (selectedHour > 12) {
                            hourForDisplay = selectedHour - 12; // Convert 24-hour time to 12-hour time
                        }
                    } else if (selectedHour == 0) {
                        hourForDisplay = 12; // Convert midnight hour to 12 AM
                    }

                    String time = String.format(Locale.getDefault(), "%02d:%02d %s", hourForDisplay, selectedMinute, amPm);
                    editText.setText(time);
                },
                hour, minute, false // Set false to show the AM/PM selector
        );
        timePickerDialog.show();
    }


    // Validate inputs before saving
    private boolean validateInputs(String studentId, String task, String date, String startTime, String endTime) {
        return !(studentId.isEmpty() || task.equals("Select Task") || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty());
    }

    // Save Duty to Server using Retrofit
    private void saveDutyToServer(long studentId, String task, String date, String startTime, String endTime) {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        APIService apiService = retrofit.create(APIService.class);

        AddTaskRequest request = new AddTaskRequest(studentId, task, date, startTime, endTime);
        Call<AddTaskResponse> call = apiService.addTask(request);

        call.enqueue(new Callback<AddTaskResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddTaskResponse> call, @NonNull Response<AddTaskResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(FacultyLogHours.this, "Duty log saved successfully: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = Objects.requireNonNull(response.errorBody()).string();
                        Toast.makeText(FacultyLogHours.this, "Failed to save duty log: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(FacultyLogHours.this, "Failed to save duty log, and error body could not be read.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddTaskResponse> call, @NonNull Throwable t) {
                Toast.makeText(FacultyLogHours.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to handle back navigation
    public void goBackToFacultyLog(View view) {
        Intent intent = new Intent(FacultyLogHours.this, FacultyAddEntry.class);
        startActivity(intent);
        finish();
    }
}
