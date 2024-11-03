package com.example.hkscholarhub.models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    // Endpoint for forgot password
    @FormUrlEncoded
    @POST("api/auth/forgot-password")
    Call<ForgotPasswordResponse> forgotPassword(
            @Field("email") String email
    );

    // Endpoint for reset password
    @FormUrlEncoded
    @POST("api/auth/reset-password")
    Call<ResetPasswordResponse> resetPassword(
            @Field("email") String email,
            @Field("new_password") String newPassword,
            @Field("verification_code") String verificationCode // Assuming you use a code to verify
    );

    // Endpoint for verify code (Optional)
    @FormUrlEncoded
    @POST("api/auth/verify-code")
    Call<VerifyCodeResponse> verifyCode(
            @Field("email") String email,
            @Field("code") String code
    );
}