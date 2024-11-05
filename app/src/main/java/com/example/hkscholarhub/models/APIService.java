package com.example.hkscholarhub.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @Headers("Content-Type: application/json")
    @POST("api/create-user")
    Call<UserResponse> createUser(@Body CreateUserRequest request);

    @GET("api/students")
    Call<StudentsResponse> getStudents();

    @GET("api/faculties")
    Call<FacultiesResponse> getFaculties();

    @POST("api/tasks")
    Call<AddTaskResponse> addTask(@Header("Authorization") String token, @Body AddTaskRequest request);

    @GET("api/tasks/student/{student_id}")
    Call<DutiesResponse> getAllDuties(@Path("student_id") long studentId);

    @PATCH("api/tasks/{id}/complete")
    Call<Void> markTaskAsCompleted(@Path("id") long taskId);

    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") long userId);

    @POST("api/assign-student")
    Call<Void> assignStudent(@Body StudentAssignmentRequest request);


    Call<AddTaskResponse> addTask(AddTaskRequest request);
}


