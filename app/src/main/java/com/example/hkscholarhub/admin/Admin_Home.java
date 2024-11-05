package com.example.hkscholarhub.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hkscholarhub.R;
import com.example.hkscholarhub.models.APIService;
import com.example.hkscholarhub.models.FacultiesResponse;
import com.example.hkscholarhub.models.RetrofitClient;
import com.example.hkscholarhub.models.StudentsResponse;
import com.example.hkscholarhub.models.StudentAssignmentRequest;
import com.example.hkscholarhub.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Home extends AppCompatActivity {

    private TextView totalStudentsTextView, totalFacultiesTextView;
    private Spinner studentSpinner;
    private Spinner facultySpinner;
    private APIService apiService;

    private List<User> studentList = new ArrayList<>();
    private List<FacultiesResponse.Faculty> facultyList = new ArrayList<>();
    private User selectedStudent;
    private FacultiesResponse.Faculty selectedFaculty;
    private String selectedDutyType;
    private String selectedHkType;

    // Duty types and HK types
    private final String[] dutyTypes = {"Internal Facilitator", "External Facilitator"};
    private final String[] hkTypes = {"HK25", "HK50", "HK75"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Initialize views
        totalStudentsTextView = findViewById(R.id.totalstudents);
        totalFacultiesTextView = findViewById(R.id.totalfaculties);
        studentSpinner = findViewById(R.id.studentSpinner);
        facultySpinner = findViewById(R.id.facultySpinner);
        Spinner dutyTypeSpinner = findViewById(R.id.dutyTypeSpinner);
        Spinner hkTypeSpinner = findViewById(R.id.hkTypeSpinner);
        Button assignButton = findViewById(R.id.assignButton);

        // Initialize API service
        apiService = RetrofitClient.getInstance(this).create(APIService.class);

        // Fetch and update the total count of students and faculties
        fetchStudentAndFacultyCounts();

        // Set up the duty type spinner
        ArrayAdapter<String> dutyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dutyTypes);
        dutyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dutyTypeSpinner.setAdapter(dutyAdapter);
        dutyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDutyType = dutyTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDutyType = null;
            }
        });

        // Set up the HK type spinner
        ArrayAdapter<String> hkAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hkTypes);
        hkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hkTypeSpinner.setAdapter(hkAdapter);
        hkTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHkType = hkTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedHkType = null;
            }
        });

        // Fetch students and faculties
        fetchStudentsAndFaculties();

        // Set up assign button click listener
        assignButton.setOnClickListener(v -> assignStudentToFaculty());

        // Set up bottom navigation
        setupBottomNavigation();
    }

    // Method to set up bottom navigation
    private void setupBottomNavigation() {
        BottomNavigationView adminBottomNavigationView = findViewById(R.id.adminbottomNavigationView);
        adminBottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Navigate to the Home page (current page)
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Navigate to the Admin Profile page
                startActivity(new Intent(Admin_Home.this, Admin_Profile.class));
                return true;
            } else if (itemId == R.id.nav_dtr) {
                // Show a popup for choosing between Student and Professor
                showUserOptions();
                return true;
            }
            return false;
        });
    }

    // Method to show popup options for Users (Students or Faculties)
    private void showUserOptions() {
        PopupMenu popupMenu = new PopupMenu(Admin_Home.this, findViewById(R.id.nav_dtr));
        popupMenu.getMenuInflater().inflate(R.menu.users_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int menuItemId = menuItem.getItemId();
            if (menuItemId == R.id.add_student) {
                // Navigate to Add Student Page
                startActivity(new Intent(Admin_Home.this, Admin_students.class));
                return true;
            } else if (menuItemId == R.id.add_professor) {
                // Navigate to Add Professor Page
                startActivity(new Intent(Admin_Home.this, Admin_faculty.class));
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    // Method to fetch and update student and faculty counts
    private void fetchStudentAndFacultyCounts() {
        // Fetch the student count
        Call<StudentsResponse> studentCall = apiService.getStudents();
        studentCall.enqueue(new Callback<StudentsResponse>() {
            @Override
            public void onResponse(@NonNull Call<StudentsResponse> call, @NonNull Response<StudentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int totalStudents = response.body().getStudents().size();
                    totalStudentsTextView.setText(String.valueOf(totalStudents));
                } else {
                    Toast.makeText(Admin_Home.this, "Failed to load students count", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StudentsResponse> call, @NonNull Throwable t) {
                Toast.makeText(Admin_Home.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch the faculty count
        Call<FacultiesResponse> facultyCall = apiService.getFaculties();
        facultyCall.enqueue(new Callback<FacultiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<FacultiesResponse> call, @NonNull Response<FacultiesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int totalFaculties = response.body().getFaculties().size();
                    totalFacultiesTextView.setText(String.valueOf(totalFaculties));
                } else {
                    Toast.makeText(Admin_Home.this, "Failed to load faculties count", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FacultiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(Admin_Home.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to fetch students and faculties to populate the Spinners
    private void fetchStudentsAndFaculties() {
        // Fetch students
        apiService.getStudents().enqueue(new Callback<StudentsResponse>() {
            @Override
            public void onResponse(@NonNull Call<StudentsResponse> call, @NonNull Response<StudentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    studentList.clear();
                    studentList = response.body().getStudents();
                    ArrayAdapter<User> studentAdapter = new ArrayAdapter<>(Admin_Home.this, android.R.layout.simple_spinner_item, studentList);
                    studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    studentSpinner.setAdapter(studentAdapter);
                    studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedStudent = studentList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            selectedStudent = null;
                        }
                    });
                } else {
                    Toast.makeText(Admin_Home.this, "Failed to load students", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StudentsResponse> call, @NonNull Throwable t) {
                Toast.makeText(Admin_Home.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch faculties
        apiService.getFaculties().enqueue(new Callback<FacultiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<FacultiesResponse> call, @NonNull Response<FacultiesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    facultyList.clear();
                    facultyList = response.body().getFaculties();
                    ArrayAdapter<FacultiesResponse.Faculty> facultyAdapter = new ArrayAdapter<>(Admin_Home.this, android.R.layout.simple_spinner_item, facultyList);
                    facultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    facultySpinner.setAdapter(facultyAdapter);
                    facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedFaculty = facultyList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            selectedFaculty = null;
                        }
                    });
                } else {
                    Toast.makeText(Admin_Home.this, "Failed to load faculties", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FacultiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(Admin_Home.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to assign a student to a faculty
    private void assignStudentToFaculty() {
        if (selectedStudent == null || selectedFaculty == null || selectedDutyType == null || selectedHkType == null) {
            Toast.makeText(this, "Please select all fields before assigning", Toast.LENGTH_SHORT).show();
            return;
        }

        StudentAssignmentRequest request = new StudentAssignmentRequest(
                selectedStudent.getId(),
                selectedFaculty.getId(),
                selectedHkType,
                selectedDutyType
        );

        apiService.assignStudent(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Admin_Home.this, "Student assigned successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Admin_Home.this, "Failed to assign student", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(Admin_Home.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
